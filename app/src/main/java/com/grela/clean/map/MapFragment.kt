package com.grela.clean.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.SupportMapFragment
import com.google.android.libraries.maps.model.CameraPosition
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.grela.clean.R
import com.grela.clean.databinding.FragmentMapBinding
import com.grela.clean.mainlist.RestaurantListFragment.Companion.restaurantList
import com.grela.clean.mainlist.RestaurantListFragment.Companion.userLocation
import com.grela.clean.mainlist.RestaurantViewModel
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt


class MapFragment : Fragment() {
    lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private var markerList = mutableMapOf<Marker, RestaurantViewModel>()

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
            mMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {

                override fun getInfoWindow(p0: Marker): View? = null

                // Defines the contents of the InfoWindow
                override fun getInfoContents(args: Marker): View? {

                    // Getting view from the layout file info_window_layout
                    val v: View = layoutInflater.inflate(R.layout.info_window_layout, null)
                    val selectedRestaurant: RestaurantViewModel? = markerList[args]
                    val title = v.findViewById(R.id.markerTitle) as TextView
                    val icon = v.findViewById(R.id.markerIcon) as ImageView
                    val markerRating = v.findViewById(R.id.markerRating) as TextView
                    selectedRestaurant?.let { restaurant ->
                        title.text = restaurant.name
                        Picasso.get().load(restaurant.logo).into(icon)
                        markerRating.text = when (restaurant.rating.roundToInt()) {
                            0 -> "☆☆☆☆☆"
                            1 -> "★☆☆☆☆"
                            2 -> "★★☆☆☆"
                            3 -> "★★★☆☆"
                            4 -> "★★★★☆"
                            5 -> "★★★★★"
                            else -> "-"
                        }
                    }
                    mMap.setOnInfoWindowClickListener {
                        selectedRestaurant?.let {
                            val direction: NavDirections =
                                MapFragmentDirections.mapToDetails(it)

                            findNavController().navigate(direction)
                        }
                    }

                    return v
                }
            })
            userLocation?.let { currentLatLng ->
                val cameraPosition =
                    CameraPosition.builder().zoom(15f).target(currentLatLng).build()
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            }
            if (markerList.isEmpty()) {
                restaurantList.forEach { restaurant ->
                    val marker = MarkerOptions().position(restaurant.latLng).title(restaurant.name)
                    markerList[mMap.addMarker(marker)] = restaurant
                }
            }
        }
    }

}