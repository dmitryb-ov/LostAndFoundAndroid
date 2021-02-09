package com.example.lostandfound.di.modules

import androidx.lifecycle.ViewModel
import com.example.lostandfound.di.ViewModelKey
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.viewmodel.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModule::class])
interface ProfileModule {

    @Binds
    @IntoMap
    @ScreenScope
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(
        profileModule: ProfileViewModel
    ): ViewModel
}