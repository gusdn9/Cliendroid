package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemBlockedCommentBinding
import com.hyunwoo.cliendroid.domain.model.BlockedComment

class EveryoneParkDetailBlockedCommentViewHolder private constructor(
    private val binding: ItemBlockedCommentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: BlockedComment) {
        binding.contents.text = comment.contents
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): EveryoneParkDetailBlockedCommentViewHolder =
            EveryoneParkDetailBlockedCommentViewHolder(
                ItemBlockedCommentBinding.inflate(inflater, parent, false)
            )
    }
}
