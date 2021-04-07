package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import javax.inject.Inject

class EveryOneParkListFragment : AppFragment() {

    private val viewModel by fragmentViewModel(EveryOneParkListViewModel::class)

    @Inject
    lateinit var viewModelFactory: EveryOneParkListViewModel.Factory

}
