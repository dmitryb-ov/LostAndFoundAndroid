package com.example.lostandfound.presentation.view.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lostandfound.databinding.LostFragmentBinding
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.view.recyclerview.lost.LostAdapter
import com.example.lostandfound.presentation.view.recyclerview.lost.LostModel
import com.example.lostandfound.presentation.viewmodel.LostViewModel
import kotlinx.android.synthetic.main.lost_fragment.*
import kotlinx.android.synthetic.main.lost_fragment.view.*
import javax.inject.Inject

class LostFragment : Fragment() {

    @Inject
    lateinit var viewModel: LostViewModel
    lateinit var binding: LostFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusLostComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.title = "Потерял"
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_lost_posts.apply {
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun goItem(losts: LostModel) {
        startActivity(Intent(activity, MoreInformationAboutLostActivity::class.java).apply {
            putExtra("userName", losts.userName)
            putExtra("lostPic", losts.lostPic)
            putExtra("lostHead", losts.lostHead)
            putExtra("lostDate", losts.date)
            putExtra("date", losts.lostDate)
            putExtra("description", losts.description)
            putExtra("phoneNumber", losts.phoneNumber)
            putExtra("authorId", losts.authorId)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LostFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
//        (activity as MainActivity).supportActionBar?.title = "Потерял"

        val adapter = LostAdapter(
            mutableListOf()
        ) {
            goItem(it)
        }

        binding.rvLostPosts.adapter = adapter
        binding.lostViewModel = viewModel

        binding.root.fab_lost.setOnClickListener {
            Log.i(TAG, "Floating Action Button lost pushed")
            startActivity(Intent(activity, AddLostActivity::class.java))
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadLosts()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearLostComponent()
    }

    companion object {
        fun newInstance(): LostFragment =
            LostFragment()

        private const val TAG = "Lost Fragment"
    }


}