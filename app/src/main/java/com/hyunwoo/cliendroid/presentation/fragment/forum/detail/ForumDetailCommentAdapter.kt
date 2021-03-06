package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.hyunwoo.cliendroid.domain.model.forum.BaseComment
import com.hyunwoo.cliendroid.domain.model.forum.BlockedComment
import com.hyunwoo.cliendroid.domain.model.forum.Comment

class ForumDetailCommentAdapter(
    private val imageLoader: ImageLoader
) : ListAdapter<BaseComment, RecyclerView.ViewHolder>(commentComparator) {

    private var isLoggedInUser: Boolean = false

    fun setIsLoggedIn(isLogin: Boolean) {
        isLoggedInUser = isLogin
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Comment -> VIEW_TYPE_COMMENT
            is BlockedComment -> VIEW_TYPE_BLOCKED
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_COMMENT -> ForumDetailCommentViewHolder.create(
                LayoutInflater.from(parent.context),
                parent,
                imageLoader
            )
            VIEW_TYPE_BLOCKED -> ForumDetailBlockedCommentViewHolder.create(
                LayoutInflater.from(parent.context),
                parent
            )
            else -> throw IllegalArgumentException("unknown view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is ForumDetailCommentViewHolder -> holder.bind(getItem(position) as Comment, isLoggedInUser)
            is ForumDetailBlockedCommentViewHolder -> holder.bind(getItem(position) as BlockedComment)
            else -> throw IllegalArgumentException("unknown view type")
        }

    companion object {
        private const val VIEW_TYPE_COMMENT = 0
        private const val VIEW_TYPE_BLOCKED = 1

        private val commentComparator = object : DiffUtil.ItemCallback<BaseComment>() {
            override fun areItemsTheSame(oldItem: BaseComment, newItem: BaseComment): Boolean =
                when (oldItem) {
                    is Comment -> oldItem.id == (newItem as Comment).id
                    is BlockedComment -> oldItem == newItem
                }

            override fun areContentsTheSame(oldItem: BaseComment, newItem: BaseComment): Boolean =
                when (oldItem) {
                    is Comment -> oldItem.id == (newItem as Comment).id
                    is BlockedComment -> oldItem == newItem
                }
        }
    }
}
