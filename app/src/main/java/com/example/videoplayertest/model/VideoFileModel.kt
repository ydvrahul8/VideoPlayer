package com.example.videoplayertest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoFileModel(val filePath: String, val createdDate: String, val fileName: String):Parcelable