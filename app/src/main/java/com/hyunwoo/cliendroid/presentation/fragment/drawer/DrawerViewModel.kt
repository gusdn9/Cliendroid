package com.hyunwoo.cliendroid.presentation.fragment.drawer

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.auth.LoginUseCase
import com.hyunwoo.cliendroid.domain.usecase.event.LoggedInSubscribeUseCase
import com.hyunwoo.cliendroid.domain.usecase.event.LoggedOutSubscribeUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DrawerViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val loginUseCase: LoginUseCase,
    private val loggedInSubscribeUseCase: LoggedInSubscribeUseCase,
    private val loggedOutSubscribeUseCase: LoggedOutSubscribeUseCase
) : AppMvRxViewModel<State>(initialState) {

    init {
        viewModelScope.launch {
            loggedInSubscribeUseCase().collect { event ->
                setState {
                    copy(loggedInUser = event.user)
                }
            }
            loggedOutSubscribeUseCase().collect {
                setState {
                    copy(loggedInUser = null)
                }
            }
        }
    }

    fun login(id: String, password: String) = withState { state ->
        if (state.loginAsync is Loading) {
            return@withState
        }
        viewModelScope.launch {
            loginUseCase::invoke.asAsync(id, password) { async ->
                var nextState = this
                if (async is Success) {
                    nextState = nextState.copy(loggedInUser = async())
                }
                nextState.copy(loginAsync = async)
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): DrawerViewModel
    }

    companion object : MavericksViewModelFactory<DrawerViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): DrawerViewModel {
            val fragment: DrawerFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}