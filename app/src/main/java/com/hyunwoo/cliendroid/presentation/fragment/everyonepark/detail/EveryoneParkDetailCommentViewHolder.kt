package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyunwoo.cliendroid.databinding.ItemCommentBinding
import com.hyunwoo.cliendroid.domain.model.Comment

class EveryoneParkDetailCommentViewHolder private constructor(
    private val binding: ItemCommentBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment) {
        binding.user.userNickName.isVisible = comment.user.nickName != null
        binding.user.userNickName.text = comment.user.nickName

        binding.user.userImage.isVisible = comment.user.image != null
        binding.user.userImage.load(comment.user.image)

        binding.contents.text = comment.contents

        binding.time.text = comment.time
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): EveryoneParkDetailCommentViewHolder =
            EveryoneParkDetailCommentViewHolder(ItemCommentBinding.inflate(inflater, parent, false))
    }
}
