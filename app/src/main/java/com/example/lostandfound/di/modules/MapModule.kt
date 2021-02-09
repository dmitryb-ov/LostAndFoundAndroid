package com.example.lostandfound.di.modules

import dagger.Module

@Module(includes = [ViewModelModule::class])
interface MapModule {

//    @Binds
//    @IntoMap
//    @ScreenScope
//    @ViewModelKey(MapViewModel::class)
//    fun bindMapViewModel(mapViewModel: MapViewModel): ViewModel
}