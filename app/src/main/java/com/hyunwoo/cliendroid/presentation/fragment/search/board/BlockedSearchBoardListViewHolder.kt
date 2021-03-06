package com.hyunwoo.cliendroid.presentation.fragment.search.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemBlockedListBinding
import com.hyunwoo.cliendroid.domain.model.search.board.BlockedSearchItem

class BlockedSearchBoardListViewHolder private constructor(
    private val binding: ItemBlockedListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BlockedSearchItem) {
        binding.title.text = item.title
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): BlockedSearchBoardListViewHolder =
            BlockedSearchBoardListViewHolder(ItemBlockedListBinding.inflate(inflater, parent, false))
    }
}
