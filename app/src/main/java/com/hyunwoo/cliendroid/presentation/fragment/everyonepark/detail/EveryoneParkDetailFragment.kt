package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentEveryoneParkDetailBinding
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import javax.inject.Inject

class EveryoneParkDetailFragment : AppFragment() {

    private var _binding: FragmentEveryoneParkDetailBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(EveryoneParkDetailViewModel::class)

    @Inject
    lateinit var viewModelFactory: EveryoneParkDetailViewModel.Factory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val adapter by lazy {
        EveryoneParkDetailCommentAdapter(imageLoader)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeStates()
    }

    private fun subscribeStates() {
        viewModel.onEach(State::htmlBody) { html ->
            if (html == null) return@onEach
            binding.WebView.loadData(html, "text/html", "utf-8")
        }

        viewModel.onEach(State::comments) { comments ->
            adapter.submitList(comments)
        }

        viewModel.onEach(State::refreshAsync) { async ->
            isProgressDialogVisible = async is Loading
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEveryoneParkDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        // binding.WebView.setBackgroundColor(Color.TRANSPARENT)
        with(binding.WebView.settings) {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true

            builtInZoomControls = false
            setSupportZoom(false)
        }
        binding.replyRecyclerView.adapter = adapter
        binding.replyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.replyRecyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
