package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.BlockedEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

class ForumListAdapter(
    private val imageLoader: ImageLoader,
    private val onForumClicked: (EveryoneParkForum) -> Unit
) : ListAdapter<BaseEveryoneParkForum, RecyclerView.ViewHolder>(forumComparator) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is EveryoneParkForum -> VIEW_TYPE_CONTENT
            is BlockedEveryoneParkForum -> VIEW_TYPE_BLOCKED
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
            is ForumListViewHolder -> holder.bind(getItem(position) as EveryoneParkForum)
            is BlockedForumListViewHolder -> holder.bind(getItem(position) as BlockedEveryoneParkForum)
            else -> throw IllegalArgumentException("unknown view type")
        }

    companion object {
        private const val VIEW_TYPE_CONTENT = 0
        private const val VIEW_TYPE_BLOCKED = 1

        private val forumComparator = object : DiffUtil.ItemCallback<BaseEveryoneParkForum>() {
            override fun areItemsTheSame(oldItem: BaseEveryoneParkForum, newItem: BaseEveryoneParkForum): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BaseEveryoneParkForum, newItem: BaseEveryoneParkForum): Boolean =
                oldItem.id == newItem.id
        }
    }
}
