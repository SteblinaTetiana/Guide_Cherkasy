package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class PlacesListViewModel : ViewModel() {

    val _place: MutableLiveData<List<Place>> by lazy {
        MutableLiveData<List<Place>>().also {
            loadData()
        }
    }

    fun getPlace(): LiveData<List<Place>> {
        return _place
    }


    private fun loadData() {
        CoroutineScope(Dispatchers.Default).launch {
            FirebaseAuth.getInstance()
                .signInAnonymously()
                .await()

            val remoteDB = FirebaseFirestore.getInstance()

            remoteDB.collection("places")
                .get()
                .addOnSuccessListener { snapshot ->

                    val place = mutableListOf<Place>()
                    for (placeSnapshot in snapshot) {
                        place.add(placeSnapshot.toObject(Place::class.java))
                    }
                    _place.value = place
                }
                .addOnFailureListener { exception ->
                }
//             .await()
            /*.zip(generateSequence(100) { it + 100 }.asIterable())
            .forEach { (snapshot) ->
                Log.d("List place", "${snapshot.id} => ${snapshot.data}")*/

        }
    }

    fun list(view: View) {
        when (view.id) {
            R.id.recycler_view ->
                view.findNavController().navigate(R.id.action_placesListFragment_to_placeDetailFragment)

        }
    }
}




