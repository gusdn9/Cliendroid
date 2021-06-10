package com.hyunwoo.cliendroid.presentation.fragment.search.board

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.domain.model.search.board.SearchSort
import com.hyunwoo.cliendroid.domain.usecase.SearchBoardUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Job

class SearchBoardViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val searchBoardUseCase: SearchBoardUseCase
) : MavericksViewModel<State>(initialState) {

    private var loadMoreJob: Job? = null

    init {
        refresh()
    }

    fun refresh() = withState { state ->
        if (state.searchRefreshAsync is Loading) {
            return@withState
        }

        loadMoreJob?.cancel()
        setState { copy(searchLoadMoreAsync = Uninitialized) }

        suspend {
            searchBoardUseCase(state.keyword, 0, state.sort, state.boardId)
        }.execute { async ->
            var nextState = this
            if (async is Success) {
                val result = async()
                nextState = nextState.copy(
                    boardList = result.boardList,
                    searchResultList = result.searchList,
                    page = 0,
                    endOfPage = result.endOfPage
                )
            }
            nextState.copy(searchRefreshAsync = async)

        }
    }

    fun loadMore() = withState { state ->
        if (state.searchRefreshAsync is Loading || state.searchLoadMoreAsync is Loading || state.endOfPage) {
            return@withState
        }

        val prevEntries = state.searchResultList ?: return@withState

        loadMoreJob = suspend {
            searchBoardUseCase(state.keyword, state.page + 1, state.sort, state.boardId)
        }.execute { async ->
            var nextState = this
            if (async is Success) {
                val result = async()
                nextState = nextState.copy(
                    searchResultList = prevEntries + result.searchList,
                    page = state.page + 1,
                    endOfPage = result.endOfPage
                )
            }
            nextState.copy(searchLoadMoreAsync = async)

        }
    }

    fun setSort(selectedSort: SearchSort) = setState {
        copy(sort = selectedSort, page = 0)
    }

    fun setBoardId(boardId: String?) = setState {
        val id = if (boardId.isNullOrEmpty()) {
            null
        } else {
            boardId
        }
        copy(boardId = id)
    }

    fun setKeyword(query: String) = setState {
        copy(keyword = query)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): SearchBoardViewModel
    }

    companion object : MavericksViewModelFactory<SearchBoardViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): SearchBoardViewModel {
            val fragment: SearchBoardFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
