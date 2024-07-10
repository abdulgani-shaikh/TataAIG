package com.shaikhabdulgani.practice

sealed class CampaignBottomLayout(val buttonText: String) {
    data class ClubLayout(
        val earned: Int = 50000,
        val target: Int = 20000000,
        val clubName: String = "Diamond club",
    ) : CampaignBottomLayout("View Incentive Details")

    data class UpcomingTargetLayout(
        val upcomingTargetTitle: String = "Upcoming Slab Target (Wtd. GWP)",
        val upcomingTargetCount: Int = 25_00_000,
    ) : CampaignBottomLayout("View Campaign")
}