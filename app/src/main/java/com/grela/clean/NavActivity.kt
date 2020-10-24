package com.grela.clean

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.grela.clean.databinding.ActivityNavBinding

class NavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = this.findNavController(R.id.navHostFragmentContainer)
        binding.navHostBottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.permissionFragment -> {
                    binding.navHostBottomNavigation.gone()
                    binding.navToolbar.gone()
                }
                R.id.restaurantDetailsFragment -> {
                    binding.navHostBottomNavigation.gone()
                    binding.navToolbar.decreaseAndGone()
                }
                else -> {
                    binding.navHostBottomNavigation.visible()
                    binding.navToolbar.visible()
                }
            }
        }
    }

    override fun onSupportNavigateUp() =
        findNavController(R.id.navHostFragmentContainer).navigateUp()
}

private fun MaterialToolbar.decreaseAndGone() {
    this.animate().y(0f).setListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            this@decreaseAndGone.gone()
        }
    })
}
