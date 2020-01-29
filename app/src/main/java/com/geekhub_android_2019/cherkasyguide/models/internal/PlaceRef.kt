package com.geekhub_android_2019.cherkasyguide.models.internal

import com.google.firebase.firestore.*

data class PlaceRef(
    @DocumentId
    val id: String? = null,
    val placeRef: DocumentReference? = null
)
