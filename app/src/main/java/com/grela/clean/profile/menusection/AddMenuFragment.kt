package com.grela.clean.profile.menusection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grela.clean.databinding.FragmentAddMenuBinding
import com.grela.clean.mainlist.MenuViewModel
import com.grela.clean.setSingleClickListener
import java.text.SimpleDateFormat
import java.util.*

class AddMenuFragment : Fragment() {
    private lateinit var binding: FragmentAddMenuBinding
    private var menu: MenuViewModel? = null
    private var selectedDate = 0L
    private val plates = listOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = AddMenuFragmentArgs.fromBundle(requireArguments())
        menu = args.selectedMenu
        selectedDate = args.selectedDate
        with(binding) {
            menu?.let {
                addMenuPrice.text = it.price.toString()
                addMenuDessert.isChecked = it.hasDessert
                addMenuDrink.isChecked = it.hasDrink
                addMenuBread.isChecked = it.hasBread
            }
            if (selectedDate != 0L) addMenuTopBar.title =
                SimpleDateFormat("dd-MMM-yyyy").format(Date(selectedDate))
            addMenuButton.setSingleClickListener {
                if (!(addMenuPrice.text.isBlank() || plates.isEmpty())) {
                    //upload menu
                    Toast.makeText(requireContext(), "Right menu", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Incorrect menu", Toast.LENGTH_SHORT).show()
                }
            }
            addMenuTopBar.setNavigationOnClickListener { goBack() }
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
}

