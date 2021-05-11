package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import coil.load
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentForumDetailBinding
import com.hyunwoo.cliendroid.domain.model.BaseComment
import com.hyunwoo.cliendroid.domain.model.ForumContent
import com.hyunwoo.cliendroid.extension.getColorWithAttr
import javax.inject.Inject

class ForumDetailFragment : AppFragment() {

    private var _binding: FragmentForumDetailBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(ForumDetailViewModel::class)

    @Inject
    lateinit var viewModelFactory: ForumDetailViewModel.Factory

    @Inject
    lateinit var imageLoader: ImageLoader

    private val adapter by lazy {
        ForumDetailCommentAdapter(imageLoader)
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
        viewModel.onEach(State::content) { content ->
            if (content == null) return@onEach
            setHeader(content)
            setHtmlBody(content.htmlBody)
            setComments(content.comments)
        }

        viewModel.onEach(State::refreshAsync) { async ->
            binding.refreshLayout.isRefreshing = async is Loading
        }

        viewModel.onEach(State::loggedInUser) { loggedInUser ->
            adapter.setIsLoggedIn(loggedInUser != null)
        }
    }

    private fun setHeader(content: ForumContent) {
        binding.title.text = content.title
        binding.user.userNickname.isVisible = content.user.nickName != null
        binding.user.userNickname.text = content.user.nickName
        binding.user.userImage.isVisible = content.user.image != null
        binding.user.userImage.load(content.user.image, imageLoader)
        binding.time.text = content.time
        binding.hits.text = content.hits
        binding.likes.isVisible = content.likes > 0
        binding.likes.text = content.likes.toString()
    }

    private fun setHtmlBody(htmlBody: String) {
        val ctx = context ?: return

        val hexColor = "#" + Integer.toHexString(ctx.getColorWithAttr(R.attr.colorOnSurface)).substring(2)
        val html = "<html><head>" +
                "<meta name='viewport' content='width=device-width, user-scalable=no' />" +
                "<style>" +
                "img, video, iframe {display: inline;height: auto;max-width: 100%;} " +
                "body { color: $hexColor; } " +
                "</style>" +
                "</head><body>$htmlBody</body></html>"
        // color를 hex 값으로 넣었을 경우 일부 디바이스에서는 loadData에서 정상적으로 동작하지 않는 이슈가 있어서 loadDataWithBaseURL를 사용
        binding.WebView.loadDataWithBaseURL(
            null,
            html,
            "text/html; charset=utf-8",
            "utf-8",
            null
        )
    }

    private fun setComments(comments: List<BaseComment>?) {
        val replyCnt = comments?.size ?: 0

        binding.replyCount.text = replyCnt.toString()
        adapter.submitList(comments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentForumDetailBinding.inflate(inflater, container, false)
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

        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
        binding.bottomSheetTopFrame.setOnClickListener {
            if (bottomSheetBehavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    override fun onDestroyView() {
        bottomSheetBehavior?.removeBottomSheetCallback(bottomSheetCallback)
        super.onDestroyView()
        _binding = null
    }
}
