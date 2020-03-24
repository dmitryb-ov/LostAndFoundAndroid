package com.example.lostandfound.recycler.lost

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LostAdapter(
    var lostData: List<LostModel>,
    private val clickLambda: (LostModel) -> Unit
) : RecyclerView.Adapter<LostHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostHolder =
        LostHolder.create(parent, clickLambda)

    override fun getItemCount() = lostData.size


    override fun onBindViewHolder(holder: LostHolder, position: Int) =
        holder.bind(lostData[position])
}