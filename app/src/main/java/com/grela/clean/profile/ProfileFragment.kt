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
                ProfileStatus.LOGGED_IN -> showLoggedInLayout()
            }
        }
        binding.profileAddRestaurantRow.transitionName = getString(R.string.add_restaurant)
        binding.profileAddRestaurantRow.setSingleClickListener {
            findNavController().navigate(R.id.profileToAddRestaurant)
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
    }

    private fun onLoginClicked() {
        viewModel.onLoginClicked(
            binding.profileUsernameField.text,
            binding.profilePasswordField.text
        )
        binding.profilePasswordField.hideKeyboard()
    }

    private fun showLoggedInLayout() {
        binding.profileName.visible()
        binding.profileAddRestaurantRow.visible()
        binding.profileLogoutRow.visible()

        binding.profilePasswordField.gone()
        binding.profileUsernameField.gone()
        binding.profileLoginButton.gone()
    }

    private fun showLoggedOutLayout() {
        binding.profileName.gone()
        binding.profileAddRestaurantRow.gone()
        binding.profileLogoutRow.gone()

        binding.profilePasswordField.visible()
        binding.profileUsernameField.visible()
        binding.profileLoginButton.visible()
    }
}