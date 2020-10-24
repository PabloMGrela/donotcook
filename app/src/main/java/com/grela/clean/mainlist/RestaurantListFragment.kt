package com.grela.clean.mainlist

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.model.LatLng
import com.grela.clean.databinding.FragmentRestaurantsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class RestaurantListFragment : Fragment() {
    private lateinit var adapter: RestaurantAdapter
    private lateinit var binding: FragmentRestaurantsBinding
    private val viewModel: HomeViewModel by viewModel()
    var restaurantList = listOf<RestaurantViewModel>()
    private var userLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        viewModel.onCreated()
        binding.restaurantList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        adapter = RestaurantAdapter(sportsItemListener)
        getUserLocation()
        binding.restaurantList.adapter = adapter
        viewModel.restaurantList.observeForever {
            restaurantList = it.toRestaurantViewModelEntityList(userLocation)
            adapter.updateData(restaurantList)
        }

    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(requireActivity()).lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        userLocation = LatLng(it.latitude, it.longitude)
                    }
                }
        }
    }

    private val sportsItemListener =
        RestaurantAdapter.OnClickListener { sports, imageView, logoImage, nameView, distanceView, ratingView, priceView, eurImage ->
            val direction: NavDirections =
                RestaurantListFragmentDirections.homeToDetails(sports)

            val extras = FragmentNavigatorExtras(
                imageView to sports.image,
                logoImage to sports.logo,
                nameView to sports.name,
                distanceView to sports.distance,
                ratingView to sports.rating.toString(),
                priceView to sports.price.toString(),
                eurImage to sports.address
            )
//            val geo = Geocoder(requireContext(), Locale.getDefault())
//            val addresses = geo.getFromLocationName(sports.address, 1)
//            val address: Address = addresses[0]
//            val longitude: Double = address.longitude
//            val latitude: Double = address.latitude
//
//            Log.d("geocoder", "$latitude , $longitude")

            findNavController().navigate(direction, extras)
        }
}