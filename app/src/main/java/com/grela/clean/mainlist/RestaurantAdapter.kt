package com.grela.clean.mainlist

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.components.RestaurantCardView

class RestaurantAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var list = mutableListOf<RestaurantViewModel>()

    fun updateData(list: List<RestaurantViewModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE -> createCountryViewHolder(container)
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemId(position: Int): Long = list[position].name.hashCode().toLong()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            else -> ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RestaurantViewHolder -> holder.bind(list[position], onClickListener)
        }
    }

    private fun createCountryViewHolder(parent: ViewGroup) =
        RestaurantViewHolder(RestaurantCardView(parent.context))

    inner class RestaurantViewHolder(view: RestaurantCardView) : RecyclerView.ViewHolder(view) {

        fun bind(restaurant: RestaurantViewModel, onClickListener: OnClickListener) {
            with(itemView as RestaurantCardView) {
                name = restaurant.name
                distance = restaurant.distance
                logo = restaurant.logo
                image = restaurant.image
                price = restaurant.price
                rating = restaurant.rating
                if (restaurant.isFav) {
                    restaurantFav.progress = 1f
                } else {
                    restaurantFav.progress = 0f
                }
                setOnClickListener {
                    onClickListener.onClick(
                        restaurant,
                        restaurantImage,
                        restaurantLogo,
                        restaurantName,
                        restaurantDistance,
                        restaurantRating,
                        restaurantPrice,
                        restaurantEuro
                    )
                }
                restaurantName.transitionName = "${restaurant.name}name"
                restaurantLogo.transitionName = "${restaurant.logo}logo"
                restaurantImage.transitionName = "${restaurant.image}image"
                restaurantRating.transitionName = "${restaurant.rating}rating"
                restaurantDistance.transitionName = "${restaurant.distance}distance"
                restaurantPrice.transitionName = "${restaurant.price}price"
                restaurantEuro.transitionName = "${restaurant.address}address"
            }
        }
    }

    class OnClickListener(val clickListener: (RestaurantViewModel, ImageView, ImageView, TextView, TextView, TextView, TextView, ImageView) -> Unit) {
        fun onClick(
            restaurant: RestaurantViewModel,
            iconImageView: ImageView,
            logoImageView: ImageView,
            title: TextView,
            distance: TextView,
            rating: TextView,
            price: TextView,
            eurImage: ImageView
        ) = clickListener(
            restaurant,
            iconImageView,
            logoImageView,
            title,
            distance,
            rating,
            price,
            eurImage
        )
    }

}