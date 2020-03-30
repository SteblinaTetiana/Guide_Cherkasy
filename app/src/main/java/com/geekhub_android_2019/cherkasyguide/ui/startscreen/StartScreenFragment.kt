package com.geekhub_android_2019.cherkasyguide.ui.startscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.geekhub_android_2019.cherkasyguide.NetHelper
import com.geekhub_android_2019.cherkasyguide.R
import kotlinx.android.synthetic.main.fragment_start_screen.*
import java.util.Calendar.getInstance

class StartScreenFragment() : Fragment(R.layout.fragment_start_screen), View.OnClickListener {

    private val startScreenViewModel
            by activityViewModels<StartScreenViewModel>()

//    val netHelper = NetHelper(context!!)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView_what_to_see.setOnClickListener(this)
        cardView_routes.setOnClickListener(this)
    }

    override fun onClick(view: View) {
//        if(NetHelper.getInstance(application = activity!!.application).isOverWifi){
//        if(netHelper.isNetworkAvailable(context!!)){
            startScreenViewModel.select(view)


    }

}

