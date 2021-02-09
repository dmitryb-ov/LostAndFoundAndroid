package com.example.lostandfound.presentation.view.recyclerview.lost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lostandfound.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post_lost.*

class LostHolder(
    override val containerView: View,
    private val clickLambda: (LostModel) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(lost: LostModel) {
        tv_lost_user_name.text = lost.userName
        tv_lost_user_date.text = lost.lostDate
        tv_lost_post_header.text = lost.lostHead
        Glide.with(containerView.context).load(lost.lostPic).into(iv_lost_post_img)
        itemView.setOnClickListener {
            clickLambda(lost)
        }
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (LostModel) -> Unit) =
            LostHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_post_lost, parent, false
                ), clickLambda
            )
    }
}