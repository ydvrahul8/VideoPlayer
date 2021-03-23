package com.example.videoplayertest.view.home

import android.app.Application
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoplayertest.model.VideoFileModel
import com.example.videoplayertest.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeViewModel @Inject constructor(val application: Application) : ViewModel() {

    private val _videoList = MutableLiveData<Resource<ArrayList<VideoFileModel>>>()
    val videoList: LiveData<Resource<ArrayList<VideoFileModel>>> get() = _videoList

    fun getAllVideoPath() {

        _videoList.value = Resource.loading(data = null)

        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection =
            arrayOf(MediaStore.Video.VideoColumns.DATA)
        val orderBy = MediaStore.Video.Media.DATE_TAKEN
        val cursor =
            application.contentResolver.query(uri, projection, null, null, "$orderBy DESC")
        val list = ArrayList<VideoFileModel>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.e("TAG", "getAllVideoPath: file name-->" + cursor.getString(0))
                list.add(getVideo(cursor))
            }
            cursor.close()
        }
        if (list.isNotEmpty())
            _videoList.value = Resource.success(list)
        else
            _videoList.value = Resource.error(data = null, message = "Videos not found!!!")
    }

    private fun getVideo(cursor: Cursor): VideoFileModel {
        val filePath = cursor.getString(0)
        val file = File(cursor.getString(0))
        val createdDate = Date(file.lastModified())
        val originalFormat =
            SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
        val targetFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val date = originalFormat.parse(createdDate.toString())
        val formattedDate = targetFormat.format(date)
        return VideoFileModel(filePath, formattedDate, file.name)
        val data = filterList("")
    }

    private val _filteredList = MutableLiveData<ArrayList<VideoFileModel>>()
    val filteredList: LiveData<ArrayList<VideoFileModel>> get() = _filteredList

    fun filterList(data: String) =
        viewModelScope.launch(Dispatchers.IO) {
            val list = ArrayList<VideoFileModel>()
            for (item in _videoList.value?.data!!) {
                if (item.fileName.toLowerCase().contains(data))
                    list.add(item)
            }
            _filteredList.postValue(list)
        }


}