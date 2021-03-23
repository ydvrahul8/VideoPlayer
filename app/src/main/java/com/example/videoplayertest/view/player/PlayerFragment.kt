package com.example.videoplayertest.view.player

import android.net.Uri
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.videoplayertest.R
import com.example.videoplayertest.base.BaseFragment
import com.example.videoplayertest.databinding.FragmentPlayerBinding
import com.example.videoplayertest.utils.VIDEOLIST
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.layout_custom_control.*
import java.io.File


class PlayerFragment : BaseFragment<FragmentPlayerBinding>() {

    override val layout: Int
        get() = R.layout.fragment_player
    private lateinit var player: SimpleExoPlayer
    private lateinit var binding: FragmentPlayerBinding
    private val args: PlayerFragmentArgs by navArgs()
    private var volumeON = true
    private var position: Int = 0
    override fun init(bind: FragmentPlayerBinding) {
        binding = bind
        position = args.position.toInt()
        if (position >= 0) {
            initializePlayer(position)
        }
        //initializePlayer()
        btn_next.setOnClickListener {
            playNextVideo()
        }
        btn_previous.setOnClickListener {
            playPreviousVideo()
        }
        imageView_volume.setOnClickListener {
            manageVolume()
        }
    }

    private fun playNextVideo() {
        player.stop()
        player.release()
        if (position < VIDEOLIST.size) {
            position += 1
            initializePlayer(position)
        } else {
            position = 0
            initializePlayer(position)
        }
    }

    private fun playPreviousVideo() {
        player.stop()
        player.release()
        if (position == 0) {
            position = VIDEOLIST.size - 1
            initializePlayer(position)
        } else {
            position -= 1
            initializePlayer(position)
        }
    }

    private fun manageVolume() {
        if (volumeON) {
            player?.volume = 0f
            Glide.with(requireActivity()).load(R.drawable.ic_volume_off_24dp)
                .into(imageView_volume)
            volumeON = false
        } else {
            player?.volume = 1f
            Glide.with(requireActivity()).load(R.drawable.ic_volume_on_24dp)
                .into(imageView_volume)
            volumeON = true
        }
    }

    override fun onPause() {
        super.onPause()
        player.playWhenReady = false
        player.playbackState
    }

    private fun initializePlayer(position: Int) {
        val file = File(VIDEOLIST[position].filePath)
//        val file = File(args.videoFile.filePath)
        val uri = Uri.fromFile(file)
        player = SimpleExoPlayer.Builder(requireContext()).build()
        val factory = DefaultDataSourceFactory(
            requireContext(),
            Util.getUserAgent(requireContext(), "Media Player")
        )
        val defaultExtractorsFactory = DefaultExtractorsFactory()
        val mediaSource =
            ProgressiveMediaSource.Factory(factory, defaultExtractorsFactory)
                .createMediaSource(uri)
        binding.videoView.player = player
        binding.videoView.keepScreenOn = true
        player.prepare(mediaSource)
        player.playWhenReady = true
    }
}