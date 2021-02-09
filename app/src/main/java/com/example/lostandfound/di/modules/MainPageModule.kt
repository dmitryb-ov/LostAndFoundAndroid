package com.example.lostandfound.di.modules

import androidx.lifecycle.ViewModel
import com.example.lostandfound.di.ViewModelKey
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.viewmodel.LostViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface MainPageModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(LostViewModel::class)
    fun bindMainPageViewModel(
        mainPageViewModel: LostViewModel
    ): ViewModel
}