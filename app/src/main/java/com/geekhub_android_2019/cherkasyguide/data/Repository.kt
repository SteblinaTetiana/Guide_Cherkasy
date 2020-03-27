package com.geekhub_android_2019.cherkasyguide.data

import com.geekhub_android_2019.cherkasyguide.common.Collection
import com.geekhub_android_2019.cherkasyguide.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import kotlin.reflect.jvm.internal.impl.load.java.Constant

class Repository {

    private val rootRef by lazy {
        Firebase.firestore
    }

    fun getPlaceById(id: String): Flow<Place> =
        rootRef.collection(Collection.PLACES).document(id).asFlow()

    fun getPlaces(): Flow<List<Place>> = rootRef.collection(Collection.PLACES).asFlow()

    fun getRoutes(): Flow<List<Route>> =
        rootRef.collection(Collection.ROUTES)
            .asFlow<Internal.Route>()
            .transformLatest { rawRoutes ->
                // Emit routes without places
                emit(rawRoutes.map { Route(it.id, it.name) })

                // Construct routes with places
                val routesFlows: List<Flow<Route>> = rawRoutes.map { rawRoute ->
                    fetchRoutePlaces(rawRoute)
                }

                // Finally, emit routes with places
                combine(routesFlows) { newRoutes: Array<Route> ->
                    newRoutes.toList()
                }.also {
                    emitAll(it)
                }
            }

    fun getUserRouteOrNUll(): Flow<UserRoute?> =
        rootRef.collection(Collection.USER_ROUTES)
            .document(FirebaseAuth.getInstance().uid!!)
            .asNullableFlow<Internal.UserRoute>()
            .transformLatest { rawRoute ->
                if (rawRoute == null)
                    emit(null)
                else {
                    fetchPlaces(rawRoute.places).map { places ->
                        UserRoute(rawRoute.id, places)
                    }.also { emitAll(it) }
                }
            }


    suspend fun updateUserRoute(r: UserRoute) {
        val uid = FirebaseAuth.getInstance().uid!!
        val placeRefs = r.places.map {
            rootRef.collection(Collection.USER_ROUTES).document(it.id!!)
        }

        rootRef.collection(Collection.USER_ROUTES)
            .document(uid)
            .set(Internal.UserRoute(r.id, placeRefs))
            .await()
    }

    private fun fetchPlaces(places: List<DocumentReference>): Flow<List<Place>> =
        if (places.isNotEmpty())
            rootRef.collection(Collection.PLACES)
                .whereIn(FieldPath.documentId(), places.map { it.id })
                .asFlow()
        else
            flowOf(listOf())

    private fun fetchRoutePlaces(r: Internal.Route): Flow<Route> =
        fetchPlaces(r.places).map { Route(r.id, r.name, it) }
}
