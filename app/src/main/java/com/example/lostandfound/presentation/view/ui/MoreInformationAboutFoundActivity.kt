package com.example.lostandfound.presentation.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lostandfound.R
import com.example.lostandfound.di.AppInjector
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_more_information_about_found.*

class MoreInformationAboutFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectMoreInformationAboutFoundActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_information_about_found)

        title = intent.getStringExtra("foundHead")

        tv_more_inf_about_found_name.text = intent.getStringExtra("userName")
        tv_more_inf_about_found_user_date.text = intent.getStringExtra("date")
        Glide.with(this).load(intent.getStringExtra("image")).into(iv_more_int_about_found_post_img)
        et_more_inf_about_found_description.setText(intent.getStringExtra("foundHead"))
        et_more_inf_about_found_num.setText(intent.getStringExtra("phone"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val authorId = intent.getStringExtra("authorId")
        if (authorId == auth.currentUser?.uid.toString()) {
            menuInflater.inflate(R.menu.menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.menu_delete -> {
                val ref = FirebaseDatabase.getInstance().getReference()
                val query = ref.child("founds").orderByChild("authorId")
                    .equalTo(MoreInformationAboutLostActivity.auth.currentUser?.uid.toString())

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("TAG", "onCancelled", error.toException());
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (dataSnapshot in snapshot.children) {
                            if (dataSnapshot.child("foundHead").value == intent.getStringExtra("foundHead"))
                                dataSnapshot.ref.removeValue()
                        }
                    }

                })

                startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra("nav_key", "found")
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val auth = FirebaseAuth.getInstance()
    }
}