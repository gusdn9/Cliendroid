package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.GetEveryoneParkForumListUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EveryoneParkListViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getEveryoneParkForumListUseCase: GetEveryoneParkForumListUseCase
) : AppMvRxViewModel<State>(initialState) {

    private var loadMoreJob: Job? = null

    init {
        refresh()
    }

    fun refresh() = withState { state ->
        if (state.listDataRefreshAsync is Loading) {
            return@withState
        }

        viewModelScope.launch {
            suspend {
                getEveryoneParkForumListUseCase(0)
            }.asAsync { async ->
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
            suspend {
                getEveryoneParkForumListUseCase(state.page + 1)
            }.asAsync { async ->
                var nextState = this
                if (async is Success) {
                    nextState = nextState.copy(listData = prevEntries + async(), page = state.page + 1)
                }
                nextState.copy(listDataLoadMoreAsync = async)
            }
        }

    }

    private fun setPage(page: Int) = setState {
        copy(page = page)
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
