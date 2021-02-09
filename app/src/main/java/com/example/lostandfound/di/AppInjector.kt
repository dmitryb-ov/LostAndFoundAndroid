package com.example.lostandfound.di

import com.example.lostandfound.di.components.*
import com.example.lostandfound.presentation.view.ui.*

object AppInjector {
    lateinit var appComponent: AppComponent
    private var lostComponent: LostComponent? = null
    private var foundComponent: FoundComponent? = null
    private var profileComponent: ProfileComponent? = null
    private var mapComponent: MapComponent? = null

    fun init(app: App) {
        DaggerAppComponent.builder()
            .application(app)
            .build().also { appComponent = it }
            .inject(app)
    }

    fun injectStartActivity(startActivity: StartActivity) {
        appComponent.inject(startActivity)
    }

    fun injectMainActivity(mainActivity: MainActivity) {
        appComponent.injectMain(mainActivity)
    }

    fun injectLoginActivity(firebaseLoginActivity: FirebaseLoginActivity) {
        appComponent.injectLogin(firebaseLoginActivity)
    }

    fun injectSignUpActivity(firebaseSignUpActivity: FirebaseSignUpActivity) {
        appComponent.injectSignUp(firebaseSignUpActivity)
    }

    fun injectAddFoundActivity(addFoundActivity: AddFoundActivity) {
        appComponent.injectAddFoundActivity(addFoundActivity)
    }

    fun injectAddLostActivity(addLostActivity: AddLostActivity) {
        appComponent.injectAddLostActivity(addLostActivity)
    }

    fun injectMoreInformationAboutLostActivity(moreInformationAboutLostActivity: MoreInformationAboutLostActivity) {
        appComponent.injectMoreInformationAboutLostActivity(moreInformationAboutLostActivity)
    }

    fun injectEditProfileActivity(editProfileActivity: EditProfileActivity) {
        appComponent.injectEditProfileActivity(editProfileActivity)
    }

    fun injectMoreInformationAboutFoundActivity(moreInformationAboutFoundActivity: MoreInformationAboutFoundActivity) {
        appComponent.injectMoreInformationAboutFoundActivity(moreInformationAboutFoundActivity)
    }

    fun plusLostComponent(): LostComponent =
        lostComponent ?: appComponent
            .lostComponentFactory()
            .create()
            .also {
                lostComponent = it
            }

    fun plusFoundComponent(): FoundComponent =
        foundComponent ?: appComponent
            .foundComponentFactory()
            .create()
            .also {
                foundComponent = it
            }

    fun plusProfileComponent(): ProfileComponent =
        profileComponent ?: appComponent
            .profileComponentFactory()
            .create()
            .also {
                profileComponent = it
            }

    fun plusMapComponent(): MapComponent =
        mapComponent ?: appComponent
            .mapComponentFactory()
            .create()
            .also {
                mapComponent = it
            }


    fun clearLostComponent() {
        lostComponent = null
    }

    fun clearFoundComponent() {
        lostComponent = null
    }

    fun clearProfileComponent() {
        profileComponent = null
    }
}