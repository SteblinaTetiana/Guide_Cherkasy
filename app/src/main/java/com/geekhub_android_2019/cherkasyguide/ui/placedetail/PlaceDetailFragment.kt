package com.geekhub_android_2019.cherkasyguide.ui.placedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.geekhub_android_2019.cherkasyguide.R

class PlaceDetailFragment : Fragment() {

    val placeArgs by navArgs<PlaceDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("placeArgs", "${placeArgs.place}")
        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }
}
