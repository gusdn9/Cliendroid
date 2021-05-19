package com.hyunwoo.cliendroid.presentation.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.args
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentSearchBinding

class SearchFragment : AppFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val args by args<SearchArgs>()

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = SearchTabAdapter(this).apply {
            setArgs(args)
        }

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (Tab.fromValue(position)) {
                Tab.BOARD_SEARCH -> getString(R.string.tab_search_board)
                Tab.TYPE_SEARCH -> getString(R.string.tab_search_type)
            }
        }
        tabLayoutMediator.attach()
    }

    override fun onDestroyView() {
        tabLayoutMediator.detach()
        super.onDestroyView()
        _binding = null
    }
}
