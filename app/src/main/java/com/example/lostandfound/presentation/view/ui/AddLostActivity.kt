package com.example.lostandfound.presentation.view.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lostandfound.R
import com.example.lostandfound.data.Util
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_lost.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AddLostActivity : AppCompatActivity() {
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var storageRef: StorageReference
    private lateinit var uri: Uri
    private lateinit var downloadUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectAddLostActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lost)

        val db = FirebaseDatabase.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val ref = db.reference.child("users")
        storageRef = FirebaseStorage.getInstance().getReference().child("Uploads");

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user =
                    snapshot.child(currentUser?.uid.toString()).getValue(UserEntity::class.java)!!
                et_aal_name.setText(user.name)
            }

        })

        val lostReference = db.getReference("losts")

        btn_aal_send.setOnClickListener {

            storageRef = FirebaseStorage.getInstance().getReference().child("Uploads")

            storageRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .child("imageurl${System.currentTimeMillis()}").putFile(uri).addOnSuccessListener {

                    val header = et_aal_header.text.toString()
                    val description = et_aal_description.text.toString()
                    val phone = et_aal_phone.text.toString()
                    val date = et_aal_date.text.toString()
                    val name = et_aal_name.text.toString()
                    val authorId = FirebaseAuth.getInstance().currentUser?.uid.toString()

                    GlobalScope.launch {
                        val uri = it.storage.downloadUrl.await()
                        val url = uri.toString()
                        val lostModel = LostModel(
                            name,
                            header,
                            description,
                            phone,
                            date,
                            url,
                            Util.getDateAndTime(),
                            authorId
                        )

                        tv_choose_photo_err.visibility = View.INVISIBLE
                        when {
                            lostModel.userName.isEmpty() -> {
                                til_aal_name.error = "Заполни имя"
                            }
                            lostModel.lostHead.isEmpty() -> {
                                til_aal_header.error = "Заполни заголовок"
                            }
                            lostModel.description.isEmpty() -> {
                                til_all_description.error = "Заполни описание"
                            }
                            lostModel.phoneNumber.isEmpty() -> {
                                tv_choose_photo_err.text = "Пустой номер телефона"
                                tv_choose_photo_err.visibility = View.VISIBLE
                            }
                            lostModel.date.isEmpty() -> {
                                tv_choose_photo_err.text = "Пустая дата"
                                tv_choose_photo_err.visibility = View.VISIBLE
                            }
                            lostModel.lostPic.isEmpty() -> {
                                tv_choose_photo_err.visibility = View.VISIBLE
                            }
                            else -> {
                                lostReference.push().setValue(lostModel) { _, _ ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Запись добавлена",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                                startActivity(
                                    Intent(
                                        applicationContext,
                                        MainActivity::class.java
                                    ).apply {
                                        putExtra("nav_key", "lost")
                                    })
                            }
                        }
                    }
                }.addOnFailureListener {
                    tv_choose_photo_err.text = "Выбери фото"
                    tv_choose_photo_err.visibility = View.VISIBLE
                }
        }

        btn_aal_photo.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    applicationContext,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val loadIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(loadIntent, REQUEST_TAKE_PHOTO)
                }
                else -> {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), 1
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data!!
            val file = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(uri, file, null, null, null)
            cursor?.moveToFirst()
            val index = cursor?.getColumnIndex(file[0])
            val path = cursor?.getString(index!!)
            cursor?.close()

            iv_aal_photo.setImageBitmap(BitmapFactory.decodeFile(path))
        }
    }

    companion object {
        private const val TAG = "AddLostActivity"
    }
}
