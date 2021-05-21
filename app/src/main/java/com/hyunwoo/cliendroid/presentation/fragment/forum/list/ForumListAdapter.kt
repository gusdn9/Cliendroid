package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.hyunwoo.cliendroid.domain.model.forum.BaseForum
import com.hyunwoo.cliendroid.domain.model.forum.BlockedForum
import com.hyunwoo.cliendroid.domain.model.forum.Forum

class ForumListAdapter(
    private val imageLoader: ImageLoader,
    private val onForumClicked: (Forum) -> Unit
) : ListAdapter<BaseForum, RecyclerView.ViewHolder>(forumComparator) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is Forum -> VIEW_TYPE_CONTENT
            is BlockedForum -> VIEW_TYPE_BLOCKED
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_CONTENT -> ForumListViewHolder.create(
                LayoutInflater.from(parent.context),
                parent,
                imageLoader,
                onForumClicked
            )
            VIEW_TYPE_BLOCKED -> BlockedForumListViewHolder.create(
                LayoutInflater.from(parent.context),
                parent
            )
            else -> throw IllegalArgumentException("unknown view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is ForumListViewHolder -> holder.bind(getItem(position) as Forum)
            is BlockedForumListViewHolder -> holder.bind(getItem(position) as BlockedForum)
            else -> throw IllegalArgumentException("unknown view type")
        }

    companion object {
        private const val VIEW_TYPE_CONTENT = 0
        private const val VIEW_TYPE_BLOCKED = 1

        private val forumComparator = object : DiffUtil.ItemCallback<BaseForum>() {
            override fun areItemsTheSame(oldItem: BaseForum, newItem: BaseForum): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BaseForum, newItem: BaseForum): Boolean =
                oldItem.id == newItem.id
        }
    }
}
