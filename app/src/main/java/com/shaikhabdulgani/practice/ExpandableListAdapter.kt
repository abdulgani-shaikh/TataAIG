package com.shaikhabdulgani.practice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import androidx.core.content.ContextCompat
import com.shaikhabdulgani.practice.databinding.ItemExpandableGroupBinding
import com.shaikhabdulgani.practice.databinding.ItemNavChildBinding


class ExpandableListAdapter(
    private val context: Context,
    private val list: List<NavigationMenuGroup>
) : BaseExpandableListAdapter() {

    private var expandingGroup = BooleanArray(list.size) { false }

    override fun getGroupCount(): Int {
        return list.size
    }

    override fun onGroupExpanded(groupPosition: Int) {
        expandingGroup[groupPosition] = true
        super.onGroupExpanded(groupPosition)
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return list[groupPosition].items.size
    }

    override fun getGroup(groupPosition: Int): NavigationMenuGroup {
        return list[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return list[groupPosition].items[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = if (convertView == null) {
            ItemExpandableGroupBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemExpandableGroupBinding.bind(convertView)
        }

        val indicator = if (list[groupPosition].items.isEmpty()) {
            null
        } else if (isExpanded) {
            ContextCompat.getDrawable(context, R.drawable.ic_up)
        } else {
            ContextCompat.getDrawable(context, R.drawable.ic_down)
        }
        val data = getGroup(groupPosition)
        binding.itemExpandableListView.text = data.text
        if (getGroup(groupPosition).icon != 0) {
            binding.itemExpandableListView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                ContextCompat.getDrawable(
                    context,
                    data.icon
                ), null, indicator, null
            )
        }

        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding = if (convertView == null) {
            ItemNavChildBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemNavChildBinding.bind(convertView)
        }
        binding.childItemNav.text = getChild(groupPosition, childPosition)

        if (expandingGroup[groupPosition]) {
            binding.root.animation = AnimationUtils.loadAnimation(context, R.anim.expand_anim)
            expandingGroup[groupPosition] = false
        }
        return binding.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

}