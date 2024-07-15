package com.shaikhabdulgani.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shaikhabdulgani.practice.databinding.ItemNavChildBinding
import com.shaikhabdulgani.practice.databinding.ItemNavGroupBinding

class MenuGroupAdapter(private val list: List<NavigationMenuGroup>) :
    RecyclerView.Adapter<MenuGroupAdapter.MenuGroupViewHolder>() {

    fun interface OnClickListener {
        fun onClick(position: Int)
    }

    private lateinit var listener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuGroupViewHolder {
        val binding =
            ItemNavGroupBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MenuGroupViewHolder(binding)
    }

    fun setOnItemClickListener(onClick: OnClickListener) {
        this.listener = onClick
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MenuGroupViewHolder, position: Int) {
        val data = list[position]
        val context = holder.itemView.context
        val indicator = if (list[position].items.isEmpty()) {
            null
        } else if (data.isExpanded) {
            ContextCompat.getDrawable(context, R.drawable.ic_up)
        } else {
            ContextCompat.getDrawable(context, R.drawable.ic_down)
        }
        val drawableStart = ContextCompat.getDrawable(context, data.icon)
        holder.apply {
            binding.navGroupText.text = data.text
            if (data.icon != 0) {
                binding.navGroupText.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    drawableStart, null, indicator, null
                )
            }
            if (data.items.isEmpty()) {
                return
            }
            binding.navGroupList.adapter =
                NavChildAdapter(data.items)
            binding.navGroupList.layoutManager = LinearLayoutManager(context)

            if (list[position].isExpanded) {
                binding.navGroupList.visibility = View.VISIBLE
            } else {
                binding.navGroupList.visibility = View.GONE
            }
        }
    }

    private class NavChildAdapter(val list: List<String>) :
        RecyclerView.Adapter<NavChildAdapter.NavChildViewHolder>() {
        class NavChildViewHolder(val binding: ItemNavChildBinding) :
            ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavChildViewHolder {
            val binding = ItemNavChildBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NavChildViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: NavChildViewHolder, position: Int) {
            holder.binding.childItemNav.text = list[position]
        }
    }

    inner class MenuGroupViewHolder(val binding: ItemNavGroupBinding) :
        ViewHolder(binding.root) {
        init {
            binding.navGroupText.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION || list[adapterPosition].items.isEmpty()) {
                    return@setOnClickListener
                }

                list[adapterPosition].isExpanded = !list[adapterPosition].isExpanded
                binding.navGroupList.scrollToPosition(list[adapterPosition].items.size - 1)

                if (this@MenuGroupAdapter::listener.isInitialized) {
                    listener.onClick(adapterPosition)
                }
                notifyItemChanged(adapterPosition)
            }
        }
    }
}
