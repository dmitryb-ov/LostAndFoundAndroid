package com.example.lostandfound.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import kotlinx.android.synthetic.main.activity_more_information_about_lost.*

class MoreInformationAboutLostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_information_about_lost)
        title = intent.getStringExtra("lostHead")

        tv_more_inf_about_lost_name.text = intent.getStringExtra("userName")
        tv_more_inf_about_lost_user_date.text = intent.getStringExtra("date")
        et_more_inf_about_lost_dat.setText(intent.getStringExtra("lostDate"))
        et_more_inf_about_lost_description.setText(intent.getStringExtra("description"))
        et_more_inf_about_lost_num.setText(intent.getStringExtra("phoneNumber"))
        et_more_int_about_lost_geo_position.setText(intent.getStringExtra("location"))
        iv_more_int_about_lost_post_img.setImageResource(intent.getIntExtra("lostPic", 0))
    }
}
