package com.example.lostandfound.recycler.lost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post_lost.*

class LostHolder(
    override val containerView: View,
    private val clickLambda: (LostModel) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(lost: LostModel) {
        tv_lost_user_name.text = lost.userName
        tv_lost_user_date.text = lost.date
        tv_lost_post_header.text = lost.lostHead
        iv_lost_post_img.setImageResource(lost.lostPic)
        itemView.setOnClickListener {
            clickLambda(lost)
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (LostModel) -> Unit) = LostHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post_lost, parent, false
            ), clickLambda
        )
    }
}