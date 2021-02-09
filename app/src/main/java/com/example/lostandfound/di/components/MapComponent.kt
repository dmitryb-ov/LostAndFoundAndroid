package com.example.lostandfound.di.components

import com.example.lostandfound.di.modules.MapModule
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.view.ui.OpenMapFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [MapModule::class])
interface MapComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MapComponent
    }

    fun inject(fragmentOpen: OpenMapFragment)
}