package com.hyunwoo.cliendroid.presentation.fragment.search

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.auth.GetLoggedInUserUseCase
import com.hyunwoo.cliendroid.domain.usecase.event.LoggedOutSubscribeUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val loggedOutSubscribeUseCase: LoggedOutSubscribeUseCase
) : AppMvRxViewModel<State>(initialState) {

    init {
        setState {
            copy(loggedInUser = getLoggedInUserUseCase())
        }

        viewModelScope.launch {
            loggedOutSubscribeUseCase().collect {
                setState {
                    copy(loggedInUser = null)
                }
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
