package com.the.app.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.the.app.databinding.ViewTheVideoPlayerBinding

class TheVideoPlayer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding: ViewTheVideoPlayerBinding =
        ViewTheVideoPlayerBinding.inflate(LayoutInflater.from(context), this, true)

    private var player: SimpleExoPlayer? = null

    private var isPlaying = false

    init {
        initializePlayer()
        loadVideo()
    }

    private fun initializePlayer() {
        binding.exoVideoPlayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        player = SimpleExoPlayer.Builder(context).build()
        player?.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
        binding.exoVideoPlayer.player = player
    }

    fun releasePlayer() {
        if (player != null) {
            player!!.release()
            player = null
            isPlaying = false
        }
    }

    private fun loadVideo() {
        val mediaItem: MediaItem = MediaItem.fromUri(VIDEO_URL)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.repeatMode = Player.REPEAT_MODE_ONE
    }

    fun play() {
        player?.play()
        isPlaying = true
    }

    fun pause() {
        isPlaying = if (isPlaying) {
            player?.pause()
            false
        } else {
            player?.play()
            true
        }
    }

    fun stop() {
        player?.stop()
        isPlaying = false
    }

    fun restartVideo() {
        player?.seekTo(0)
    }

    fun changeVolumeBy(value: Float) {
        player?.let {
            it.volume = it.volume + value
        }
    }

    fun changeTimeBy(value: Long) {
        player?.let {
            val position = it.currentPosition + value * 1000
            it.seekTo(position)
        }
    }

    companion object {
        private const val VIDEO_URL =
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
    }
}