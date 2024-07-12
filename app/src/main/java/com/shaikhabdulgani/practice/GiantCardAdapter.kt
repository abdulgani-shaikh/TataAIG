package com.shaikhabdulgani.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shaikhabdulgani.practice.databinding.LayoutGiantCardBinding

class GiantCardAdapter(private val list: List<String>) :
    RecyclerView.Adapter<GiantCardAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutGiantCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun getItemCount(): Int = list.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }
}