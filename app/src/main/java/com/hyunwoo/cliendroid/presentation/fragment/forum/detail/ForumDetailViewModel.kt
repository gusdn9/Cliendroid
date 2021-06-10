package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.domain.usecase.GetForumDetailUseCase
import com.hyunwoo.cliendroid.domain.usecase.auth.GetLoggedInUserUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class ForumDetailViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getForumDetailUseCase: GetForumDetailUseCase,
    private val getLoggedInUserUseCase: GetLoggedInUserUseCase
) : MavericksViewModel<State>(initialState) {

    init {
        setState {
            copy(loggedInUser = getLoggedInUserUseCase())
        }

        refresh()
    }

    fun refresh() = withState { state ->
        if (state.refreshAsync is Loading) {
            return@withState
        }

        suspend {
            getForumDetailUseCase(state.url)
        }.execute { async ->
            var nextState = this
            if (async is Success) {
                nextState = nextState.copy(content = async())
            }
            nextState.copy(refreshAsync = async)
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): ForumDetailViewModel
    }

    companion object : MavericksViewModelFactory<ForumDetailViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): ForumDetailViewModel {
            val fragment: ForumDetailFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
