package ru.mininn.openweather.data.model

class Weather(
        var city: String,
        var main: String,
        var description: String,
        var icon: String,
        var temp: Double,
        var pressure: Int,
        var humidity: Int,
        var rain: Int,
        var windSpeed: Double,
        var windDeg: Int
) {
    constructor() : this("","","","",0.0,0,0,0,0.0,0)
}