package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemEveryoneForumListBinding
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

class ForumListViewHolder private constructor(
    private val binding: ItemEveryoneForumListBinding,
    private val onForumItemClicked: (EveryoneParkForum) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forumItem: EveryoneParkForum) {
        binding.title.text = forumItem.title
        binding.time.text = forumItem.time

        binding.userNickNameText.isVisible = forumItem.user.id != null
        binding.userNickNameText.text = forumItem.user.id

        binding.userImage.isVisible = forumItem.user.image != null

        binding.replyCount.isVisible = forumItem.replyCount != null
        binding.replyCount.text = forumItem.replyCount?.toString()

        binding.likes.isVisible = forumItem.likes != null
        binding.likes.text = forumItem.likes?.toString()
    }

    companion object {

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            onForumItemClicked: (EveryoneParkForum) -> Unit
        ): ForumListViewHolder =
            ForumListViewHolder(
                ItemEveryoneForumListBinding.inflate(inflater, parent, false),
                onForumItemClicked
            )
    }
}
