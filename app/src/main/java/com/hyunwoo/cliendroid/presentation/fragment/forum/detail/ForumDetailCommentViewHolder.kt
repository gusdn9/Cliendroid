package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.hyunwoo.cliendroid.databinding.ItemCommentBinding
import com.hyunwoo.cliendroid.domain.model.Comment

class ForumDetailCommentViewHolder private constructor(
    private val binding: ItemCommentBinding,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(comment: Comment, isLogin: Boolean) {
        binding.reCommentSpace.isVisible = comment.isReply

        binding.user.userNickname.isVisible = comment.user.nickName != null
        binding.user.userNickname.text = comment.user.nickName

        binding.user.userImage.isVisible = comment.user.image != null
        binding.user.userImage.load(comment.user.image, imageLoader)

        binding.likes.isVisible = comment.likes > 0
        binding.likes.text = comment.likes.toString()

        binding.contents.text = comment.contents

        binding.time.text = comment.time

        binding.moreButton.isVisible = isLogin
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            imageLoader: ImageLoader
        ): ForumDetailCommentViewHolder =
            ForumDetailCommentViewHolder(ItemCommentBinding.inflate(inflater, parent, false), imageLoader)
    }
}
