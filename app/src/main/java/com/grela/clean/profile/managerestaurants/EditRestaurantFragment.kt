package com.grela.clean.profile.managerestaurants

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grela.clean.databinding.FragmentEditRestaurantBinding
import com.grela.clean.mainlist.RestaurantViewModel
import com.squareup.picasso.Picasso
import java.util.concurrent.TimeUnit

class EditRestaurantFragment : Fragment() {
    private lateinit var restaurant: RestaurantViewModel

    private lateinit var binding: FragmentEditRestaurantBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditRestaurantBinding.inflate(layoutInflater)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition(150, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restaurant = EditRestaurantFragmentArgs.fromBundle(requireArguments()).selectedRestaurant
        with(restaurant) {
            binding.apply {
                editRestaurantRestaurantName.text = name
                editRestaurantTopBar.title = name
                editRestaurantRestaurantAddress.text = address
                editRestaurantRestaurantPhone.text = phone
                editRestaurantRestaurantCoordinates.text = "${latLng.latitude},${latLng.longitude}"
                if (logo.isNotBlank()) {
                    Picasso.get()
                        .load(logo)
                        .into(editRestaurantLogoImage)
                }
                if (image.isNotBlank()) {
                    Picasso.get()
                        .load(image)
                        .into(editRestaurantHeaderImage)
                }

                editRestaurantHeaderImage.transitionName = "${image}image"
                editRestaurantLogoImage.transitionName = "${logo}logo"
                editRestaurantRestaurantCoordinates.transitionName = "${distance}distance"
                editRestaurantRestaurantAddress.transitionName = "${address}address"
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
        binding.editRestaurantTopBar.setNavigationOnClickListener {
            goBack()
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}