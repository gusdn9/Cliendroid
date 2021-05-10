package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.databinding.ItemCommonListBinding
import com.hyunwoo.cliendroid.domain.model.Forum
import com.hyunwoo.cliendroid.extension.getColorWithAttr

class ForumListViewHolder private constructor(
    private val binding: ItemCommonListBinding,
    private val imageLoader: ImageLoader,
    private val onForumItemClicked: (Forum) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val color = binding.root.context.getColorWithAttr(R.attr.colorSecondary)

    fun bind(forumItem: Forum) {
        binding.root.setOnClickListener {
            onForumItemClicked(forumItem)
        }
        // max 2000
        val hit = forumItem.hit ?: 0L
        var alpha = hit / MAX_HIT
        alpha = if (alpha > 1f) 1f else alpha

        binding.hits.setBackgroundColor(color)
        binding.hits.alpha = alpha

        binding.title.text = forumItem.title
        binding.time.text = forumItem.time

        binding.user.userNickname.isVisible = forumItem.user.nickName != null
        binding.user.userNickname.text = forumItem.user.nickName

        binding.user.userImage.isVisible = forumItem.user.image != null
        binding.user.userImage.load(forumItem.user.image, imageLoader)

        binding.replyCount.isVisible = forumItem.replyCount != null
        binding.replyCount.text = forumItem.replyCount?.toString()

        binding.likes.isVisible = forumItem.likes != null
        binding.likes.text = forumItem.likes?.toString()
    }

    companion object {

        private const val MAX_HIT = 2000f

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            imageLoader: ImageLoader,
            onForumItemClicked: (Forum) -> Unit
        ): ForumListViewHolder =
            ForumListViewHolder(
                ItemCommonListBinding.inflate(inflater, parent, false),
                imageLoader,
                onForumItemClicked
            )
    }
}
