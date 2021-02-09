package com.example.lostandfound.presentation.view.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lostandfound.R
import com.example.lostandfound.data.model.UserEntity
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.view.recyclerview.found.FoundModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_found.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddFoundActivity : AppCompatActivity() {
    private val REQUEST_TAKE_PHOTO = 1
    private lateinit var storageRef: StorageReference
    private lateinit var uri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectAddFoundActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_found)

        val db = FirebaseDatabase.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser
        val ref = db.reference.child("users")
        storageRef = FirebaseStorage.getInstance().getReference().child("Uploads")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val user =
                    snapshot.child(currentUser?.uid.toString()).getValue(UserEntity::class.java)!!
                et_aaf_name.setText(user.name)
            }

        })

        val foundReference = db.getReference("founds")

        btn_aaf_send.setOnClickListener {
            storageRef = FirebaseStorage.getInstance().getReference().child("Uploads")
            storageRef.child(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .child("imageurlfound${System.currentTimeMillis()}").putFile(uri)
                .addOnSuccessListener {

                    val header = et_aaf_header.text.toString()
//                    val description = et_aaf_header.text.toString()
                    val phone = et_aaf_phone.text.toString()
//                    val date = et_aaf_date.text.toString()
                    val name = et_aaf_name.text.toString()
                    val authorId = FirebaseAuth.getInstance().currentUser?.uid.toString()

                    GlobalScope.launch {
                        val uri = it.storage.downloadUrl.await()
                        val url = uri.toString()
                        val foundModel = FoundModel(
                            name,
                            url,
                            header,
                            phone,
                            com.example.lostandfound.data.Util.getDateAndTime(),
                            authorId
                        )

                        when {
                            foundModel.userName.isEmpty() -> {
                                til_aaf_name.error = "Имя не может быть пустым"
                            }
                            foundModel.foundHead.isEmpty() -> {
                                til_aaf_header.error = "Заголовок не может быть пустым"
                            }
                            foundModel.phone.isEmpty() -> {

                            }
                            else -> {
                                foundReference.push().setValue(foundModel) { _, _ ->
                                    Toast.makeText(
                                        applicationContext,
                                        "Запись успешно добавлена",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                startActivity(
                                    Intent(
                                        applicationContext,
                                        MainActivity::class.java
                                    ).apply {
                                        putExtra("nav_key", "found")
                                    })
                            }
                        }
                    }
                }.addOnFailureListener {

                }
        }

        btn_aaf_photo.setOnClickListener {
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

            iv_aaf_photo.setImageBitmap(BitmapFactory.decodeFile(path))
        }
    }

    companion object {
        private const val TAG = "AddFoundActivity"
    }
}
