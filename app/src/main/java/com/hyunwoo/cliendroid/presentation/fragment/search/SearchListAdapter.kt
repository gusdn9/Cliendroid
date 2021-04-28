package com.hyunwoo.cliendroid.presentation.fragment.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.ImageLoader
import com.hyunwoo.cliendroid.domain.model.SearchItem

class SearchListAdapter(
    private val imageLoader: ImageLoader,
    private val onSearchItemClicked: (SearchItem) -> Unit
) : ListAdapter<SearchItem, SearchListViewHolder>(searchComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder =
        SearchListViewHolder.create(LayoutInflater.from(parent.context), parent, imageLoader, onSearchItemClicked)

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {

        private val searchComparator = object : DiffUtil.ItemCallback<SearchItem>() {
            override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
                oldItem.link == newItem.link

            override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean =
                oldItem.link == newItem.link
        }
    }
}
