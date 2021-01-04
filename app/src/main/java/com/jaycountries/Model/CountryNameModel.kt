package com.jaycountries.Model

import com.google.gson.annotations.SerializedName

//String countryName can be null
data class CountryNameModel(
    @SerializedName("name")
    val countryName: String?,
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flagPNG")
    val flag: String?
)