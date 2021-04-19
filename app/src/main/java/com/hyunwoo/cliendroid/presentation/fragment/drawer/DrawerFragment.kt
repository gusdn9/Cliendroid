package com.hyunwoo.cliendroid.presentation.fragment.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentDrawerBinding
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import javax.inject.Inject

class DrawerFragment : AppFragment() {

    private var _binding: FragmentDrawerBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(DrawerViewModel::class)

    @Inject
    lateinit var viewModelFactory: DrawerViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeStates()
    }

    private fun subscribeStates() {
        viewModel.onEach(State::loggedInUser) { user ->
            binding.loggedInLayout.isVisible = user != null
            binding.notLoggedInLayout.isVisible = user == null
            binding.userId.text = user?.userId
        }

        viewModel.onEach(State::loginAsync) { async ->
            isProgressDialogVisible = async is Loading
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
        }

        binding.logoutButton.setOnClickListener {
        }
    }
}
