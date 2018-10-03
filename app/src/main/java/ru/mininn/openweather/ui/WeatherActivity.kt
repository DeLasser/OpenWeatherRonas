package ru.mininn.openweather.ui

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.mininn.openweather.R
import ru.mininn.openweather.data.model.Weather

class WeatherActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION_CODE = 1001

    private val viewModel by lazy { ViewModelProviders.of(this).get(WeatherViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribe()
        if(savedInstanceState == null) {
            requestWeatherByLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                var isGranted = true
                grantResults.forEach {
                    if (it != PackageManager.PERMISSION_GRANTED) {
                        isGranted = false
                    }
                }
                if (isGranted) {
                    viewModel.weatherByLocation()
                    viewModel.isPermissionsGranted.value = isGranted
                }
            }
        }
    }

    private fun requestWeatherByLocation() {
        if (viewModel.checkPermission()) {
            viewModel.weatherByLocation()
        } else {
            requestPermissions()
        }
    }

    private fun subscribe() {
        subscribeForMetricType()
        subscribeForWeather()
    }

    private fun subscribeForWeather() {
        viewModel.weatherLiveData.observe(this, Observer {
            if (it != null) {
                bindView(it)
            }
        })
    }

    private fun subscribeForMetricType() {
        viewModel.isCelsius.observe(this, Observer {

        })
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_CODE)
        }
    }

    private fun bindView(weather: Weather) {
        this.city.text = weather.city
    }
}
