package com.example.lostandfound.presentation.view.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.presentation.view.recyclerview.BindableAdapter

@BindingAdapter("recycler")
fun <T> setSubsRecyclerViewProperties(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).update(data)
    }
}