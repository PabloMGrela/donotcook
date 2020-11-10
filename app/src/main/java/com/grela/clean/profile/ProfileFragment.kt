package com.grela.clean.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.grela.clean.*
import com.grela.clean.databinding.FragmentProfileBinding
import com.grela.domain.model.UserRole
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        viewModel.profileStatus.observe(this) {
            when (it.status) {
                ProfileStatus.LOGGED_OUT -> showLoggedOutLayout()
                ProfileStatus.LOGGED_IN -> showLoggedInLayout(it.name, it.role)
            }
        }
        binding.profileManageRestaurant.setSingleClickListener {
            findNavController().navigate(R.id.profileToManage)
        }
        binding.profilePasswordField.apply {
            setOnEnterPressedListener { onLoginClicked() }
        }
        binding.profileLoginButton.setSingleClickListener {
            onLoginClicked()
        }

        binding.profileLogoutRow.setSingleClickListener {
            viewModel.onLogoutClicked()
        }

        binding.profileRegisterButton.setSingleClickListener {

        }
    }

    private fun onLoginClicked() {
        viewModel.onLoginClicked(
            binding.profileUsernameField.text,
            binding.profilePasswordField.text
        )
        binding.profilePasswordField.hideKeyboard()
    }

    private fun showLoggedInLayout(name: String, role: UserRole) {
        with(binding) {
            profileName.visible()
            profileLogoutRow.visible()
            profileName.text = name
            profileRegisterButton.gone()
            profilePasswordField.gone()
            profileUsernameField.gone()
            profileLoginButton.gone()
            if (role == UserRole.OWNER) {
                profileManageRestaurant.visible()
            }
        }

    }

    private fun showLoggedOutLayout() {
        with(binding) {
            profileName.gone()
            profileManageRestaurant.gone()
            profileLogoutRow.gone()
            profileRegisterButton.visible()
            profilePasswordField.visible()
            profileUsernameField.visible()
            profileLoginButton.visible()
        }
    }
}