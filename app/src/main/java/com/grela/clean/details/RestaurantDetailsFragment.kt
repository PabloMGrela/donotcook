package com.grela.clean.details

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grela.clean.databinding.FragmentRestaurantDetailsBinding
import com.grela.clean.mainlist.RestaurantViewModel
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


class RestaurantDetailsFragment : Fragment() {
    private lateinit var sportsArgs: RestaurantViewModel
    private lateinit var binding: FragmentRestaurantDetailsBinding
    private lateinit var adapter: MenuAdapter
    private var phoneNumber = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantDetailsBinding.inflate(layoutInflater)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })
        binding.detailsTopBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        val args = requireArguments()
        sportsArgs = RestaurantDetailsFragmentArgs.fromBundle(args).selectedRestaurant
        with(sportsArgs) {
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
            adapter.updateData(sportsArgs.menus[0].sections)
            binding.detailsImage.transitionName = image
            binding.detailsLogo.transitionName = logo
            binding.detailsName.transitionName = name
            binding.detailsDistance.transitionName = distance
            binding.restaurantRating.transitionName = rating.toString()
            binding.detailsPrice.transitionName = price.toString()
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

}