package com.example.lostandfound.di.components

import android.app.Application
import com.example.lostandfound.di.App
import com.example.lostandfound.di.modules.InteractorModule
import com.example.lostandfound.di.modules.LocalDataModule
import com.example.lostandfound.di.modules.RepositoryModule
import com.example.lostandfound.di.scope.ApplicationScope
import com.example.lostandfound.presentation.view.ui.*
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        RepositoryModule::class,
        LocalDataModule::class,
        InteractorModule::class
    ]
)
interface AppComponent {

    fun lostComponentFactory(): LostComponent.Factory

    fun foundComponentFactory(): FoundComponent.Factory

    fun profileComponentFactory(): ProfileComponent.Factory

    fun mapComponentFactory(): MapComponent.Factory

//    fun startPageComponentFactory

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(startActivity: StartActivity)

    fun injectMain(mainActivity: MainActivity)

    fun injectLogin(firebaseLoginActivity: FirebaseLoginActivity)

    fun injectSignUp(firebaseSignUpActivity: FirebaseSignUpActivity)

    fun injectAddLostActivity(addLostActivity: AddLostActivity)

    fun injectAddFoundActivity(addFoundActivity: AddFoundActivity)

    fun injectMoreInformationAboutLostActivity(moreInformationAboutLostActivity: MoreInformationAboutLostActivity)

    fun injectEditProfileActivity(editProfileActivity: EditProfileActivity)

    fun injectMoreInformationAboutFoundActivity(moreInformationAboutFoundActivity: MoreInformationAboutFoundActivity)
}