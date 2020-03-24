package com.example.lostandfound.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.BuildConfig
import com.example.lostandfound.R
import com.example.lostandfound.data.SharedPreferencesFields
import com.example.lostandfound.data.Util
import com.example.lostandfound.db.AppDatabase
import com.example.lostandfound.db.User
import com.example.lostandfound.db.UserDao
import com.example.lostandfound.security.AESCrypt
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private val mSettings: SharedPreferences by lazy {
        getSharedPreferences(BuildConfig.APP_PREFERENCES, Context.MODE_PRIVATE)
    }
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Log.i(TAG, "SignUpActivity created")

        btn_sign_up.setOnClickListener {
            Log.i(TAG, "SignUp button pushed")
            if (et_email_sign_up.text.toString().isNotEmpty() && et_password_sign_up.text.toString()
                    .isNotEmpty()
                && et_name_sign_up.text.toString().isNotEmpty()
            ) {
                Observable.fromCallable {
                    db = AppDatabase.getAppDatabase(context = this)
                    userDao = db?.userDao()
                    val user = User(
                        email = et_email_sign_up.text.toString(),
                        password = AESCrypt.encrypt(et_password_sign_up.text.toString()),
                        name = et_name_sign_up.text.toString(),
                        phoneNumber = et_number_sign_up.text?.toString(),
                        registrationDate = Util.getDateAndTime()
                    )

                    with(userDao) {
                        this?.addUser(user)
                    }
                }.subscribeOn(Schedulers.io()).apply {
                    observeOn(AndroidSchedulers.mainThread())
                    subscribe()
                }

                var user: User?
                with(userDao) {
                    user = this?.getUserByEmail(et_email_sign_up.text.toString())
                }
                mSettings.edit().apply() {
                    putBoolean(SharedPreferencesFields.APP_PREFERENCES_AUTH, true)
                    Log.i(TAG, "Put true value in shared preferences")
                    user?.id?.let {
                        putInt(SharedPreferencesFields.APP_USER_ID, it)
                        Log.i(TAG, "Put $it value in shared preferences")
                    }
                    apply()
                }
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                if (et_email_sign_up.text.toString().isEmpty()) {
                    Log.w(TAG, "Empty mail field")
                    til_email_sign_up.error = "Введите почту"
                }
                if (et_password_sign_up.text.toString().isEmpty()) {
                    Log.w(TAG, "Empty password field")
                    til_password_sign_up.error = "Ну куда уж без пароля"
                }
                if (et_name_sign_up.text.toString().isEmpty()) {
                    Log.w(TAG, "Empty name field")
                    til_name_sign_up.error = "Все хотят знать ваше имя"
                }
            }
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}
