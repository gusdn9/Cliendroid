package com.hyunwoo.cliendroid.presentation.fragment.drawer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunwoo.cliendroid.databinding.ItemMenuListBinding
import com.hyunwoo.cliendroid.domain.model.MenuBoardItem

class MenuListViewHolder private constructor(
    private val binding: ItemMenuListBinding,
    private val onItemClicked: (MenuBoardItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MenuBoardItem) {
        binding.root.setOnClickListener {
            onItemClicked(item)
        }
        binding.menuText.text = item.name
    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            onItemClicked: (MenuBoardItem) -> Unit
        ): MenuListViewHolder =
            MenuListViewHolder(
                ItemMenuListBinding.inflate(inflater, parent, false),
                onItemClicked
            )
    }
}
