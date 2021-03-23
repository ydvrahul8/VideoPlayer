package com.example.videoplayertest.utils

import android.graphics.Color
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.File

@BindingAdapter("imageResource")
fun setImageResource(view: ImageView, filePath: String?) {
    val file = File(filePath)
    val uri = Uri.fromFile(file)
    Glide.with(view.context)
        .load(uri).into(view)
}


@BindingAdapter("customColor")
fun setTextColor(view: TextView, color: String?) {
    if (color != null)
        view.setTextColor(Color.parseColor(color))
}
