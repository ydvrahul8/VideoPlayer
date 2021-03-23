package com.example.videoplayertest.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.videoplayertest.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
object AppModule {


    @Singleton
    @Provides
    @JvmStatic
    fun providesRequestOptions(): RequestOptions {
        return RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun providesGlideInstance(
        application: Application,
        requestOptions: RequestOptions
    ): RequestManager {
        return Glide.with(application).setDefaultRequestOptions(requestOptions)
    }

}