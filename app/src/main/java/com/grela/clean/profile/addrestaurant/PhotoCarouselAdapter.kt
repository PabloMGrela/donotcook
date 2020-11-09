package com.grela.clean.profile.addrestaurant

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grela.clean.R
import com.grela.clean.setSingleClickListener
import kotlinx.android.synthetic.main.photo_row.view.*

class PhotoCarouselAdapter(val list: MutableList<Bitmap>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    fun addPhoto(bitmap: Bitmap) {
        list.add(bitmap)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(container.context)
        return createCountryViewHolder(container, inflater)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ImageViewHolder -> holder.bind(list[position])
        }
    }


    private fun createCountryViewHolder(parent: ViewGroup, inflater: LayoutInflater) =
        ImageViewHolder(inflater.inflate(R.layout.photo_row, parent, false))

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var isRowSelected = false
        fun bind(image: Bitmap) {
            with(itemView) {
                restaurantPhoto.setImageBitmap(image)
                setSingleClickListener {
                    if (isRowSelected) {
                        selectedPicture.setBackgroundResource(R.drawable.circle_shape_border_filled)
                    } else {
                        selectedPicture.setBackgroundResource(R.drawable.circle_shape_border)
                    }
                    isRowSelected = !isRowSelected
                }
            }
        }
    }

    interface OnImageSelected {
        fun onImageClicked()
    }

}