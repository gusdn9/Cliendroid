package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

class Adapter(
    private val onForumClicked: (EveryoneParkForum) -> Unit
) : ListAdapter<EveryoneParkForum, ForumListViewHolder>(forumComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumListViewHolder =
        ForumListViewHolder.create(LayoutInflater.from(parent.context), parent, onForumClicked)

    override fun onBindViewHolder(holder: ForumListViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val forumComparator = object : DiffUtil.ItemCallback<EveryoneParkForum>() {
            override fun areItemsTheSame(oldItem: EveryoneParkForum, newItem: EveryoneParkForum): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: EveryoneParkForum, newItem: EveryoneParkForum): Boolean =
                oldItem == newItem
        }
    }
}
