package com.grela.clean.profile.menusection


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.R
import com.grela.clean.mainlist.MenuViewModel
import com.grela.clean.setSingleClickListener
import kotlinx.android.synthetic.main.menu_row.view.*

class MenuOverviewAdapter(val listener: OnMenuClickedListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            ITEM_TYPE -> createMenuViewHolder(container, inflater)
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


    private fun createMenuViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        RestaurantViewHolder(inflater.inflate(R.layout.menu_row, parent, false))

    inner class RestaurantViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(menu: MenuViewModel) {
            view.setSingleClickListener {
                listener.onMenuClicked(menu)
            }
            with(itemView) {
                menuName.text = "${menu.name} - ${menu.price}"
            }
        }
    }

    interface OnMenuClickedListener {
        fun onMenuClicked(menu: MenuViewModel)
    }
}