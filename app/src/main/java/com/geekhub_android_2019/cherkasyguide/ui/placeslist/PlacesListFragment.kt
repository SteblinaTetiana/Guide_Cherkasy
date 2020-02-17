package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place

class PlacesListFragment : Fragment(), View.OnClickListener {

    private lateinit var mAdapter: PlacesAdapter
    private lateinit var place: List<Place>
    private lateinit var clickListener: PlacesAdapter.OnItemClickListener
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
        mAdapter = PlacesAdapter(place, clickListener)
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        mAdapter.notifyDataSetChanged()
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val listViewModel: PlacesListViewModel by viewModels()
        listViewModel.getPlace().observe(this, Observer<List<Place>> { place ->


        })
        /* listViewModel.loadData()*/
    }

    override fun onClick(view: View) {
        listViewModel.list(view)
    }
/*    companion object {
        fun newInstance() = PlacesListFragment()
    }*/

}
