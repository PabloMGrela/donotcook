package com.grela.clean

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.maps.model.LatLng
import com.grela.clean.AppModules.injectActivity
import com.grela.clean.mainlist.RestaurantAdapter
import com.grela.clean.mainlist.RestaurantViewModel
import com.grela.domain.model.CountryDomainEntity
import com.grela.presentation.MainPresenter
import com.grela.presentation.MainViewTranslator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainViewTranslator {

    private val presenter: MainPresenter by injectActivity()
    private lateinit var adapter: RestaurantAdapter
    val maps = "AIzaSyDcufkbUcDPuX8_deSDI5UpAMK9z9XBqDM"
    val mapbox =
        "pk.eyJ1IjoicGdyZWxhIiwiYSI6ImNrZzNlODVkdTA5NTIycWw2czBtNzBtMmYifQ.mWk9ZfSIsK4qN2gAkA4tCA"

    val aliaDieCoords = LatLng(43.3695643, -8.4021057)
    val almaNegraCoords = LatLng(43.3712745, -8.3994435)
    val myLocation = LatLng(43.3688638, -8.4345315)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onCreate()
        adapter = RestaurantAdapter {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
        }
        restaurantList.adapter = adapter

        val restaurantList = listOf<RestaurantViewModel>(
            RestaurantViewModel(
                "Alia Die",
                "https://imgur.com/dJOvyWO.png",
                "https://imgur.com/KmljY4e.png",
                10f,
                myLocation.getDistanceString(aliaDieCoords),
                3f
            ),
            RestaurantViewModel(
                "Aroma",
                "https://imgur.com/R4pox2e.png",
                "https://imgur.com/R4pox2e.png",
                10f,
                myLocation.getDistanceString(aliaDieCoords),
                4f
            ), RestaurantViewModel(
                "Alma Negra",
                "https://imgur.com/u4Cvc5v.png",
                "https://imgur.com/u4Cvc5v.png",
                9.95f,
                myLocation.getDistanceString(almaNegraCoords),
                5f
            )
        )
        adapter.updateData(restaurantList.reversed())
    }

    override fun showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    override fun showCountry(r: List<CountryDomainEntity>) {
//        adapter.updateData(r.toCountryViewModelList())
    }
}