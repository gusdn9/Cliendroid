package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentEveryoneParkListBinding
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import javax.inject.Inject

class EveryoneParkListFragment : AppFragment() {

    private var _binding: FragmentEveryoneParkListBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(EveryoneParkListViewModel::class)

    @Inject
    lateinit var viewModelFactory: EveryoneParkListViewModel.Factory

    private val adapter by lazy {
        Adapter(this::onForumClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeStates()
    }

    private fun subscribeStates() {
        viewModel.onEach(State::listData) { forumList ->
            adapter.submitList(forumList ?: emptyList())
        }

        viewModel.onEach(State::getListAsync) { async ->
            binding.refreshLayout.isRefreshing = async is Loading
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEveryoneParkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
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

    private fun onForumClicked(forum: EveryoneParkForum) {
        // TODO click 처리
    }
}
