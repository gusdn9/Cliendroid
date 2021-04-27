package com.hyunwoo.cliendroid.presentation.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchFragment : AppFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(SearchViewModel::class)

    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
