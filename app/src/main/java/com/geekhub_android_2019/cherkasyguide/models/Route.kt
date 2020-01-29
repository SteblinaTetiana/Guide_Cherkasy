package com.geekhub_android_2019.cherkasyguide.models

import com.google.firebase.firestore.*

data class Route(
    @DocumentId
    val id: String? = null,
    val name: String? = null
)

