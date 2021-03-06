package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.SnackbarHolder
import com.hyunwoo.cliendroid.common.error.snackbar.SnackbarErrorResolution
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentForumListBinding
import com.hyunwoo.cliendroid.domain.model.forum.Forum
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import com.hyunwoo.cliendroid.extension.navigateGraph
import com.hyunwoo.cliendroid.extension.toFragmentArgsBundle
import com.hyunwoo.cliendroid.presentation.fragment.forum.detail.ForumDetailArgs
import javax.inject.Inject

class ForumListFragment : AppFragment() {

    private var _binding: FragmentForumListBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(ForumListViewModel::class)

    @Inject
    lateinit var viewModelFactory: ForumListViewModel.Factory

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var snackbarErrorResolution: SnackbarErrorResolution

    private var scrollParcelable: Parcelable? = null

    private val snackbarHolder by lazy {
        SnackbarHolder.forFragment(this, R.id.rootFrameLayout)
    }

    private val adapter by lazy {
        ForumListAdapter(imageLoader, this::onForumClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        subscribeStates()
        savedInstanceState?.let {
            scrollParcelable = it.getParcelable(ARGS_SCROLL_STATE)
            it.clear()
        }
    }

    private fun subscribeStates() {
        viewModel.onEach(State::listData) { forumList ->
            adapter.submitList(forumList ?: emptyList())
            scrollParcelable?.let {
                binding.recyclerView.layoutManager?.onRestoreInstanceState(it)
                scrollParcelable = null
            }
        }

        viewModel.onEach(State::listDataRefreshAsync) { async ->
            binding.refreshLayout.isRefreshing = async is Loading
        }

        viewModel.onEach(State::listDataLoadMoreAsync) { async ->
            isProgressDialogVisible = async is Loading
        }

        viewModel.onAsync(
            State::listDataRefreshAsync,
            uniqueOnly("listDataRefreshAsync"),
            onFail = { throwable ->
                snackbarErrorResolution.resolve(this, snackbarHolder, throwable) {
                    viewModel.refresh()
                }
            }
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (scrollParcelable != null) {
            outState.putParcelable(ARGS_SCROLL_STATE, scrollParcelable)
            scrollParcelable = null
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentForumListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.recyclerView.adapter = adapter
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
    }

    override fun onPause() {
        super.onPause()
        scrollParcelable = binding.recyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onForumClicked(forum: Forum) {
        val args = ForumDetailArgs(forum.link)
        // TODO youtube ?????? ??????. ????????? ????????? ?????????
        // val args = ForumDetailArgs("service/board/park/16149846")
        navigateGraph(
            R.id.action_forumListFragment_to_forumDetailFragment,
            args.toFragmentArgsBundle()
        )
    }

    companion object {
        const val EXTRA_TITLE = "title"
        private const val ARGS_SCROLL_STATE = "recyclerState"
    }
}
