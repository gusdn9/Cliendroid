package com.hyunwoo.cliendroid.presentation.fragment.search.type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentTypeSearchBinding
import com.hyunwoo.cliendroid.domain.model.search.type.SearchType
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeItem
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import com.hyunwoo.cliendroid.extension.navigateGraph
import com.hyunwoo.cliendroid.extension.toFragmentArgsBundle
import com.hyunwoo.cliendroid.presentation.fragment.forum.detail.ForumDetailArgs
import javax.inject.Inject

class SearchTypeFragment : AppFragment() {

    private var _binding: FragmentTypeSearchBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(SearchTypeViewModel::class)

    @Inject
    lateinit var viewModelFactory: SearchTypeViewModel.Factory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val searchListAdapter by lazy {
        SearchTypeListAdapter(imageLoader, this::onSearchItemClicked)
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

        viewModel.onEach(State::keyword, State::searchType) { keyword, _ ->
            binding.searchView.setQuery(keyword, false)
            viewModel.refresh()
        }

        viewModel.onEach(State::searchResultList) { searchList ->
            searchListAdapter.submitList(searchList)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTypeSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initViews() {
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

        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            val stringArray = resources.getStringArray(R.array.search_type_value)
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                SearchType.getValueOf(stringArray[position])?.let { viewModel.setType(it) }
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

    private fun onSearchItemClicked(searchItem: SearchTypeItem) {
        val args = ForumDetailArgs(searchItem.link)
        navigateGraph(
            R.id.action_searchFragment_to_forumDetailFragment,
            args.toFragmentArgsBundle()
        )
    }
}
