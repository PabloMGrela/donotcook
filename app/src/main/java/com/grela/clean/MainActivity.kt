package com.grela.clean

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.libraries.maps.model.LatLng
import com.grela.clean.AppModules.injectActivity
import com.grela.clean.mainlist.RestaurantAdapter
import com.grela.clean.mainlist.toRestaurantViewModelEntityList
import com.grela.domain.model.RestaurantDomainEntity
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
    }

    override fun showError() {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }

    override fun showRestaurants(r: List<RestaurantDomainEntity>) {
        adapter.updateData(r.toRestaurantViewModelEntityList(myLocation))
    }
}