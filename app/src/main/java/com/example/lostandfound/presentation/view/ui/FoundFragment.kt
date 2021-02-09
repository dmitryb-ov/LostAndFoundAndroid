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
import com.example.lostandfound.databinding.FoundFragmentBinding
import com.example.lostandfound.di.AppInjector
import com.example.lostandfound.presentation.view.recyclerview.found.FoundAdapter
import com.example.lostandfound.presentation.view.recyclerview.found.FoundModel
import com.example.lostandfound.presentation.viewmodel.FoundViewModel
import kotlinx.android.synthetic.main.found_fragment.*
import kotlinx.android.synthetic.main.found_fragment.view.*
import javax.inject.Inject

class FoundFragment : Fragment() {
    @Inject
    lateinit var viewModel: FoundViewModel
    lateinit var binding: FoundFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.plusFoundComponent().inject(this)
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.title = "Нашел"
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_found_posts.apply {
            layoutManager = LinearLayoutManager(activity)
//            adapter =
//                FoundAdapter(
//                    Found.foundList
//                ) {
//
//                }
//            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FoundFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = FoundAdapter(
            mutableListOf()
        ) {
            goItem(it)
        }

        binding.rvFoundPosts.adapter = adapter
        binding.foundViewModel = viewModel


//        val view = inflater.inflate(R.layout.found_fragment, container, false)
        binding.root.fab_found.setOnClickListener {
            Log.i(TAG, "Floating Action Button found pushed")
            startActivity(Intent(activity, AddFoundActivity::class.java))
        }
        return binding.root
    }

    private fun goItem(founds: FoundModel) {
        startActivity(Intent(activity, MoreInformationAboutFoundActivity::class.java).apply {
            putExtra("userName", founds.userName)
            putExtra("foundHead", founds.foundHead)
            putExtra("phone", founds.phone)
            putExtra("image", founds.foundPic)
            putExtra("date", founds.date)
            putExtra("authorId", founds.authorId)
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFounds()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppInjector.clearFoundComponent()
    }

    companion object {
        fun newInstance(): FoundFragment =
            FoundFragment()

        private const val TAG = "Found Fragment"
    }
}