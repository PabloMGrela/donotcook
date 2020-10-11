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
                setOnClickListener {
                    onClickListener.onClick(
                        restaurant,
                        restaurantImage,
                        restaurantLogo,
                        restaurantName,
                        restaurantDistance,
                        restaurantRating,
                        restaurantPrice
                    )
                }
                restaurantName.transitionName = restaurant.name
                restaurantLogo.transitionName = restaurant.logo
                restaurantImage.transitionName = restaurant.image
                restaurantRating.transitionName = restaurant.rating.toString()
                restaurantDistance.transitionName = restaurant.distance
                restaurantPrice.transitionName = restaurant.price.toString()
            }
        }
    }

    class OnClickListener(val clickListener: (RestaurantViewModel, ImageView, ImageView, TextView, TextView, TextView, TextView) -> Unit) {
        fun onClick(
            restaurant: RestaurantViewModel,
            iconImageView: ImageView,
            logoImageView: ImageView,
            title: TextView,
            distance: TextView,
            rating: TextView,
            price: TextView
        ) = clickListener(restaurant, iconImageView, logoImageView, title, distance, rating, price)
    }

}