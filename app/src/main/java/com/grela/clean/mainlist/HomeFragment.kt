package com.grela.clean.mainlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.maps.model.LatLng
import com.grela.clean.R
import com.grela.clean.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    companion object {
        const val RESTAURANT_KEY = "selectedRestaurant"
    }

    private lateinit var adapter: RestaurantAdapter
    private val viewModel: HomeViewModel by viewModel()

    private val myLocation = LatLng(43.3688638, -8.4345315)
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onCreated()
        adapter = RestaurantAdapter {
            val bundle = (bundleOf(RESTAURANT_KEY to it))
            findNavController().navigate(R.id.homeToDetails, bundle)
        }
        binding.restaurantList.adapter = adapter
        viewModel.restaurantList.observeForever {
            adapter.updateData(it.toRestaurantViewModelEntityList(myLocation))
        }
    }

}