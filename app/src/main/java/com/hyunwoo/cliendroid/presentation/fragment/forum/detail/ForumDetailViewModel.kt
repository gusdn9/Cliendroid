package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.GetForumDetailUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class ForumDetailViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getForumDetailUseCase: GetForumDetailUseCase
) : AppMvRxViewModel<State>(initialState) {

    init {
        refresh()
    }

    fun refresh() = withState { state ->
        if (state.refreshAsync is Loading) {
            return@withState
        }

        viewModelScope.launch {
            getForumDetailUseCase::invoke.asAsync(state.url) { async ->
                var nextState = this
                if (async is Success) {
                    nextState = nextState.copy(content = async())
                }
                nextState.copy(refreshAsync = async)
            }
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
