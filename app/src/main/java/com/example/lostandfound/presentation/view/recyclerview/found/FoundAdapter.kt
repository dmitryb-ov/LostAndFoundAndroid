package com.example.lostandfound.presentation.view.recyclerview.found

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.presentation.view.recyclerview.BindableAdapter

class FoundAdapter(
    var foundData: MutableList<FoundModel>,
    private val clickLambda: (FoundModel) -> Unit
) : RecyclerView.Adapter<FoundHolder>(), BindableAdapter<MutableList<FoundModel>> {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundHolder =
        FoundHolder.create(
            parent,
            clickLambda
        )

    override fun onBindViewHolder(holder: FoundHolder, position: Int) =
        holder.bind(foundData[position])

    override fun getItemCount() = foundData.size

    override fun update(data: MutableList<FoundModel>?) {
        if (data == null) {
            return
        }
        foundData.clear()
        notifyDataSetChanged()
        foundData.addAll(data)
        notifyDataSetChanged()
    }

}