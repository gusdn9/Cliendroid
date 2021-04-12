package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hyunwoo.cliendroid.databinding.ItemEveryoneForumListBinding
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

class ForumListViewHolder private constructor(
    private val binding: ItemEveryoneForumListBinding,
    private val onForumItemClicked: (EveryoneParkForum) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forumItem: EveryoneParkForum) {
        binding.root.setOnClickListener {
            onForumItemClicked(forumItem)
        }
        binding.title.text = forumItem.title
        binding.time.text = forumItem.time

        binding.user.userNickName.isVisible = forumItem.user.nickName != null
        binding.user.userNickName.text = forumItem.user.nickName

        binding.user.userImage.isVisible = forumItem.user.image != null
        binding.user.userImage.load(forumItem.user.image)

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
