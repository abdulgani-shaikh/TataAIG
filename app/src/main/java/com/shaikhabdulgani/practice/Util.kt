package com.shaikhabdulgani.practice

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shaikhabdulgani.practice.databinding.CardCampaignEligibilityLayoutBinding
import java.util.Locale
import kotlin.math.abs


fun Int.toFormattedString(): String {
    val absNum = abs(this)

    return when {
        absNum < 1000 -> this.toString()
        absNum < 100000 -> String.format(Locale.ENGLISH, "%.1fK", this / 1000.0)
        absNum < 10000000 -> String.format(Locale.ENGLISH, "%.1fL", this / 100000.0)
        else -> String.format(Locale.ENGLISH, "%.1fCr", this / 10000000.0)
    }
}

fun TextView.setTextAndLeftDrawable(context: Context, text: String, drawableId: Int) {
    this.text = text
    setCompoundDrawablesRelativeWithIntrinsicBounds(
        ContextCompat.getDrawable(context, drawableId), null, null, null
    )
}

fun populateValues(
    view: CardCampaignEligibilityLayoutBinding,
    data: CampaignCardData,
    context: Context
) {
    view.apply {
        //top section
        if (data.title.isBlank()){
            titleCampaignCard.visibility = View.GONE
        }else{
            titleCampaignCard.text = data.title
        }
        dateCampaign.text = data.dateQuarterly
        asOnDate.text = data.asOnDate
        //top section end

        //middle section
        targetDualText.titleST.text = data.achievedTitle
        targetDualText.numberST.text = data.achievedCount.toFormattedString()
        achievedDualText.titleST.text = data.achievedTitle
        achievedDualText.numberST.text = data.achievedCount.toFormattedString()

        if (data.progressImageIcon > 0) {
            circularProgressImageCampaign.setImageResource(data.progressImageIcon)
        }else{
            circularProgressImageCampaign.setImageIcon(null)
        }
//        circularProgressCampaign.progress = (data.achievedCount/data.targetCount)*100

        targetDualText.titleST.text = data.targetTitle
        targetDualText.numberST.text = data.targetCount.toFormattedString()

        achievedDualText.titleST.text = data.achievedTitle
        achievedDualText.numberST.text = data.achievedCount.toFormattedString()

        achievedDualText.numberST.setTextColor(
            context.resources.getColor(
                R.color.progress_indicator_color_green,
                null
            )
        )

        if (data.isEligible) {
            eligibilityTextCampaign.setTextAndLeftDrawable(
                context,
                "Eligible",
                R.drawable.ic_green_dot
            )
        } else {
            eligibilityTextCampaign.setTextAndLeftDrawable(
                context,
                "Not Eligible",
                R.drawable.ic_red_dot
            )
        }

        membershipGroup.visibility = data.clubGoldVisibility

        upcomingTargetTitle.text = data.upcomingTargetTitle
        upcomingTargetCount.text = data.upcomingTargetCount.toFormattedString()
    }
}

fun getAllCampaignData(): List<CampaignCardData> {
    val healthData = CampaignCardData(
        title = "Health Quarterly Campaign",
        progressImageIcon = R.drawable.ic_health_blue
    )

    val motorData = CampaignCardData(
        title = "Motor Quarterly Campaign",
        progressImageIcon = R.drawable.ic_motor_blue,
        isEligible = false,
    )

    val travelData = CampaignCardData(
        title = "Travel 24 Campaign",
        progressImageIcon = R.drawable.ic_travel_blue,
    )

    val commLineData = CampaignCardData(
        title = "Comm. Lines Quarterly Campaign",
        progressImageIcon = R.drawable.ic_comm_lines_blue,
        clubGoldVisibility = View.VISIBLE,
        targetTitle = "Slab Target",
        targetCount = 75_000,
        achievedTitle =  "Achieved",
        achievedCount = 55_000,
    )
    return arrayListOf(healthData,motorData,travelData,commLineData)
}

fun getNavMenuList(): List<NavigationMenuGroup> = listOf(
    NavigationMenuGroup(
        R.drawable.ic_knowledge_center,
        "Knowledge Centre",
        listOf("Products", "Engagement", "Agency Connect")
    ),
    NavigationMenuGroup(
        R.drawable.ic_knowledge_center,
        "Servicing",
        listOf("Claims", "Endorsements")
    ),
    NavigationMenuGroup(
        R.drawable.ic_knowledge_center,
        "Renewals",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_knowledge_center,
        "Quick Quote",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_business_reports,
        "Business Reports",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_giant_steps,
        "Giant Steps",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_campaigns,
        "Campaigns",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_knowledge_center,
        "Commissions",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_locators,
        "Locators",
        listOf()
    ),
    NavigationMenuGroup(
        R.drawable.ic_help,
        "Help",
        listOf("Contact RM", "FAQs", "Raise a concern")
    ),
    NavigationMenuGroup(
        R.drawable.ic_logout,
        "Logout",
        listOf()
    ),
)
