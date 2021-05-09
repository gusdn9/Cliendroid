package com.hyunwoo.cliendroid.presentation.fragment.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppFragment
import com.hyunwoo.cliendroid.common.exception.ViewBindingException
import com.hyunwoo.cliendroid.databinding.FragmentDrawerBinding
import com.hyunwoo.cliendroid.domain.exception.LoginFailedException
import com.hyunwoo.cliendroid.domain.model.MenuBoardItem
import com.hyunwoo.cliendroid.extension.isProgressDialogVisible
import com.hyunwoo.cliendroid.extension.navigateGraph
import javax.inject.Inject

class DrawerFragment : AppFragment() {

    private var _binding: FragmentDrawerBinding? = null
    private val binding get() = _binding ?: throw ViewBindingException()

    private val viewModel by fragmentViewModel(DrawerViewModel::class)

    @Inject
    lateinit var viewModelFactory: DrawerViewModel.Factory

    private val menuAdapter by lazy {
        MenuListAdapter(::onMenuItemClicked)
    }

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

        viewModel.onEach(State::menuList) { menus ->
            menuAdapter.submitList(menus)
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

        initViews()
        initListeners()
    }

    private fun initViews() {
        binding.recyclerView.adapter = menuAdapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                LinearLayoutManager.VERTICAL
            )
        )
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

        binding.settingsButton.setOnClickListener {
            navigateMenu(R.id.action_to_settingsFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateMenu(actionId: Int, args: Bundle? = null) {
        navigateGraph(actionId, args)
        (activity as? Callback)?.onMenuCLicked()
    }

    private fun onMenuItemClicked(item: MenuBoardItem) {
    }

    interface Callback {
        fun onMenuCLicked()
    }
}
