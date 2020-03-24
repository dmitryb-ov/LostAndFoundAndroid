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
import com.example.lostandfound.activity.AddLostActivity
import com.example.lostandfound.activity.MoreInformationAboutLostActivity
import com.example.lostandfound.data.Losts
import com.example.lostandfound.recycler.lost.LostAdapter
import com.example.lostandfound.recycler.lost.LostModel
import kotlinx.android.synthetic.main.lost_fragment.*
import kotlinx.android.synthetic.main.lost_fragment.view.*

class LostFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.title = "Потерял"
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_lost_posts.apply {
            layoutManager = LinearLayoutManager(activity)
            //Change lostlist
            adapter = LostAdapter(Losts.lostList2) {
                goItem(it)
            }
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun goItem(losts: LostModel) {
        startActivity(Intent(activity, MoreInformationAboutLostActivity::class.java).apply {
            putExtra("userName", losts.userName)
            putExtra("lostPic", losts.lostPic)
            putExtra("lostHead", losts.lostHead)
            putExtra("date", losts.date)
            putExtra("lostDate", losts.lostDate)
            putExtra("location", losts.location)
            putExtra("description", losts.description)
            putExtra("phoneNumber", losts.phoneNumber)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.lost_fragment, container, false)
        view.fab_lost.setOnClickListener {
            Log.i(TAG, "Floating Action Button lost pushed")
            startActivity(Intent(activity, AddLostActivity::class.java))
        }
        return view
    }

    companion object {
        fun newInstance(): LostFragment = LostFragment()
        private const val TAG = "Lost Fragment"
    }


}