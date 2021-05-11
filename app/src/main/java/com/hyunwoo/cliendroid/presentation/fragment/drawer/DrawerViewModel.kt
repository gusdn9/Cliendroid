package com.hyunwoo.cliendroid.presentation.fragment.drawer

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.model.LogoutCause
import com.hyunwoo.cliendroid.domain.usecase.GetMenuBoardListUseCase
import com.hyunwoo.cliendroid.domain.usecase.auth.GetLoggedInUserUseCase
import com.hyunwoo.cliendroid.domain.usecase.auth.LoginUseCase
import com.hyunwoo.cliendroid.domain.usecase.auth.LogoutUseCase
import com.hyunwoo.cliendroid.domain.usecase.event.LoggedOutSubscribeUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DrawerViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase,
    private val getMenuBoardListUseCase: GetMenuBoardListUseCase,
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

        getMenuList()
    }

    fun login(id: String, password: String) = withState { state ->
        if (state.loginAsync is Loading) return@withState

        viewModelScope.launch {
            loginUseCase::invoke.asAsync(id, password) { async ->
                var nextState = this
                if (async is Success) {
                    nextState = copy(loggedInUser = async())
                }
                nextState.copy(loginAsync = async)
            }
        }
    }

    fun logout() = setState {
        logoutUseCase.invoke(LogoutCause.USER_INTENTION)
        copy(loggedInUser = null)
    }

    private fun getMenuList() = withState { state ->
        if (state.menuListAsync is Loading) return@withState

        viewModelScope.launch {
            getMenuBoardListUseCase::invoke.asAsync { async ->
                var nextState = this
                if (async is Success) {
                    val result = async()
                    nextState = copy(menuList = result.communities + result.somoimList)
                }
                nextState.copy(menuListAsync = async)
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
