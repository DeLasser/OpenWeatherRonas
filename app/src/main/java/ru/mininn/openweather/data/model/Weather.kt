package ru.mininn.openweather.data.model

class Weather(
        var city: String,
        var type: String,
        var description: String,
        var icon: String,
        var temp: Int,
        var pressure: Int,
        var humidity: Int,
        var rain: Int,
        var windSpeed: Double,
        var windDeg: Int
)