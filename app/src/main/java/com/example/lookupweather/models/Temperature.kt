package com.example.lookupweather.models

data class Temperature(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: MainInfo,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<WeatherX>,
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Sys(
    val pod: String
)

data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)