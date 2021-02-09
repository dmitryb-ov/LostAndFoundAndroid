package com.example.lostandfound.presentation.view.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lostandfound.R
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.view.recyclerview.lost.LostAdapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var adapter: LostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.injectMainActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "MainActivity created")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            Log.e(TAG, token)
        })
        if (savedInstanceState == null) {
            val fragment =
                ProfileFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, fragment, javaClass.simpleName)
                .commit()
        }

        val key = intent.getStringExtra("nav_key")
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        when (key) {
            "lost" -> {
                bottomNavigationView.selectedItemId = R.id.navigation_lost
            }
            "found" -> {
                bottomNavigationView.selectedItemId = R.id.navigation_found
            }
            else -> {
                bottomNavigationView.selectedItemId = R.id.navigation_profile
            }
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_lost -> {
                    val fragment =
                        LostFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select lost navigation")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_found -> {
                    val fragment =
                        FoundFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select found navigation")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val fragment =
                        ProfileFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select profile navigation")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_map -> {
                    val fragment = OpenMapFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                        .commit()
                    Log.i(TAG, "Select map navigation")
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    companion object {
        private const val TAG = "MainActivity"
    }
}
