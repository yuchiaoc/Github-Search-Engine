package com.yuchiaoc.githubapidemo.model

import com.google.gson.annotations.SerializedName

data class TextMatch(
    @SerializedName("object_url") val urlString: String,
    @SerializedName("object_type") val type: String, //User
    val property: String, //name
    @SerializedName("fragment") val name: String
)