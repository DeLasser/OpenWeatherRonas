package ru.mininn.openweather.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.mininn.openweather.data.api.WeatherApiClient
import ru.mininn.openweather.data.model.Weather

class WeatherRepository(val context: Context) {
    private val api = WeatherApiClient.create()
    private val locationManager = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

    fun getWeather(city: String) = api.getWeatherByName(city).subscribeOn(Schedulers.io())

    @SuppressLint("MissingPermission")
    fun getWeather(): Observable<Weather> {
        return Observable.create<Location> {
            locationManager.requestLocationUpdates(getBestProvider(locationManager), 0, 0.0f,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location?) {
                            if (location != null && (location.latitude != 0.0 || location.longitude != 0.0)) {
                                it.onNext(location)
                                locationManager.removeUpdates(this)
                                it.onComplete()
                            }
                        }

                        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                            it.onComplete()
                        }

                        override fun onProviderEnabled(p0: String?) {
                            it.onComplete()
                        }

                        override fun onProviderDisabled(p0: String?) {
                            it.onComplete()
                        }

                    }, Looper.getMainLooper())
        }.flatMap {
            return@flatMap api.getWeatherByCoordinates(it.latitude, it.longitude).subscribeOn(Schedulers.io())
        }


    }

    private fun getBestProvider(locationManager: LocationManager): String? {
        val criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        return locationManager.getBestProvider(criteria, true)
    }
}