package com.example.lostandfound.presentation.view.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.BuildConfig
import com.example.lostandfound.R
import com.example.lostandfound.di.AppInjector
import com.google.firebase.auth.FirebaseAuth

class StartActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "StartActivity"
    }

    private val mSettings: SharedPreferences by lazy {
        getSharedPreferences(BuildConfig.APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectStartActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Log.i(TAG, "Start Activity create")

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(applicationContext, FirebaseLoginActivity::class.java))
            finish()
        }

//        if (mSettings.contains(SharedPreferencesFields.APP_PREFERENCES_AUTH)) { //&& mSettings.contains(SharedPreferencesFields.APP_USER_ID)
//            Log.i(TAG, "User has auth preferences")
//            if (mSettings.getBoolean(SharedPreferencesFields.APP_PREFERENCES_AUTH, false)) {
//                Log.i(TAG, "User has true auth. Launch LoginActivity")
//                startActivity(Intent(applicationContext, MainActivity::class.java)) // true
//                finish()
//                Log.i(TAG, "MainActivity launched")
//            } else {
//                Log.i(TAG, "User has false auth. Launch LoginActivity")
//                startActivity(Intent(applicationContext, LogInActivity::class.java))
//                finish()
//                Log.i(TAG, "LoginActivity launched")
//            }
//        } else {
//            Log.i(TAG, "User has not auth preferences. Launch LoginActivity")
//            startActivity(Intent(applicationContext, LogInActivity::class.java))
//            finish()
//            Log.i(TAG, "LoginActivity launched")
//        }
    }
}
