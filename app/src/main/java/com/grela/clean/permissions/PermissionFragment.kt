package com.grela.clean.permissions

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grela.clean.R
import com.grela.clean.databinding.FragmentPermissionBinding
import com.grela.clean.setSingleClickListener
import com.grela.clean.splash.SplashFragment.Companion.FIRST_TIME_SHARED

class PermissionFragment : Fragment() {

    companion object {
        const val LOCATION_PERMISSION = 1222
    }

    private lateinit var binding: FragmentPermissionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPermissionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.permissionButton.setSingleClickListener {
            makeRequest()
        }
    }

    private fun makeRequest() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION
        )
        requireContext().getSharedPreferences(FIRST_TIME_SHARED, MODE_PRIVATE).edit()
            .putBoolean(FIRST_TIME_SHARED, false).apply()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION -> {
                findNavController().navigate(R.id.permissionToHome)
            }
        }
    }
}