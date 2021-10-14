package com.the.app.main

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.the.app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initListeners()
    }


    override fun onStart() {
        super.onStart()
        if (viewModel.areLocationPermissionsGranted()) {
            viewModel.initLocationUpdates()
        } else {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                if (viewModel.areLocationPermissionsGranted()) {
                    viewModel.initLocationUpdates()
                }
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                if (viewModel.areLocationPermissionsGranted()) {
                    viewModel.initLocationUpdates()
                }
            } else -> {

            }
        }
    }

    private fun initListeners() {
        viewModel.onLocationChanged().observe(this, Observer {
            println("--- location changed")
        })
    }

}