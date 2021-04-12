package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentEveryoneParkDetailBinding
import com.hyunwoo.cliendroid.extension.getColorWithAttr
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

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null

    private val onBackPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            bottomSheetBehavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            onBackPressedCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeStates()
    }

    private fun subscribeStates() {
        viewModel.onEach(State::htmlBody) { body ->
            if (body == null) return@onEach

            val colorOnSurface = "#%x".format(requireContext().getColorWithAttr(R.attr.colorOnSurface))
            val html = "<html><head>" +
                    "<meta name='viewport' content='width=device-width, user-scalable=no' />" +
                    "<style>" +
                    "img, video, span, iframe{display: inline;height: auto;max-width: 100%;} " +
                    "body { color: $colorOnSurface; } " +
                    "</style>" +
                    "</head><body>$body</body></html>"

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
        initListeners()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetFrame)
        bottomSheetBehavior?.addBottomSheetCallback(bottomSheetCallback)

        binding.WebView.setBackgroundColor(Color.TRANSPARENT)
        with(binding.WebView.settings) {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING

            builtInZoomControls = false
            setSupportZoom(true)
        }
        binding.replyRecyclerView.adapter = adapter
        binding.replyRecyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.replyRecyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    private fun initListeners() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onDestroyView() {
        bottomSheetBehavior?.removeBottomSheetCallback(bottomSheetCallback)
        super.onDestroyView()
        _binding = null
    }
}
