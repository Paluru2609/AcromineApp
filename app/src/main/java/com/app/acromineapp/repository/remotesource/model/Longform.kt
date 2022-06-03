package com.app.acromineapp.repository.remotesource.model

import com.google.gson.annotations.SerializedName

data class LongForm (
    @SerializedName("lf")
    val longForm: String,
    @SerializedName("freq")
    val frequency: Int,
    val since: Int,
    @SerializedName("vars")
    val variations: List<Variation>
)