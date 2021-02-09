package com.example.lostandfound.di.modules

import android.app.Application
import androidx.room.Room
import com.example.lostandfound.data.local.AppDatabase
import com.example.lostandfound.data.local.dao.FoundDao
import com.example.lostandfound.data.local.dao.LostDao
import com.example.lostandfound.data.local.dao.UserDao
import com.example.lostandfound.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class LocalDataModule {

    @Provides
    @ApplicationScope
    fun provideDb(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java, "mylaf.db"
        )
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideLostDao(db: AppDatabase): LostDao = db.lostDao()

    @Provides
    @ApplicationScope
    fun provideFoundDao(db: AppDatabase): FoundDao = db.foundDao()

    @Provides
    @ApplicationScope
    fun provideProfileDao(db: AppDatabase): UserDao = db.userDao()
}