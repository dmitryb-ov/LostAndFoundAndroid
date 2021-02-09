package com.example.lostandfound.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.lostandfound.presentation.viewmodel.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(
        factory: AppViewModelFactory
    ): ViewModelProvider.Factory
}