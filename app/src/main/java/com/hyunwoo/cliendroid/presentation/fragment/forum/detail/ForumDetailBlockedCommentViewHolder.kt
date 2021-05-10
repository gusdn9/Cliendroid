package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemBlockedCommentBinding
import com.hyunwoo.cliendroid.domain.model.BlockedComment

class ForumDetailBlockedCommentViewHolder private constructor(
    private val binding: ItemBlockedCommentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: BlockedComment) {
        binding.reCommentSpace.isVisible = comment.isReply
        binding.contents.text = comment.contents
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): ForumDetailBlockedCommentViewHolder =
            ForumDetailBlockedCommentViewHolder(
                ItemBlockedCommentBinding.inflate(inflater, parent, false)
            )
    }
}
