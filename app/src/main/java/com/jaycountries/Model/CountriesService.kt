package com.jaycountries.Model


import com.jaycountries.Di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class CountriesService {

    @Inject
    lateinit var api: CountriesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCountries(): Single<List<CountryNameModel>>{
        return api.getCountries() //Get method called from CountriesApi interface
    }
}