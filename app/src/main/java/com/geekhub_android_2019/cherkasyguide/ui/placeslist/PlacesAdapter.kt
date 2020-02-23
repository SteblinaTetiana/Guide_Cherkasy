package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import kotlinx.android.synthetic.main.list_item.view.*


class PlacesAdapter(var place: List<Place>, val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    interface OnItemClickListener {
        fun onClick(place: Place)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return PlacesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return place.size
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
        holder.bind(place[position])
    }


    inner class PlacesViewHolder(private val view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        fun bind(place: Place) {
            view.textViewName.text = place.name
            Glide.with(this.view)
                .load(place.photoSmallUrl)
                .into(view.imageViewPlaceIcon)
        }

        override fun onClick(v: View?) {
            clickListener.onClick(place[adapterPosition])
            Log.d("onClick", "click")
        }
    }
}
