package com.hyunwoo.cliendroid.presentation.fragment.search.type

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.databinding.ItemCommonListBinding
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeItem
import com.hyunwoo.cliendroid.extension.getColorWithAttr

class SearchTypeListViewHolder private constructor(
    private val binding: ItemCommonListBinding,
    private val imageLoader: ImageLoader,
    private val onSearchItemClicked: (SearchTypeItem) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val color = binding.root.context.getColorWithAttr(R.attr.colorSecondary)

    fun bind(searchItem: SearchTypeItem) {
        binding.root.setOnClickListener {
            onSearchItemClicked(searchItem)
        }
        // max 2000
        val hit = searchItem.hits
        var alpha = hit / MAX_HIT
        alpha = if (alpha > 1f) 1f else alpha

        binding.hits.setBackgroundColor(color)
        binding.hits.alpha = alpha

        binding.boardName.isVisible = true
        binding.boardName.text = searchItem.board

        binding.title.text = searchItem.title
        binding.time.text = searchItem.time

        binding.user.userNickname.isVisible = searchItem.user.nickName != null
        binding.user.userNickname.text = searchItem.user.nickName

        binding.user.userImage.isVisible = searchItem.user.image != null
        binding.user.userImage.load(searchItem.user.image, imageLoader)

        binding.replyCount.isVisible = false
        binding.likes.isVisible = false
    }

    companion object {
        private const val MAX_HIT = 2000f

        fun create(
            inflater: LayoutInflater,
            parent: ViewGroup,
            imageLoader: ImageLoader,
            onSearchItemClicked: (SearchTypeItem) -> Unit
        ): SearchTypeListViewHolder =
            SearchTypeListViewHolder(
                ItemCommonListBinding.inflate(inflater, parent, false),
                imageLoader,
                onSearchItemClicked
            )
    }
}
