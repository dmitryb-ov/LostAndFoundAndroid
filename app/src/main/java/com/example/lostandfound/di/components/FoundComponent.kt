package com.example.lostandfound.di.components

import com.example.lostandfound.di.modules.FoundModule
import com.example.lostandfound.di.scope.ScreenScope
import com.example.lostandfound.presentation.view.ui.FoundFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(modules = [FoundModule::class])
interface FoundComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): FoundComponent
    }

    fun inject(fragment: FoundFragment)
}