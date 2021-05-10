package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.GetForumListUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EveryoneParkListViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getForumListUseCase: GetForumListUseCase
) : AppMvRxViewModel<State>(initialState) {

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

        viewModelScope.launch {
            getForumListUseCase::invoke.asAsync(state.url, 0) { async ->
                var nextState = this
                if (async is Success) {
                    nextState = nextState.copy(listData = async(), page = 0)
                }
                nextState.copy(listDataRefreshAsync = async)
            }
        }
    }

    fun loadMore() = withState { state ->
        if (state.listDataRefreshAsync is Loading || state.listDataLoadMoreAsync is Loading) {
            return@withState
        }

        val prevEntries = state.listData ?: return@withState

        loadMoreJob = viewModelScope.launch {
            getForumListUseCase::invoke.asAsync(state.url, state.page + 1) { async ->
                var nextState = this
                if (async is Success) {
                    nextState = nextState.copy(listData = prevEntries + async(), page = state.page + 1)
                }
                nextState.copy(listDataLoadMoreAsync = async)
            }
        }

    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): EveryoneParkListViewModel
    }

    companion object : MavericksViewModelFactory<EveryoneParkListViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): EveryoneParkListViewModel {
            val fragment: EveryoneParkListFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
