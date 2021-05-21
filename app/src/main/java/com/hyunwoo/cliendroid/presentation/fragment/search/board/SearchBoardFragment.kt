package com.hyunwoo.cliendroid.presentation.fragment.search.board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentBoardSearchBinding
import com.hyunwoo.cliendroid.domain.model.search.board.SearchItem
import com.hyunwoo.cliendroid.domain.model.search.board.SearchSort
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import com.hyunwoo.cliendroid.extension.navigateGraph
import com.hyunwoo.cliendroid.extension.toFragmentArgsBundle
import com.hyunwoo.cliendroid.presentation.fragment.forum.detail.ForumDetailArgs
import javax.inject.Inject

class SearchBoardFragment : AppFragment() {

    private var _binding: FragmentBoardSearchBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(SearchBoardViewModel::class)

    @Inject
    lateinit var viewModelFactory: SearchBoardViewModel.Factory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val boardAdapter by lazy {
        BoardSpinnerAdapter(requireContext())
    }

    private val searchListAdapter by lazy {
        SearchBoardListAdapter(imageLoader, this::onSearchItemClicked)
    }

    private val radioCheckedChangeListener = RadioGroup.OnCheckedChangeListener { _, checkedId ->
        when (checkedId) {
            R.id.sortRecency -> {
                viewModel.setSort(SearchSort.RECENCY)
            }
            R.id.sortAccuracy -> {
                viewModel.setSort(SearchSort.ACCURACY)
            }
        }
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
        _binding = FragmentBoardSearchBinding.inflate(inflater, container, false)
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

        binding.sortedRadioGroup.setOnCheckedChangeListener(radioCheckedChangeListener)

        binding.boardSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                viewModel.setBoardId(boardAdapter.getItem(position)?.id)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) = Unit
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    viewModel.setKeyword(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSearchItemClicked(searchItem: SearchItem) {
        val args = ForumDetailArgs(searchItem.link)
        navigateGraph(
            R.id.action_searchFragment_to_forumDetailFragment,
            args.toFragmentArgsBundle()
        )
    }
}
