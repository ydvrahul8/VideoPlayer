package com.example.videoplayertest.di.home

import com.example.videoplayertest.base.OnClickHandler
import com.example.videoplayertest.view.adapter.VideoThumbnailAdapter
import dagger.Module
import dagger.Provides

@Module
object HomeModule {

    @HomeScope
    @Provides
    @JvmStatic
    fun provideVideoThumbnailAdapter(onClickHandler: OnClickHandler): VideoThumbnailAdapter {
        return VideoThumbnailAdapter(onClickHandler)
    }

}