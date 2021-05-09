package com.hyunwoo.cliendroid.presentation.fragment.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hyunwoo.cliendroid.domain.model.MenuBoardItem

class MenuListAdapter(
    private val onItemClicked: (MenuBoardItem) -> Unit
) : ListAdapter<MenuBoardItem, MenuListViewHolder>(menuComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuListViewHolder =
        MenuListViewHolder.create(LayoutInflater.from(parent.context), parent, onItemClicked)

    override fun onBindViewHolder(holder: MenuListViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val menuComparator = object : DiffUtil.ItemCallback<MenuBoardItem>() {
            override fun areItemsTheSame(oldItem: MenuBoardItem, newItem: MenuBoardItem): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MenuBoardItem, newItem: MenuBoardItem): Boolean =
                oldItem == newItem
        }
    }
}
