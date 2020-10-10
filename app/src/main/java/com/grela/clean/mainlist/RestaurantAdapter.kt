package com.grela.clean.mainlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.R
import com.grela.clean.components.RestaurantCardView
import kotlinx.android.synthetic.main.title_row.view.*

class RestaurantAdapter(
    val itemClickListener: (RestaurantViewModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TITLE_TYPE = 0
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
        val inflater = LayoutInflater.from(container.context)
        return when (viewType) {
            TITLE_TYPE -> createTitleViewHolder(container, inflater)
            ITEM_TYPE -> createCountryViewHolder(container)
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = list.size + 1

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TITLE_TYPE
            else -> ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind()
            is RestaurantViewHolder -> holder.bind(list[position - 1])
        }
    }

    private fun createTitleViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        TitleViewHolder(
            inflater.inflate(
                R.layout.title_row, parent, false
            )
        )

    class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind() {
            itemView.title.text = itemView.context.getString(R.string.restaurants)
        }
    }

    private fun createCountryViewHolder(parent: ViewGroup) =
        RestaurantViewHolder(RestaurantCardView(parent.context))

    inner class RestaurantViewHolder(view: RestaurantCardView) : RecyclerView.ViewHolder(view) {

        fun bind(restaurant: RestaurantViewModel) {
            with(itemView as RestaurantCardView) {
                name = restaurant.name
                distance = restaurant.distance
                logo = restaurant.logo
                image = restaurant.image
                price = restaurant.price
                rating = restaurant.rating
                setOnClickListener { itemClickListener(restaurant) }
            }
        }
    }

}