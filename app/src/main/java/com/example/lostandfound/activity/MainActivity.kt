package com.example.lostandfound.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.fragments.FoundFragment
import com.example.lostandfound.fragments.LostFragment
import com.example.lostandfound.fragments.ProfileFragment
import com.example.lostandfound.recycler.lost.LostAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: LostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "MainActivity created")

        if (savedInstanceState == null) {
            val fragment = ProfileFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, javaClass.simpleName)
                .commit()
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView.selectedItemId = R.id.navigation_profile
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_lost -> {
                    val fragment = LostFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select lost navigation")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_found -> {
                    val fragment = FoundFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select found navigation")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val fragment = ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select profile navigation")
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    companion object {
        private const val TAG = "MainActivity"
    }
}
