package com.hyunwoo.cliendroid.presentation.fragment.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentDrawerBinding
import com.hyunwoo.cliendroid.domain.exception.LoginFailedException
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

        viewModel.onAsync(
            State::loginAsync,
            uniqueOnly("loginAsync"),
            onFail = { exception ->
                when (exception) {
                    is LoginFailedException ->
                        Toast.makeText(context, R.string.navigation_drawer_login_failed, Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDrawerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListeners() {
        binding.loginButton.setOnClickListener {
            if (validateId().not() || validatePassword().not()) return@setOnClickListener
            
            val id = binding.userIdInput.text.toString()
            val password = binding.userPasswordInput.text.toString()
            binding.userIdInput.setText("")
            binding.userPasswordInput.setText("")
            viewModel.login(id, password)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun validateId(): Boolean {
        val id = binding.userIdInput.text.toString()

        if (id.isEmpty()) {
            binding.userIdInputLayout.error = getString(R.string.navigation_drawer_login_id_error_empty)
            return false
        }
        binding.userIdInputLayout.error = null
        return true
    }

    private fun validatePassword(): Boolean {
        val password = binding.userPasswordInput.text.toString()

        if (password.isEmpty()) {
            binding.userPasswordInputLayout.error = getString(R.string.navigation_drawer_login_password_error_empty)
            return false
        }
        binding.userPasswordInputLayout.error = null
        return true
    }
}
