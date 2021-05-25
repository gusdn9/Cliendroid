package com.hyunwoo.cliendroid.presentation.fragment.search.type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemBlockedListBinding
import com.hyunwoo.cliendroid.domain.model.search.type.BlockedSearchTypeItem

class BlockedSearchTypeListViewHolder private constructor(
    private val binding: ItemBlockedListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BlockedSearchTypeItem) {
        binding.title.text = item.title
    }

    companion object {

        fun create(inflater: LayoutInflater, parent: ViewGroup): BlockedSearchTypeListViewHolder =
            BlockedSearchTypeListViewHolder(ItemBlockedListBinding.inflate(inflater, parent, false))
    }
}
