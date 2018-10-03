package ru.mininn.openweather.data.api

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mininn.openweather.Constants
import ru.mininn.openweather.data.model.Weather
import ru.mininn.openweather.data.model.WeatherDeserializer

class WeatherApiClient() {
    companion object {
        fun create(): OpenWeatherApi {
            return Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().registerTypeAdapter(Weather::class.java, WeatherDeserializer()).create()))
                    .build()
                    .create(OpenWeatherApi::class.java)
        }
    }
}