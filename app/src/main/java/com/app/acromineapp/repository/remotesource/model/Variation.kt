package com.app.acromineapp.repository.remotesource.model

import com.google.gson.annotations.SerializedName

data class Variation (
    @SerializedName("lf")
    val longForm: String,
    @SerializedName("freq")
    val frequency: Int,
    val since: Int
)