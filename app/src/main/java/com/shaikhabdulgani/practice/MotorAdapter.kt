package com.shaikhabdulgani.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shaikhabdulgani.practice.databinding.CardCampaignEligibilityLayoutBinding

class MotorAdapter : RecyclerView.Adapter<MotorAdapter.MyViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<CampaignCardData>() {
        override fun areItemsTheSame(
            oldItem: CampaignCardData,
            newItem: CampaignCardData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CampaignCardData,
            newItem: CampaignCardData
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ: AsyncListDiffer<CampaignCardData> = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            CardCampaignEligibilityLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CardCampaignEligibilityLayoutBinding.bind(itemView)
    }
}
