package com.hyunwoo.cliendroid.presentation.fragment.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyunwoo.cliendroid.extension.toFragmentArgsBundle
import com.hyunwoo.cliendroid.presentation.fragment.search.board.SearchBoardFragment

class SearchTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private var args: SearchArgs? = null
    private var isLoggedIn: Boolean = false

    fun setArgs(args: SearchArgs) {
        this.args = args
    }

    fun setIsLoggedIn(isLoggedIn: Boolean) {
        this.isLoggedIn = isLoggedIn
    }

    override fun getItemCount(): Int =
        if (isLoggedIn)
            Tab.ALL.size
        else
            Tab.ALL.size - 1

    override fun createFragment(position: Int): Fragment {
        return SearchBoardFragment().apply {
            arguments = args?.toFragmentArgsBundle()
        }
    }
}
