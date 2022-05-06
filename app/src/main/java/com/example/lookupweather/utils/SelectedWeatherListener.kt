package com.example.lookupweather.utils

import com.example.lookupweather.models.Temperature


interface SelectedWeatherListener {
    fun onSelectedWeather(selected: Temperature)
}