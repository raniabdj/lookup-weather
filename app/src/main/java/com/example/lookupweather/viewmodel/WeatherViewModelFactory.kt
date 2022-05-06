package com.example.lookupweather.viewmodel
import WeatherRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class WeatherViewModelFactory(private val repository: WeatherRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(repository) as T
    }
}
