package com.shaikhabdulgani.practice

import android.view.View

data class CampaignCardData(
    val title: String,
    val dateQuarterly: String = "Quarterly\n02 May - 02 Aug’24",
    val asOnDate: String = "As on 12 Jun 24",
    val targetTitle: String = "Slab Target (Wtd. GWP)",
    val targetCount: Int = 75_000,
    val achievedTitle: String = "Achieved (Wtd. GWP)",
    val achievedCount: Int = 20_500,
    val progressImageIcon: Int = 0,
    val isEligible: Boolean = true,
    val clubGoldVisibility: Int = View.GONE,
    val bottomLayout: CampaignBottomLayout = CampaignBottomLayout.UpcomingTargetLayout(),
)


