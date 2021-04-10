package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentEveryoneParkListBinding
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import javax.inject.Inject

class EveryoneParkListFragment : AppFragment() {

    private var _binding: FragmentEveryoneParkListBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(EveryoneParkListViewModel::class)

    @Inject
    lateinit var viewModelFactory: EveryoneParkListViewModel.Factory

    private val adapter by lazy {
        ForumListAdapter(this::onForumClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeStates()
    }

    private fun subscribeStates() {
        viewModel.onEach(State::listData) { forumList ->
            adapter.submitList(forumList ?: emptyList())
        }

        viewModel.onEach(State::listDataRefreshAsync) { async ->
            binding.refreshLayout.isRefreshing = async is Loading
        }

        viewModel.onEach(State::listDataLoadMoreAsync) { async ->
            isProgressDialogVisible = async is Loading
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEveryoneParkListBinding.inflate(inflater, container, false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onForumClicked(forum: EveryoneParkForum) {
        // TODO click 처리
        Log.d("###Test", "click")
    }
}
