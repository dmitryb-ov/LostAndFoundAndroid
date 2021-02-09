package com.example.lostandfound.presentation.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_firebase_sign_up.*
import java.nio.charset.Charset
import javax.inject.Inject


class FirebaseSignUpActivity : AppCompatActivity() {

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectSignUpActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_sign_up)

//        val city = CityParser(loadCities()).parse()
        val city = arrayOf("Казань", "Москва", "Елабуга")
        var selectCity: String
        val adapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, city)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.prompt = "Город"
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                selectCity = spinner.selectedItem.toString()
//                Toast.makeText(baseContext, "Position = $position", Toast.LENGTH_SHORT)
//                    .show()
            }

            override fun onNothingSelected(arg0: AdapterView<*>?) {}
        }

        btn_sign_up.setOnClickListener {
            val email = et_email_sign_up.text.toString()
            val password = et_password_sign_up.text.toString()
            val name = et_name_sign_up.text.toString()
            val phone = et_phone_sign_up.text.toString()
            val city = spinner.selectedItem.toString()

            when {
                email.isEmpty() -> {
                    til_email_sign_up.error = "Поле не может быть пустым"
                }
                password.isEmpty() -> {
                    til_password_sign_up.error = "Поле не может быть пустым"
                }
                name.isEmpty() -> {
                    til_name_sign_up.error = "Поле не может быть пустым"
                }
                phone.isEmpty() -> {
                    til_phone_sign_up.error = "Поле не может быть пустым"
                }
                else -> {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val db = FirebaseDatabase.getInstance().getReference()
                                val user = UserEntity(
                                    it.result?.user?.uid,
                                    it.result?.user?.email,
                                    name,
                                    phone,
                                    city
                                )
                                db.child("users").child(it.result?.user?.uid.toString())
                                    .setValue(user)
//                        val user = User(
//                            email = email,
//                            password = password,
//                            name = "Пусто",
//                            city = "Пусто",
//                            phoneNumber = "Пусто",
//                            registrationDate = Util.getDateAndTime()
//                        )
//
//                        profileViewModel.saveProfile(user)

                                startActivity(Intent(applicationContext, MainActivity::class.java))
                            }
                        }
                }
            }

        }
    }

    private fun loadCities(): String {
        val json: String
        val inputStream = applicationContext.assets.open("russia.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, Charset.forName("UTF-8"))
        return json
    }
}