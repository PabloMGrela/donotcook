package com.grela.clean.profile.menusection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.grela.clean.databinding.FragmentAddMenuOverviewBinding
import com.grela.clean.mainlist.MenuViewModel
import com.grela.clean.mainlist.RestaurantViewModel
import com.grela.clean.setSingleClickListener
import java.util.*

class AddMenuOverviewFragment : Fragment() {
    private lateinit var binding: FragmentAddMenuOverviewBinding

    private lateinit var restaurant: RestaurantViewModel
    private lateinit var adapter: MenuOverviewAdapter
    private val dayMenus = mutableListOf<MenuViewModel>()
    private var selectedMenu: MenuViewModel? = null
    private var selectedDate = Date()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMenuOverviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val calendar = Calendar.getInstance()
        adapter = MenuOverviewAdapter(object : MenuOverviewAdapter.OnMenuClickedListener {
            override fun onMenuClicked(menu: MenuViewModel) {
                selectedMenu = menu
                navigateToEdit()
            }
        })
        restaurant = AddMenuOverviewFragmentArgs.fromBundle(requireArguments()).selectedRestaurant
        with(binding) {
            addMenuMenuList.adapter = adapter
            addMenuCalendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
                dayMenus.clear()
                restaurant.menus.forEach {
                    if (it.date == "$year-${month + 1}-$dayOfMonth") {
                        dayMenus.add(it)
                    }
                }
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                adapter.updateData(dayMenus)
            }
            addMenuAddButton.setSingleClickListener {
                val direction: NavDirections =
                    AddMenuOverviewFragmentDirections.actionAddMenuOverviewFragmentToAddMenuFragment(
                        selectedMenu, selectedDate.time
                    )
                findNavController().navigate(direction)
            }
            addMenuOverviewTopBar.setNavigationOnClickListener {
                goBack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        })
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun navigateToEdit() {
        val direction: NavDirections =
            AddMenuOverviewFragmentDirections.actionAddMenuOverviewFragmentToAddMenuFragment(
                selectedMenu, selectedDate.time
            )
        findNavController().navigate(direction)
    }
}