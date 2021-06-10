package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.domain.usecase.GetForumListUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Job

class ForumListViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getForumListUseCase: GetForumListUseCase
) : MavericksViewModel<State>(initialState) {

    private var loadMoreJob: Job? = null

    init {
        refresh()
    }

    fun refresh() = withState { state ->
        if (state.listDataRefreshAsync is Loading) {
            return@withState
        }

        loadMoreJob?.cancel()
        setState { copy(listDataLoadMoreAsync = Uninitialized) }

        suspend {
            getForumListUseCase(state.url, 0)
        }.execute { async ->
            var nextState = this
            if (async is Success) {
                nextState = nextState.copy(listData = async(), page = 0)
            }
            nextState.copy(listDataRefreshAsync = async)
        }
    }

    fun loadMore() = withState { state ->
        if (state.listDataRefreshAsync is Loading || state.listDataLoadMoreAsync is Loading) {
            return@withState
        }

        val prevEntries = state.listData ?: return@withState

        loadMoreJob = suspend {
            getForumListUseCase(state.url, state.page + 1)
        }.execute { async ->
            var nextState = this
            if (async is Success) {
                nextState = nextState.copy(listData = prevEntries + async(), page = state.page + 1)
            }
            nextState.copy(listDataLoadMoreAsync = async)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): ForumListViewModel
    }

    companion object : MavericksViewModelFactory<ForumListViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): ForumListViewModel {
            val fragment: ForumListFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
