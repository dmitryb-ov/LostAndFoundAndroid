package com.example.lostandfound.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.BuildConfig
import com.example.lostandfound.R
import com.example.lostandfound.data.SharedPreferencesFields
import com.example.lostandfound.db.AppDatabase
import com.example.lostandfound.db.User
import com.example.lostandfound.db.UserDao
import com.example.lostandfound.security.AESCrypt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_log_in.view.*

class LogInActivity : AppCompatActivity() {
    private val mSettings: SharedPreferences by lazy {
        getSharedPreferences(BuildConfig.APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        Log.i(TAG, "LogInActivity created")

        btn_log_in.setOnClickListener {
            Log.i(TAG, "Login button pushed")
            if (et_login_log_in.text.toString().isNotEmpty() && et_password.text.toString()
                    .isNotEmpty()
            ) {
                Observable.fromCallable {
                    db = AppDatabase.getAppDatabase(context = this)
                    userDao = db?.userDao()
                    var user: User?
                    with(userDao) {
                        user = this?.getUserByEmail(et_login_log_in.text.toString())
                        if (checkEqualsPasswords(user, et_password.text.toString())) {
                            mSettings.edit().apply() {
                                putBoolean(SharedPreferencesFields.APP_PREFERENCES_AUTH, true)
                                Log.i(TAG, "Put true value in shared preferences")
                                user?.id?.let { it1 ->
                                    putInt(
                                        SharedPreferencesFields.APP_USER_ID,
                                        it1
                                    )
                                    Log.i(TAG, "Put $it1 value in shared preferences")
                                }
                                apply()
                            }
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }
                    }
                }.subscribeOn(Schedulers.io()).apply {
                    observeOn(AndroidSchedulers.mainThread())
                    subscribe()
                }
            } else {
                if (et_login_log_in.text.toString().isEmpty()) {
                    Log.w(TAG, "Empty login field")
                    til_log_in.error = "Не оставляйте поле пустым"
                } else if (et_password.text.toString().isEmpty()) {
                    Log.w(TAG, "Empty pawword field")
                    til_password.error = "Не оставляйте поле пустым"
                }
            }
        }
    }

    fun onClickForgotPassword(v: View) {
        val clickForgotPas = v.tv_forgot_password
        clickForgotPas.setOnClickListener {
            Log.i(TAG, "Forgot Password pushed")

        }
    }

    fun onClickRegistration(v: View) {
        val clickReg = v.tv_registration
        clickReg.setOnClickListener {
            Log.i(TAG, "Registration pushed")
            startActivity(Intent(v.context, SignUpActivity::class.java))
            finish()
        }
    }

    private fun checkEqualsPasswords(user: User?, password: String): Boolean {
        return if (user != null) {
            if (user.password.let { AESCrypt.decrypt(it) } == password) {
                Log.i(TAG, "OK login and password")
                true
            } else {
                til_password.error = "Неверный пароль"
                Log.w(TAG, "Wrong password")
                false
            }
        } else {
            til_log_in.error = "Неверный логин"
            Log.w(TAG, "Wrong login")
            false
        }
    }

    companion object {
        private const val TAG = "LogInActivity"
    }
}
