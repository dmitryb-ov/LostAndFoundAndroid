package com.example.lostandfound.presentation.view.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.data.SharedPreferencesFields
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_firebase_login.*
import kotlinx.android.synthetic.main.activity_firebase_login.view.*
import javax.inject.Inject

class FirebaseLoginActivity : AppCompatActivity() {

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectLoginActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_login)

        val auth = FirebaseAuth.getInstance();

        btn_log_in.setOnClickListener {
            val email = et_login_log_in.text.toString()
            val password = et_password.text.toString()
            when {
                email.isEmpty() -> {
                    til_log_in.error = "Поле не может быть пустым"
                }
                password.isEmpty() -> {
                    til_log_in.error = "Поле не может быть пустым"
                }
                else -> {
                    try {
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    mSettings.edit()
                                        .putBoolean(SharedPreferencesFields.IS_NEW, false)
                                        .apply()

                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            MainActivity::class.java
                                        )
                                    )

                                    finish()

                                }
                            }.addOnFailureListener {
                                et_login_log_in.error = "Логин или пароль неверные"
                            }
                    } catch (e: IllegalArgumentException) {
                        Log.e("Error", "Field is empty")
                    }
                }
            }

        }
    }

    private val mSettings: SharedPreferences by lazy {
        getSharedPreferences("mysettings", Context.MODE_PRIVATE)
    }

    fun onClickRegistration(v: View) {
        val clickReg = v.tv_registration
        clickReg.setOnClickListener {
            startActivity(Intent(v.context, FirebaseSignUpActivity::class.java))
            finish()
        }
    }
}