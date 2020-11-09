package com.grela.clean.profile.managerestaurants

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.R
import com.grela.clean.databinding.FragmentManageRestaurantsBinding
import com.grela.clean.mainlist.RestaurantAdapter
import com.grela.clean.setSingleClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class ManageRestaurantsFragment : Fragment() {
    private lateinit var binding: FragmentManageRestaurantsBinding
    private val viewModel: ManageRestaurantsViewModel by viewModel()
    private val restaurantListener =
        RestaurantAdapter.OnClickListener { restaurant, imageView, logoImage, nameView, distanceView, ratingView, priceView, eurImage ->
            val direction: NavDirections =
                ManageRestaurantsFragmentDirections.manageToEdit(restaurant)

//            val extras = FragmentNavigatorExtras(
//                imageView to restaurant.image,
//                logoImage to restaurant.logo,
//                nameView to restaurant.name,
//                distanceView to restaurant.distance,
//                ratingView to restaurant.rating.toString(),
//                priceView to restaurant.price.toString(),
//                eurImage to restaurant.address
//            )

            findNavController().navigate(direction)
        }
    private val adapter = RestaurantAdapter(restaurantListener)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageRestaurantsBinding.inflate(layoutInflater)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(150, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
        binding.manageRestaurantTopBar.setNavigationOnClickListener {
            goBack()
        }
        binding.manageRestaurantList.adapter = adapter
        binding.manageRestaurantList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) {
                    binding.manageRestaurantAddButton.extend()
                } else {
                    binding.manageRestaurantAddButton.shrink()
                }
            }
        })
        binding.manageRestaurantAddButton.setSingleClickListener {
            findNavController().navigate(R.id.manageToAddRestaurant)
        }
        viewModel.restaurantList.observe(this) {
            adapter.updateData(it)
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}