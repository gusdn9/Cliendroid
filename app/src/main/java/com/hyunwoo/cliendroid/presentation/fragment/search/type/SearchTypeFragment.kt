package com.hyunwoo.cliendroid.presentation.fragment.search.type

import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import javax.inject.Inject

class SearchTypeFragment : AppFragment() {

    private val viewModel by fragmentViewModel(SearchTypeViewModel::class)

    @Inject
    lateinit var viewModelFactory: SearchTypeViewModel.Factory
}
