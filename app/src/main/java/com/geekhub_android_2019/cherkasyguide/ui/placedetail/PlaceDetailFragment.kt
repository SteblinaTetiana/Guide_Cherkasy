package com.geekhub_android_2019.cherkasyguide.ui.placedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.geekhub_android_2019.cherkasyguide.models.Place

class PlaceDetailFragment : Fragment() {

    companion object {
        fun newInstance(place:Place): PlaceDetailFragment =
            PlaceDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(PLACES_POS, place)
                }
            }
    }
}

const val PLACES_POS = "com.geekhub_android_2019.cherkasyguide.PlacesListFragmentPosition"
