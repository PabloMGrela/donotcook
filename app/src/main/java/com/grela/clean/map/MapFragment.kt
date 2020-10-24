package com.grela.clean.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.MarkerOptions
import com.grela.clean.R
import com.grela.clean.databinding.FragmentMapBinding
import com.grela.clean.mainlist.RestaurantListFragment.Companion.restaurantList
import com.grela.clean.mainlist.RestaurantListFragment.Companion.userLocation


class MapFragment : Fragment() {
    lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            mMap = it

            userLocation?.let { currentLatLng ->
                val cameraPosition =
                    CameraPosition.builder().zoom(13f).target(currentLatLng).build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
            restaurantList.forEach { restaurant ->
                mMap.addMarker(MarkerOptions().position(restaurant.latLng).title(restaurant.name))
            }
        }
    }
}