package ru.mininn.openweather.ui

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.mininn.openweather.R

class WeatherActivity : AppCompatActivity() {
    private val LOCATION_PERMISSION_CODE = 1001

    private val viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        subscribe()
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
                if(isGranted) {
                    viewModel.weatherByLocation()
                    viewModel.isPermissionsGranted.value = isGranted
                }
            }
        }
    }

    private fun subscribe() {
        subscribeForPermissions()
        subscribeForMetricType()
        subscribeForWeather()
    }

    private fun subscribeForWeather() {
        viewModel.weatherLiveData.observe(this, Observer {

        })
    }

    private fun subscribeForMetricType() {
        viewModel.isCelsius.observe(this, Observer {

        })
    }

    private fun subscribeForPermissions() {
        viewModel.isPermissionsGranted.observe(this, Observer {
            if (it != null && !it) {
                requestPermissions()
            }
        })
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.GET_ACCOUNTS),
                    LOCATION_PERMISSION_CODE)
        }
    }
}
