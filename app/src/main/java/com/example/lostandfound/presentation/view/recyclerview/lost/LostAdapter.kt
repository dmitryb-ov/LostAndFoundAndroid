package com.example.lostandfound.presentation.view.recyclerview.lost

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.presentation.view.recyclerview.BindableAdapter

class LostAdapter(
    var lostData: MutableList<LostModel>,
    private val clickLambda: (LostModel) -> Unit
) : RecyclerView.Adapter<LostHolder>(), BindableAdapter<MutableList<LostModel>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LostHolder =
        LostHolder.create(
            parent,
            clickLambda
        )

    override fun getItemCount(): Int = lostData.size


    override fun onBindViewHolder(holder: LostHolder, position: Int) =
        holder.bind(lostData[position])

    override fun update(data: MutableList<LostModel>?) {
        if (data == null) {
            return
        }
        lostData.clear()
        notifyDataSetChanged()
        lostData.addAll(data)
        notifyDataSetChanged()
    }


}