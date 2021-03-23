package com.example.videoplayertest.di.home

import androidx.lifecycle.ViewModel
import com.example.videoplayertest.di.viewmodel.ViewModelKey
import com.example.videoplayertest.view.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

}