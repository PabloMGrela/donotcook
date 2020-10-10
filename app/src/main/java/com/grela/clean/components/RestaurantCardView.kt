package com.grela.clean.components

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.grela.clean.R
import com.squareup.picasso.Picasso
import kotlin.math.roundToInt

class RestaurantCardView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {
    
    val restaurantName: TextView by lazy { findViewById<TextView>(R.id.restaurantName) }
    val restaurantDistance: TextView by lazy { findViewById<TextView>(R.id.restaurantDistance) }
    val restaurantRating: TextView by lazy { findViewById<TextView>(R.id.restaurantRating) }
    val restaurantPrice: TextView by lazy { findViewById<TextView>(R.id.restaurantPrice) }
    val restaurantLogo: ImageView by lazy { findViewById<ImageView>(R.id.restaurantLogo) }
    val restaurantImage: ImageView by lazy { findViewById<ImageView>(R.id.restaurantImage) }


    var name: String? = null
        set(value) {
            field = value
            restaurantName.text = name
        }

    var distance: String? = null
        set(value) {
            field = value
            if (value?.isNotBlank() == true) {
                restaurantDistance.text = value
            } else {
                restaurantDistance.text = "-"
            }
        }

    var logo: String? = null
        set(value) {
            field = value
            if (value != null) {
                Picasso.get()
                    .load(value)
                    .error(R.drawable.aliadie)
                    .into(restaurantLogo)
            } else {
                restaurantLogo.setImageResource(R.drawable.aliadie)
            }
        }

    var image: String? = null
        set(value) {
            field = value
            if (image != null) {
                Picasso.get()
                    .load(value)
                    .error(R.drawable.aliadie)
                    .into(restaurantImage)
            } else {
                restaurantImage.setImageResource(R.drawable.aliadie)
            }
        }

    var rating: Float = 0f
        set(value) {
            field = value
            restaurantRating.text = when (rating.roundToInt()) {
                0 -> "☆☆☆☆☆"
                1 -> "★☆☆☆☆"
                2 -> "★★☆☆☆"
                3 -> "★★★☆☆"
                4 -> "★★★★☆"
                5 -> "★★★★★"
                else -> "-"
            }
        }

    var price: Float = 0f
        set(value) {
            field = value
            restaurantPrice.text = "$price"
        }

    init {
        inflate(context, R.layout.restaurant_card_view_layout, this)
    }
}
