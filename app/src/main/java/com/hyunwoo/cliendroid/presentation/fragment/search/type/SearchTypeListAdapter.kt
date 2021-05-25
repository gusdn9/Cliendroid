package com.hyunwoo.cliendroid.presentation.fragment.search.type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.hyunwoo.cliendroid.domain.model.search.type.BaseSearchTypeItem
import com.hyunwoo.cliendroid.domain.model.search.type.BlockedSearchTypeItem
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeItem

class SearchTypeListAdapter(
    private val imageLoader: ImageLoader,
    private val onSearchItemClicked: (SearchTypeItem) -> Unit
) : ListAdapter<BaseSearchTypeItem, RecyclerView.ViewHolder>(searchComparator) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is SearchTypeItem -> VIEW_TYPE_CONTENT
            is BlockedSearchTypeItem -> VIEW_TYPE_BLOCKED
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_CONTENT -> SearchTypeListViewHolder.create(
                LayoutInflater.from(parent.context),
                parent,
                imageLoader,
                onSearchItemClicked
            )
            VIEW_TYPE_BLOCKED -> BlockedSearchTypeListViewHolder.create(
                LayoutInflater.from(parent.context),
                parent
            )
            else -> throw IllegalArgumentException("unknown view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is SearchTypeListViewHolder -> holder.bind(getItem(position) as SearchTypeItem)
            is BlockedSearchTypeListViewHolder -> holder.bind(getItem(position) as BlockedSearchTypeItem)
            else -> throw IllegalArgumentException("unknown view type")
        }

    companion object {
        private const val VIEW_TYPE_CONTENT = 0
        private const val VIEW_TYPE_BLOCKED = 1

        private val searchComparator = object : DiffUtil.ItemCallback<BaseSearchTypeItem>() {
            override fun areItemsTheSame(oldItem: BaseSearchTypeItem, newItem: BaseSearchTypeItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BaseSearchTypeItem, newItem: BaseSearchTypeItem): Boolean =
                oldItem.id == newItem.id
        }
    }
}
