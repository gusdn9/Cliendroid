package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemBlockedListBinding
import com.hyunwoo.cliendroid.domain.model.BlockedForum

class BlockedForumListViewHolder private constructor(
    private val binding: ItemBlockedListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BlockedForum) {
        binding.title.text = item.title
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): BlockedForumListViewHolder =
            BlockedForumListViewHolder(ItemBlockedListBinding.inflate(inflater, parent, false))
    }
}
