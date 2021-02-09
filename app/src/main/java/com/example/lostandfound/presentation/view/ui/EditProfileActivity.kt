package com.example.lostandfound.presentation.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity() {

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectEditProfileActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        et_aef_name.setText(intent.getStringExtra("name"))
        et_aef_city.setText(intent.getStringExtra("city"))
        et_aef_phone.setText(intent.getStringExtra("phone").substring(3))

        saveButton()
    }

    fun saveButton() {
        btn_aef_ok.setOnClickListener {
            val name = et_aef_name.text.toString()
            val city = et_aef_city.text.toString()
            val phone = et_aef_phone.text.toString()
            val email = FirebaseAuth.getInstance().currentUser?.email
            val id = FirebaseAuth.getInstance().currentUser?.uid

            val user = UserEntity(
                id,
                email,
                name,
                phone,
                city
            )

            val db = FirebaseDatabase.getInstance().getReference("users")
            db.child(id.toString()).setValue(user)
            startProfileFragment()
//
//            val user = email?.let { it1 -> profileViewModel.getProfileByEmail(it1) }
//            Log.e("TAG", "HELLO: "+user?.email)
//            if(user != null){
//                user.name = name
//                user.city = city
//                user.phoneNumber = phone
//                profileViewModel.updateProfile(user)
//
//                startProfileFragment()
//            } else {
//                Toast.makeText(applicationContext, "Что-то пошло нет так", Toast.LENGTH_LONG).show()
//            }
//
//            Log.e("TAG", "name: $name city: $city phone: $phone")
        }
    }

    fun startProfileFragment() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }


}