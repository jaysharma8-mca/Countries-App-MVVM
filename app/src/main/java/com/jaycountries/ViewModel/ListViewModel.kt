package com.jaycountries.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaycountries.Di.DaggerApiComponent
import com.jaycountries.Model.CountriesService
import com.jaycountries.Model.CountryNameModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//LiveData is a variable that any one can subscribe to and can access the data in real time.
//i.e. when this variable updates then all the subscribers will get notified for the change.
class ListViewModel: ViewModel() {

    @Inject
    lateinit var countriesService: CountriesService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val disposable = CompositeDisposable()

    //As defined in Model package
    val countries = MutableLiveData<List<CountryNameModel>>()

    //Will notify if any error is there or not while loading the data
    val countryLoadError = MutableLiveData<Boolean>()

    //Will notify whether the ViewModel is in the process of loading the data from backend
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchCountries()
    }

    //This is the actual function which calls the data but since we do not want to expose it so
    //we call it inside refresh() as refresh() is public method
    private fun fetchCountries(){
        loading.value = true
        disposable.add(
            countriesService.getCountries()
                .subscribeOn(Schedulers.newThread()) //does processing on different thread and shows data on main thread
                .observeOn(AndroidSchedulers.mainThread()) //main thread
                .subscribeWith(object :DisposableSingleObserver<List<CountryNameModel>>(){
                    override fun onSuccess(value: List<CountryNameModel>) {
                        countries.value = value
                        countryLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        countryLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}