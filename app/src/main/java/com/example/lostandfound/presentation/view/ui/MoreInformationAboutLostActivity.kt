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
import kotlinx.android.synthetic.main.activity_more_information_about_lost.*

class MoreInformationAboutLostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectMoreInformationAboutLostActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_information_about_lost)
        title = intent.getStringExtra("lostHead")

        tv_more_inf_about_lost_name.text = intent.getStringExtra("userName")
        tv_more_inf_about_lost_user_date.text = intent.getStringExtra("date")
        et_more_inf_about_lost_dat.setText(intent.getStringExtra("lostDate"))
        et_more_inf_about_lost_description.setText(intent.getStringExtra("description"))
        et_more_inf_about_lost_num.setText(intent.getStringExtra("phoneNumber"))
        Glide.with(this).load(intent.getStringExtra("lostPic"))
            .into(iv_more_int_about_lost_post_img)
//        et_more_int_about_lost_geo_position.setText(intent.getStringExtra("location"))
//        iv_more_int_about_lost_post_img.setImageResource(intent.getStringExtra("lostPic"))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val authorId = intent.getStringExtra("authorId")
        if (authorId == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
            menuInflater.inflate(R.menu.menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.menu_delete -> {
                val ref = FirebaseDatabase.getInstance().getReference()
                val query = ref.child("losts").orderByChild("authorId")
                    .equalTo(auth.currentUser?.uid.toString())

                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        Log.e("TAG", "onCancelled", error.toException());
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (dataSnapshot in snapshot.children) {
                            if (dataSnapshot.child("description").value == intent.getStringExtra("description"))
                                dataSnapshot.ref.removeValue()
                        }
                    }

                })

                startActivity(Intent(applicationContext, MainActivity::class.java).apply {
                    putExtra("nav_key", "lost")
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val auth = FirebaseAuth.getInstance()
    }
}
