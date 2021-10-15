package com.the.app.feature.main

import android.Manifest
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.the.app.common.views.TheVideoPlayer
import com.the.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    lateinit var player: TheVideoPlayer
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        player = binding.tvpVideoPlayer
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.areLocationPermissionsGranted()) {
            initData()
        } else {
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION))
        }
    }

    override fun onPause() {
        player.releasePlayer()
        super.onPause()
    }

    private val timer = object: CountDownTimer(4000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            binding.tvCountdown.text = round((millisUntilFinished / 1000).toDouble() + 1).toInt().toString()
        }

        override fun onFinish() {
            binding.tvCountdown.isVisible = false
            playVideo()
            initListeners()
        }
    }

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                if (viewModel.areLocationPermissionsGranted()) {
                    initData()
                }
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                if (viewModel.areLocationPermissionsGranted()) {
                    initData()
                }
            } else -> {

            }
        }
    }

    private fun initData() {
        timer.start()
        viewModel.initLocationUpdates()
    }

    private fun playVideo() {
        player.play()
    }

    private fun initListeners() {
        viewModel.onRestartVideo().observe(this, Observer {
            if (it.changed) {
                player.restartVideo()
            }
        })
        viewModel.onChangeVideoVolume().observe(this, Observer {
            player.changeVolumeBy(it.value)
        })
        viewModel.onChangeVideoTime().observe(this, Observer {
            player.changeTimeBy(it.value.toLong())
        })
        viewModel.onPauseVideo().observe(this, Observer {
            player.pause()
        })
    }

}