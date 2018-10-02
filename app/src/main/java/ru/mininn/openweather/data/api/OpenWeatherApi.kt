package ru.mininn.openweather.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mininn.openweather.Constants
import ru.mininn.openweather.data.model.Weather

interface OpenWeatherApi {

    @GET("weather")
    fun getWeatherByName(@Query("q") city: String, @Query("APPID") apikey: String = Constants.API_KEY): Observable<Weather>

    @GET("weather")
    fun getWeatherByCoordinates(@Query("lat") lat: Double,@Query("lon") lon: Double, @Query("APPID") apikey: String = Constants.API_KEY): Observable<Weather>

}