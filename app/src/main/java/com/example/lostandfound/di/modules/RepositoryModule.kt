package com.example.lostandfound.di.modules

import com.example.lostandfound.data.repository.*
import com.example.lostandfound.di.scope.ApplicationScope
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @ApplicationScope
    fun bindLostRepository(lostRepositoryImpl: LostRepositoryImpl): LostRepository

    @Binds
    @ApplicationScope
    fun bindFoundRepository(foundRepositoryImpl: FoundRepositoryImpl): FoundRepository

    @Binds
    @ApplicationScope
    fun bindProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}