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
import androidx.recyclerview.widget.LinearLayoutManager
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
        val args = requireArguments()
        sportsArgs = RestaurantDetailsFragmentArgs.fromBundle(args).selectedRestaurant
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
        binding.detailsTopBar.setNavigationOnClickListener {
            goBack()
        }
        with(sportsArgs) {
            binding.detailsName.text = name
            binding.detailsDistance.text = distance
            Picasso.get().load(logo).into(binding.detailsLogo)
            Picasso.get().load(image).into(binding.detailsImage)
            binding.detailsPrice.text = price.toString()
            binding.detailsAddress.text = address
            binding.detailsAddress.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${latLng.latitude}:${latLng.longitude}")
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = gmmIntentUri
                }
                startActivity(intent)
            }
            binding.restaurantRating.text = when (rating.roundToInt()) {
                0 -> "☆☆☆☆☆"
                1 -> "★☆☆☆☆"
                2 -> "★★☆☆☆"
                3 -> "★★★☆☆"
                4 -> "★★★★☆"
                5 -> "★★★★★"
                else -> "-"
            }

            adapter = MenuAdapter()
            with(binding) {
                detailsPhone.animate().alpha(1f).apply { duration = 1300 }.start()
                detailsAddress.animate().alpha(1f).apply { duration = 1300 }.start()
                detailsPhone.setOnClickListener {
                    phoneNumber = phone
                    requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 200)
                }
                restaurantMenuList.adapter = adapter
                adapter.updateData(sportsArgs.menus)
                if (sportsArgs.menus.size == 1) {
                    restaurantMenuList.layoutManager = LinearLayoutManager(requireContext())
                }
                detailsImage.transitionName = image
                detailsLogo.transitionName = logo
                detailsName.transitionName = name
                detailsDistance.transitionName = distance
                restaurantRating.transitionName = rating.toString()
                detailsPrice.transitionName = price.toString()
                resEur.transitionName = address
            }
        }
        binding.restaurantMenuList.animate().alpha(1f).apply { duration = 1200 }.start()
    }

    private fun goBack() {
        findNavController().popBackStack()
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