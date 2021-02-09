package com.example.lostandfound.di.modules

import com.example.lostandfound.di.scope.ApplicationScope
import com.example.lostandfound.domain.interactors.*
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    @ApplicationScope
    fun provideLostInteractor(
        lostInteractorImpl: LostInteractorImpl
    ): LostInteractor

    @Binds
    @ApplicationScope
    fun provideFoundInteractor(
        foundInteractorImpl: FoundInteractorImpl
    ): FoundInteractor

    @Binds
    @ApplicationScope
    fun provideProfileInteractor(
        profileInteractorImpl: ProfileInteractorImpl
    ): ProfileInteractor
}