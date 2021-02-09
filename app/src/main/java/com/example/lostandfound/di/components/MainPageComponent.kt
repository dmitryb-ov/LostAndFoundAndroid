package com.example.lostandfound.di.components

import com.example.lostandfound.di.modules.MainPageModule
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.view.ui.LostFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [MainPageModule::class])
interface MainPageComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainPageComponent
    }

    fun inject(fragment: LostFragment)
}