package com.hyunwoo.cliendroid.presentation.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.tabs.TabLayout
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentSearchBinding
import com.hyunwoo.cliendroid.domain.model.SearchItem
import com.hyunwoo.cliendroid.domain.model.SearchSort
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import javax.inject.Inject

class SearchFragment : AppFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(SearchViewModel::class)

    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val boardAdapter by lazy {
        BoardSpinnerAdapter(requireContext())
    }

    private val searchListAdapter by lazy {
        SearchListAdapter(imageLoader, this::onSearchItemClicked)
    }

    private val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            when (tab?.text) {
                getString(R.string.search_sort_recency) -> {
                    viewModel.setSort(SearchSort.RECENCY)
                }
                getString(R.string.search_sort_accuracy) -> {
                    viewModel.setSort(SearchSort.ACCURACY)
                }
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeStates()
    }

    private fun subscribeStates() {
        viewModel.onEach(State::searchRefreshAsync) { async ->
            binding.refreshLayout.isRefreshing = async is Loading
        }

        viewModel.onEach(State::searchLoadMoreAsync) { async ->
            isProgressDialogVisible = async is Loading
        }

        viewModel.onEach(State::keyword, State::sort, State::sort, State::boardId) { keyword, _, _, _ ->
            binding.searchView.setQuery(keyword, false)
            viewModel.refresh()
        }

        viewModel.onEach(State::boardList) { boardList ->
            binding.boardSpinner.isVisible = boardList != null

            if (boardList == null) return@onEach
            boardAdapter.clear()
            boardAdapter.addAll(boardList)
        }

        viewModel.onEach(State::searchResultList) { searchList ->
            searchListAdapter.submitList(searchList)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.boardSpinner.adapter = boardAdapter
        binding.recyclerView.adapter = searchListAdapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun initListeners() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (binding.recyclerView.canScrollVertically(1).not()) {
                    viewModel.loadMore()
                }
            }
        })

        binding.tabLayout.addOnTabSelectedListener(tabSelectedListener)

        binding.boardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                viewModel.setBoardId(boardAdapter.getItem(position)?.id)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) = Unit
        }
    }

    override fun onDestroyView() {
        binding.tabLayout.removeOnTabSelectedListener(tabSelectedListener)
        super.onDestroyView()
        _binding = null
    }

    private fun onSearchItemClicked(searchItem: SearchItem) {
        // TODO 상세로 이동해야함.
    }
}
