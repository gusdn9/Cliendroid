package com.hyunwoo.cliendroid.presentation.fragment.search.board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.hyunwoo.cliendroid.domain.model.search.board.BaseSearchItem
import com.hyunwoo.cliendroid.domain.model.search.board.BlockedSearchItem
import com.hyunwoo.cliendroid.domain.model.search.board.SearchItem

class SearchBoardListAdapter(
    private val imageLoader: ImageLoader,
    private val onSearchItemClicked: (SearchItem) -> Unit
) : ListAdapter<BaseSearchItem, RecyclerView.ViewHolder>(searchComparator) {

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is SearchItem -> VIEW_TYPE_CONTENT
            is BlockedSearchItem -> VIEW_TYPE_BLOCKED
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_CONTENT -> SearchBoardListViewHolder.create(
                LayoutInflater.from(parent.context),
                parent,
                imageLoader,
                onSearchItemClicked
            )
            VIEW_TYPE_BLOCKED -> BlockedSearchBoardListViewHolder.create(
                LayoutInflater.from(parent.context),
                parent
            )
            else -> throw IllegalArgumentException("unknown view type")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        when (holder) {
            is SearchBoardListViewHolder -> holder.bind(getItem(position) as SearchItem)
            is BlockedSearchBoardListViewHolder -> holder.bind(getItem(position) as BlockedSearchItem)
            else -> throw IllegalArgumentException("unknown view type")
        }

    companion object {
        private const val VIEW_TYPE_CONTENT = 0
        private const val VIEW_TYPE_BLOCKED = 1

        private val searchComparator = object : DiffUtil.ItemCallback<BaseSearchItem>() {
            override fun areItemsTheSame(oldItem: BaseSearchItem, newItem: BaseSearchItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BaseSearchItem, newItem: BaseSearchItem): Boolean =
                oldItem.id == newItem.id
        }
    }
}
