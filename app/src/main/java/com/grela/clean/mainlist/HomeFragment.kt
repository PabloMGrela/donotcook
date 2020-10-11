package com.grela.clean.mainlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.maps.model.LatLng
import com.grela.clean.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var adapter: RestaurantAdapter
    private val viewModel: HomeViewModel by viewModel()

    private val myLocation = LatLng(43.3688638, -8.4345315)
    private lateinit var binding: FragmentHomeBinding
    var restaurantList = listOf<RestaurantViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.restaurantList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        viewModel.onCreated()
        adapter = RestaurantAdapter(sportsItemListener)
        binding.restaurantList.adapter = adapter
        viewModel.restaurantList.observeForever {
            restaurantList = it.toRestaurantViewModelEntityList(myLocation)
            adapter.updateData(restaurantList)
        }
    }

    private val sportsItemListener =
        RestaurantAdapter.OnClickListener { sports, imageView, logoImage, nameView, distanceView, ratingView, priceView, eurImage ->
            val direction: NavDirections =
                HomeFragmentDirections.homeToDetails(sports)

            val extras = FragmentNavigatorExtras(
                imageView to sports.image,
                logoImage to sports.logo,
                nameView to sports.name,
                distanceView to sports.distance,
                ratingView to sports.rating.toString(),
                priceView to sports.price.toString(),
                eurImage to sports.address
            )

            findNavController().navigate(direction, extras)
        }

}