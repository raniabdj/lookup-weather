package com.example.lookupweather.viewmodel

import WeatherRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lookupweather.models.Temperature
import com.example.lookupweather.models.WeatherResponse
import com.example.lookupweather.utils.Status
import kotlinx.coroutines.*


class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var job: Job? = null

    private val _weatherLiveData = MutableLiveData<WeatherResponse>()
    val weatherLiveData: LiveData<WeatherResponse>
        get() = _weatherLiveData

    private val _currentWeatherLiveData = MutableLiveData<Temperature>()
    private val currentWeatherLiveData: LiveData<Temperature>
        get() = _currentWeatherLiveData


    private val _statusLiveData = MutableLiveData<Status>()
    val statusLiveData: LiveData<Status>
        get() = _statusLiveData

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getWeather(city: String) {
        _statusLiveData.postValue(Status.LOADING)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getResults(city = city)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _weatherLiveData.postValue(response.body())
                    _statusLiveData.postValue(Status.SUCCESS)
                } else {
                    _statusLiveData.postValue(Status.ERROR)
                }
            }

        }
    }

    private fun onError(message: String) {
        // errorMessage.value = message
        //loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    fun selectedCity() {
        _statusLiveData.value = Status.SELECTED
    }


   // fun selectedWeather(data:Temperature) {
   //     currentWeatherLiveData.value = data
   // }
}