package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.domain.model.EveryOneParkForum

class Adapter(
    private val onForumClicked: (EveryOneParkForum) -> Unit
): ListAdapter<EveryOneParkForum, RecyclerView.ViewHolder>(forumComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val forumComparator = object : DiffUtil.ItemCallback<EveryOneParkForum>() {
            override fun areItemsTheSame(oldItem: EveryOneParkForum, newItem: EveryOneParkForum): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: EveryOneParkForum, newItem: EveryOneParkForum): Boolean =
                oldItem == newItem
        }
    }
}
