package com.example.lostandfound.presentation.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lostandfound.R
import com.example.lostandfound.di.AppInjector
import kotlinx.android.synthetic.main.map_fragment.view.*

class OpenMapFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusMapComponent().inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.map_fragment, container, false)
        view.open_map.setOnClickListener {
            startActivity(Intent(activity, MapActivity::class.java))
        }
        return view
    }

    override fun onResume() {
        super.onResume()
    }

    companion object {
        val KEY = "7c504409-52d7-4b27-9acc-5c49234c503b"
        fun newInstance(): OpenMapFragment = OpenMapFragment()
    }
}