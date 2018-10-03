package ru.mininn.openweather.ui

import android.Manifest
import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mininn.openweather.data.WeatherRepository
import ru.mininn.openweather.data.model.Weather

class WeatherViewModel(application: Application): AndroidViewModel(application) {
    val weatherRepository = WeatherRepository(application.applicationContext)
    val weatherLiveData= MutableLiveData<Weather>()
    val isCelsius = MutableLiveData<Boolean>()
    val isPermissionsGranted = MutableLiveData<Boolean>()

    init {
        weatherLiveData.value = null
        isCelsius.value = false
        isPermissionsGranted.value = checkPermission()
    }

    fun weatherByCity(city: String) {
        weatherRepository.getWeather(city)

                .subscribe({

                    weatherLiveData.postValue(it)
                },{

                    it.printStackTrace()
                })
    }

    fun weatherByLocation() {
        if(!checkPermission()) {
            isPermissionsGranted.postValue(false)

        } else {
            weatherRepository.getWeather()
                    .subscribe({
                        Log.d("asd", it.city)
                        weatherLiveData.postValue(it)
                    }, {
                        Log.d("asd", "error")
                        it.printStackTrace()
                    })
        }
    }

    fun changeMetricType() {
        isCelsius.value = !isCelsius.value!!
    }

    fun checkPermission(): Boolean {
        return (PermissionChecker.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }
}