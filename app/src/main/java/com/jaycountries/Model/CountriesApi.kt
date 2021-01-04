package com.jaycountries.Model

import io.reactivex.Single
import retrofit2.http.GET

interface CountriesApi {
    @GET("DevTides/countries/master/countriesV2.json")
    fun getCountries(): Single<List<CountryNameModel>> //single is an observable that emits one variable and then closes
}