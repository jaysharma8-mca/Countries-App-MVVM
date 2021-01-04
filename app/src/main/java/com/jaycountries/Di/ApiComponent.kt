package com.jaycountries.Di

import com.jaycountries.Model.CountriesService
import com.jaycountries.ViewModel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service: CountriesService)

    fun inject(viewModel: ListViewModel) //where we need to inject
}