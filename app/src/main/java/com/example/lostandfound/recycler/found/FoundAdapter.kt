package com.example.lostandfound.recycler.found

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class FoundAdapter(
    var foundData: List<FoundModel>,
    private val clickLambda: (FoundModel) -> Unit
) : RecyclerView.Adapter<FoundHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundHolder =
        FoundHolder.create(parent, clickLambda)

    override fun onBindViewHolder(holder: FoundHolder, position: Int) =
        holder.bind(foundData[position])

    override fun getItemCount() = foundData.size

}