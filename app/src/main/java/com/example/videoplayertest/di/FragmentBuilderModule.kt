package com.example.videoplayertest.di

import com.example.videoplayertest.di.home.HomeModule
import com.example.videoplayertest.di.home.HomeScope
import com.example.videoplayertest.di.home.HomeViewModelModule
import com.example.videoplayertest.di.player.PlayerModule
import com.example.videoplayertest.di.player.PlayerScope
import com.example.videoplayertest.view.home.HomeFragment
import com.example.videoplayertest.view.player.PlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {

    @HomeScope
    @ContributesAndroidInjector(
        modules = [
            HomeViewModelModule::class,
            HomeModule::class]
    )
    fun contributeHomeFragment(): HomeFragment

    @PlayerScope
    @ContributesAndroidInjector(
        modules = [
            PlayerModule::class]
    )
    fun contributePlayerFragment(): PlayerFragment

}