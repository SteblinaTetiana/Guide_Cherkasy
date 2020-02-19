package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.content.Context
import android.os.Bundle
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
import kotlinx.android.synthetic.main.list_item.*

@Suppress("UNREACHABLE_CODE")
class PlacesListFragment : Fragment()/*, View.OnClickListener */ {

    private lateinit var mAdapter: PlacesAdapter
    private lateinit var clickListener: PlacesAdapter.OnItemClickListener
    private val listViewModel by activityViewModels<PlacesListViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        clickListener = context as PlacesAdapter.OnItemClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        listViewModel.places.observe(viewLifecycleOwner, Observer<List<Place>> {
            mAdapter = PlacesAdapter(listViewModel.places.value!!, clickListener)
            recyclerView.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(activity)
            }
            mAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_list, container, false)

        /*cardView_places.setOnClickListener(this)*/
    }

    /* override fun onClick(view: View) {
         listViewModel.list(view)
     }*/

    /* companion object {
         fun newInstance() = PlacesListFragment()
     }*/

}
