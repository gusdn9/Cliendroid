package com.hyunwoo.cliendroid.presentation.fragment.search.type

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.model.search.type.SearchType
import com.hyunwoo.cliendroid.domain.usecase.SearchTypeUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchTypeViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val searchTypeUseCase: SearchTypeUseCase
) : AppMvRxViewModel<State>(initialState) {

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

        viewModelScope.launch {
            searchTypeUseCase::invoke.asAsync(state.searchType, state.keyword, state.page) { async ->
                var nextState = this
                    if (async is Success) {
                    val result = async()
                    nextState = nextState.copy(
                        page = 0,
                        searchResultList = result.contents,
                        endOfPage = result.endOfPage
                    )
                }
                nextState.copy(searchRefreshAsync = async)
            }
        }
    }

    fun loadMore() = withState { state ->
        if (state.searchRefreshAsync is Loading || state.searchLoadMoreAsync is Loading || state.endOfPage) {
            return@withState
        }

        val prevEntries = state.searchResultList ?: return@withState
        loadMoreJob = viewModelScope.launch {
            searchTypeUseCase::invoke.asAsync(state.searchType, state.keyword, state.page + 1) { async ->
                var nextState = this
                if (async is Success) {
                    val result = async()
                    nextState = nextState.copy(
                        searchResultList = prevEntries + result.contents,
                        page = state.page + 1,
                        endOfPage = result.endOfPage
                    )
                }
                nextState.copy(searchLoadMoreAsync = async)
            }
        }
    }

    fun setType(searchType: SearchType) = setState {
        copy(searchType = searchType)
    }

    fun setKeyword(query: String) = setState {
        copy(keyword = query)
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): SearchTypeViewModel
    }

    companion object : MavericksViewModelFactory<SearchTypeViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): SearchTypeViewModel {
            val fragment: SearchTypeFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
