package com.example.lostandfound.di.components

import com.example.lostandfound.di.modules.LostModule
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.view.ui.LostFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [LostModule::class])
interface LostComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LostComponent
    }

    fun inject(fragment: LostFragment)
}