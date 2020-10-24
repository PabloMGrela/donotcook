package com.grela.clean.splash

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grela.clean.R
import com.grela.clean.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    companion object {
        const val FIRST_TIME_SHARED = "isFirstTime"
    }

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFirstTime =
            requireContext().getSharedPreferences(FIRST_TIME_SHARED, Context.MODE_PRIVATE)
                .getBoolean(FIRST_TIME_SHARED, true)
        if (isFirstTime) {
            checkLocationPermission()
        } else {
            goToMain()
        }
    }

    private fun checkLocationPermission() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            findNavController().navigate(R.id.splashToPermission)
        } else {
            goToMain()
        }
    }

    private fun goToMain() {
        if (findNavController().currentDestination?.id == R.id.splashFragment) {
            findNavController().navigate(R.id.splashToHome2)
        }
    }
}