package com.grela.clean.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.R
import com.grela.clean.mainlist.MenuViewModel
import kotlinx.android.synthetic.main.menu_layout.view.*

class MenuAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE = 1
    }

    init {
        setHasStableIds(true)
    }

    private var list = mutableListOf<MenuViewModel>()

    fun updateData(list: List<MenuViewModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(container.context)
        return when (viewType) {
            ITEM_TYPE -> createCountryViewHolder(container, inflater)
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
            is RestaurantViewHolder -> holder.bind(list[position])
        }
    }


    private fun createCountryViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        RestaurantViewHolder(inflater.inflate(R.layout.menu_layout, parent, false))

    inner class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(menu: MenuViewModel) {
            with(itemView) {
                detailMenuName.text =
                    "${menu.price}€\n" + if (menu.dessertAndCoffee) "\uD83E\uDD67 & ☕" else "🥧 o ☕"
                detailMenuList.adapter = DetailMenuAdapter(menu.sections)
            }
        }
    }

}