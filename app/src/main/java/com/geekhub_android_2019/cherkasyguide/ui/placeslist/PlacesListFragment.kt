package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_places_list.view.*


class PlacesListFragment : Fragment(), PlacesAdapter.OnItemClickListener {

    private lateinit var mAdapter: PlacesAdapter
    private val listViewModel by activityViewModels<PlacesListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLayout = inflater.inflate(R.layout.fragment_places_list, container, false)
        val navController = NavHostFragment.findNavController(this)
        val bottomBar = view?.findViewById<NavigationView>(R.id.bottom_nav_view)
        bottomBar?.setupWithNavController(navController)
        fragmentLayout.bottom_nav_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map_nav -> {
                    println("map pressed")
                    listViewModel.replaceFragment(this.requireView())
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        return fragmentLayout
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
    }
}
