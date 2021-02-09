package com.example.lostandfound.di.components

import com.example.lostandfound.di.modules.ProfileModule
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.view.ui.ProfileFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [ProfileModule::class])
interface ProfileComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ProfileComponent
    }

    fun inject(fragment: ProfileFragment)
}