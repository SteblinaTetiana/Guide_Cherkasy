package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place


class PlacesListFragment : Fragment(), PlacesAdapter.OnItemClickListener {

    private lateinit var mAdapter: PlacesAdapter
    private val listViewModel by activityViewModels<PlacesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        listViewModel.places.observe(viewLifecycleOwner, Observer<List<Place>> {
            mAdapter = PlacesAdapter(listViewModel.places.value!!, clickListener = this)
            recyclerView.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(activity)
            }
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onClick(place: Place) {
        listViewModel.list(this.requireView(), place)
        Log.d("onClick", "$place")
    }
}
