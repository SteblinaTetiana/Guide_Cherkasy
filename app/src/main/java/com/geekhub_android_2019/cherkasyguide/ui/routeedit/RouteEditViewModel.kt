package com.geekhub_android_2019.cherkasyguide.ui.routeedit

import androidx.lifecycle.*
import com.geekhub_android_2019.cherkasyguide.common.AppDispatchers
import com.geekhub_android_2019.cherkasyguide.common.Limits
import com.geekhub_android_2019.cherkasyguide.common.EventChannel
import com.geekhub_android_2019.cherkasyguide.data.Repository
import com.geekhub_android_2019.cherkasyguide.models.Place
import com.geekhub_android_2019.cherkasyguide.models.UserRoute
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class RouteEditViewModel(
    private val repo: Repository,
    private val dispatchers: AppDispatchers
) : ViewModel() {

    val warn = EventChannel<Messages>()

    val state: LiveData<ViewState> by lazy {
        val placesF = repo.getPlaces().conflate()
        val userRouteF = repo.getUserRouteOrNUll().conflate()

        placesF
            .combine(userRouteF) { places, userRoute ->
                ViewState(places, userRoute)
            }
            .flowOn(dispatchers.io)
            .conflate()
            .asLiveData(viewModelScope.coroutineContext)
    }

    fun toggleCheck(place: Place) = viewModelScope.launch(dispatchers.io) {
        val (id, selected) = state.value?.userRoute ?: UserRoute()

        val newPlaces =
            if (selected.contains(place)) selected - place
            else selected + place

        if (newPlaces.size > Limits.MAX_PLACES)
            warn.offer(Messages.ROUTE_TO_LONG)
        else
            UserRoute(id, newPlaces)
                .also { repo.updateUserRoute(it) }
    }

    fun clearUserRoute() = viewModelScope.launch {
        repo.clearUserRoute()
    }
}
