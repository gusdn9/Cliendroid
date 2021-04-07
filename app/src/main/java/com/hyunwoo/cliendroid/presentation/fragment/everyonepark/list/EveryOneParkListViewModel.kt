package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.ViewModelContext
import com.hyunwoo.cliendroid.architecture.AppMvRxViewModel
import com.hyunwoo.cliendroid.domain.usecase.GetEveryOneParkForumListUseCase
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class EveryOneParkListViewModel @AssistedInject constructor(
    @Assisted initialState: State,
    private val getEveryOneParkForumListUseCase: GetEveryOneParkForumListUseCase
) : AppMvRxViewModel<State>(initialState) {

    init {
        refresh()
    }

    fun refresh() = withState { state ->
        if (state.getListAsync is Loading) {
            return@withState
        }

        viewModelScope.launch {
            suspend {
                getEveryOneParkForumListUseCase(page = 0)
            }.asAsync { async ->
                var nextState = this
                if (async is Success) {
                    nextState = nextState.copy(listData = async())
                }
                nextState.copy(getListAsync = async)
            }
        }
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: State): EveryOneParkListViewModel
    }

    companion object : MavericksViewModelFactory<EveryOneParkListViewModel, State> {

        @JvmStatic
        override fun create(viewModelContext: ViewModelContext, state: State): EveryOneParkListViewModel {
            val fragment: EveryOneParkListFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.viewModelFactory.create(state)
        }
    }
}
