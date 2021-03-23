package com.example.videoplayertest.di

import com.example.videoplayertest.di.home.HomeViewModelModule
import com.example.videoplayertest.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {


    @ContributesAndroidInjector(
        modules = [HomeViewModelModule::class,
            FragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

}