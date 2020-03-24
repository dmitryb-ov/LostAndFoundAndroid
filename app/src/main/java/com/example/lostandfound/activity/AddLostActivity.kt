package com.example.lostandfound.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R

class AddLostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lost)
    }

    companion object {
        private const val TAG = "AddLostActivity"
    }
}
