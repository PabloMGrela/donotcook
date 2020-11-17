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
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.R
import com.grela.clean.databinding.FragmentManageRestaurantsBinding
import com.grela.clean.mainlist.RestaurantAdapter
import com.grela.clean.mainlist.RestaurantViewModel
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

            val extras = FragmentNavigatorExtras(
                imageView to "${restaurant.image}image",
                logoImage to "${restaurant.logo}logo",
                nameView to "${restaurant.name}name",
                distanceView to "${restaurant.distance}distance",
                ratingView to "${restaurant.rating}rating",
                priceView to "${restaurant.price}price"
            )

            findNavController().navigate(direction, extras)
        }
    private lateinit var adapter: RestaurantAdapter
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
        adapter = RestaurantAdapter(restaurantListener, object : RestaurantAdapter.OnEditMenuClickListener{
            override fun onEditRestaurantClicked(restaurant: RestaurantViewModel) {
                val direction: NavDirections =
                    ManageRestaurantsFragmentDirections.manageToEditMenu(restaurant)
                findNavController().navigate(direction)
            }

        })
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