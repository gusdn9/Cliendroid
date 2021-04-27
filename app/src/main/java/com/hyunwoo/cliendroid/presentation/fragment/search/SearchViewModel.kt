package com.hyunwoo.cliendroid.presentation.fragment.search

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.SearchUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class SearchViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val searchUseCase: SearchUseCase
) : AppMvRxViewModel<State>(initialState) {

    init {
        refresh()
    }

    fun refresh() = withState { state ->
        if (state.searchRefreshAsync is Loading) {
            return@withState
        }

        viewModelScope.launch {
            searchUseCase::invoke.asAsync(state.keyword, state.page, state.sort, state.boardId) { async ->
                var nextState = this
                if (async is Success) {
                    val result = async()
                    nextState = nextState.copy(boardList = result.boardList, searchResultList = result.searchList)
                }
                nextState.copy(searchRefreshAsync = async)
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): SearchViewModel
    }

    companion object : MavericksViewModelFactory<SearchViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): SearchViewModel {
            val fragment: SearchFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
