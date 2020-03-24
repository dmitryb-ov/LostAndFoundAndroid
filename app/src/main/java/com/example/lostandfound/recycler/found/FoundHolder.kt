package com.example.lostandfound.recycler.found

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lostandfound.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post_found.*

class FoundHolder(
    override val containerView: View,
    private val clickLambda: (FoundModel) -> Unit
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(found: FoundModel) {
        tv_found_user_name.text = found.userName
        tv_found_user_date.text = found.date
        tv_found_post_header.text = found.foundHead
        iv_found_post_img.setImageResource(found.foundPic)
    }

    companion object {
        fun create(parent: ViewGroup, clickLambda: (FoundModel) -> Unit) = FoundHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post_found, parent, false
            ), clickLambda
        )
    }
}