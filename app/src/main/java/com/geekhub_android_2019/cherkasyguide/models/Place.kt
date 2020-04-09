package com.geekhub_android_2019.cherkasyguide.models

import com.google.firebase.firestore.*
import java.io.Serializable

data class Place(
    @DocumentId
    val id: String? = null,
    val name: String? = null,
    @Transient val location: GeoPoint? = null,
    val description: String? = null,
    val photoSmallUrl: String? = null,
    val photoLargeUrl: List<String>? = null
) : Serializable
