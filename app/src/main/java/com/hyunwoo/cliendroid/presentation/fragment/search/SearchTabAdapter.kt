package com.hyunwoo.cliendroid.presentation.fragment.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyunwoo.cliendroid.extension.toFragmentArgsBundle
import com.hyunwoo.cliendroid.presentation.fragment.search.board.SearchBoardFragment

class SearchTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var args: SearchArgs? = null

    fun setArgs(args: SearchArgs) {
        this.args = args
    }

    override fun getItemCount(): Int = Tab.ALL.size

    override fun createFragment(position: Int): Fragment {
        return SearchBoardFragment().apply {
            arguments = args?.toFragmentArgsBundle()
        }
    }
}
