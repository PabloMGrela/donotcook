package com.grela.clean.details

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grela.clean.R
import com.grela.clean.databinding.FragmentRestaurantDetailsBinding
import com.grela.clean.mainlist.HomeFragment.Companion.RESTAURANT_KEY
import com.grela.clean.mainlist.RestaurantViewModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class RestaurantDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantDetailsBinding
    private lateinit var adapter: MenuAdapter
    private var phoneNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailsTopBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val restaurant: RestaurantViewModel = arguments?.get(RESTAURANT_KEY) as RestaurantViewModel
        setSharedElementTransitionOnEnter()
        with(restaurant) {
            binding.detailsImage.transitionName = image
            binding.detailsName.text = name
            binding.detailsDistance.text = distance
            Picasso.get().load(logo).into(binding.detailsLogo)
            Picasso.get().load(image).into(binding.detailsImage)
            binding.detailsPrice.text = price.toString()
            binding.detailsAddress.text = address
            binding.restaurantRating.text = when (rating.roundToInt()) {
                0 -> "☆☆☆☆☆"
                1 -> "★☆☆☆☆"
                2 -> "★★☆☆☆"
                3 -> "★★★☆☆"
                4 -> "★★★★☆"
                5 -> "★★★★★"
                else -> "-"
            }
            binding.detailsPhone.setOnClickListener {
                phoneNumber = phone
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 200)

            }
            adapter = MenuAdapter()
            binding.restaurantMenuList.adapter = adapter
            adapter.updateData(restaurant.menus[0].sections)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200 && permissions.contentEquals(arrayOf(Manifest.permission.CALL_PHONE))) {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
            startActivity(intent)
        }
    }

    private fun setSharedElementTransitionOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(R.transition.shared_element_transition)
    }
}