package com.geekhub_android_2019.cherkasyguide.ui.placeslist

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.geekhub_android_2019.cherkasyguide.R
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class PlacesListViewModel : ViewModel() {

    private val _places = MutableLiveData<List<Place>>()

    val places: LiveData<List<Place>> by lazy {
        loadAllPlaces()
        _places
    }

    private fun loadAllPlaces() =
        viewModelScope.launch(Dispatchers.IO) {
            FirebaseAuth.getInstance()
                .signInAnonymously()
                .await()

            Firebase.firestore
                .collection("places")
                .get()
                .await()
                .toObjects<Place>()
                .also(_places::postValue)
        }

    /*   fun list(view: View) {
           when (view.id) {
               R.id.cardView_places ->
                   view.findNavController().navigate(R.id.action_placesListFragment_to_placeDetailFragment)
           }
       }*/
}





