package com.example.lostandfound.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.R
import com.example.lostandfound.activity.AddFoundActivity
import com.example.lostandfound.data.Founds
import com.example.lostandfound.recycler.found.FoundAdapter
import kotlinx.android.synthetic.main.found_fragment.*
import kotlinx.android.synthetic.main.found_fragment.view.*

class FoundFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.title = "Нашел"
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_found_posts.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = FoundAdapter(Founds.foundList) {

            }
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.found_fragment, container, false)
        view.fab_found.setOnClickListener {
            Log.i(TAG, "Floating Action Button found pushed")
            startActivity(Intent(activity, AddFoundActivity::class.java))
        }
        return view;
    }

    companion object {
        fun newInstance(): FoundFragment = FoundFragment()
        private const val TAG = "Found Fragment"
    }
}