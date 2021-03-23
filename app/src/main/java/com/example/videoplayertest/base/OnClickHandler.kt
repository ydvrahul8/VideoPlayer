package com.example.videoplayertest.base

import android.view.View
import androidx.navigation.findNavController
import com.example.videoplayertest.model.VideoFileModel
import com.example.videoplayertest.view.home.HomeFragmentDirections
import java.text.FieldPosition
import javax.inject.Inject


class OnClickHandler @Inject constructor() {

    fun onLocationClick(view: View, position: Integer) {
        val data = HomeFragmentDirections.actionHomeFragmentToPlayerFragment(position)
        view.findNavController().navigate(data)
    }

}