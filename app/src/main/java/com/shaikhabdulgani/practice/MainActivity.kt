package com.shaikhabdulgani.practice

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager.LayoutParams
import android.view.animation.AnimationUtils
import android.widget.ExpandableListView
import android.widget.PopupWindow
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.transition.Fade
import androidx.transition.Visibility
import com.shaikhabdulgani.practice.databinding.ActivityMainBinding
import com.shaikhabdulgani.practice.databinding.TooltipLayoutBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "MainActivity"
    }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var giantCardAdapter: GiantCardAdapter
    private lateinit var motorAdapter: MotorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpToolbar()
        setUpNavigationView()
        setUpExpandableListView()

        setUpGiantCardsAdapter()
        setUpMotorCardAdapter()
        setUpTooltipClickEvent()

        populateQuickActions()
        populateBusinessSummary()
        populateQuickQuote()
        populateGiantStepsCard()
        populateCampaignCard()
    }

    private fun setUpTooltipClickEvent() {
        binding.giantStepsTitle.titleInfo.setOnClickListener(this)
    }

    private fun setUpMotorCardAdapter() {
        motorAdapter = MotorAdapter()
        val cardTitle = resources.getString(R.string.motor_subtitle)
        motorAdapter.differ.submitList(
            listOf(
                CampaignCardData(cardTitle),
                CampaignCardData(cardTitle),
                CampaignCardData(cardTitle),
                CampaignCardData(cardTitle)
            )
        )

        binding.motorRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.motorRv.adapter = motorAdapter
        binding.motorRv.setHasFixedSize(true)
        binding.motorRv.addItemDecoration(
            DotsIndicatorDecoration(
                10,
                30,
                60,
                resources.getColor(R.color.progress_track_color_gray, null),
                resources.getColor(R.color.dark_blue_color, null)
            )
        )
        PagerSnapHelper().attachToRecyclerView(binding.motorRv)
    }

    private fun setUpGiantCardsAdapter() {
        giantCardAdapter = GiantCardAdapter(listOf("1", "2", "3", "4"))
        binding.scrollableGiantCards.adapter = giantCardAdapter
        binding.scrollableGiantCards.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun populateGiantStepsCard() {
        binding.giantStepsTitle.title.text = resources.getString(R.string.giant_steps)
        binding.giantStepsTitle.titleInfo.setImageResource(R.drawable.ic_info)
        binding.giantStepsTitle.titleInfo.visibility = View.VISIBLE

        val points = resources.getString(R.string.default_points)
        val awayFromStr = resources.getString(R.string.away_from)
        val club = resources.getString(R.string.diamond_club)
        val spannableString = SpannableString("$points $awayFromStr $club")

        val start = spannableString.indexOf(club)
        val end = start + club.length
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            start,
            end,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        binding.giantStepsCard.pointsText.text = spannableString
    }

    private fun populateCampaignCard() {

        val campaignData = getAllCampaignData()

        binding.campaignTitle.title.text = resources.getString(R.string.campaign_title)

        binding.healthSubtitle.subtitle.text = resources.getString(R.string.health_subtitle)
        populateValues(binding.healthCard, campaignData[0], this)

        binding.motorSubtitle.subtitle.text = resources.getString(R.string.motor_subtitle)

        binding.travelSubtitle.subtitle.text = resources.getString(R.string.travel_subtitle)
        populateValues(binding.travelCard, campaignData[2], this)

        binding.commsLineSubtitle.subtitle.text = resources.getString(R.string.comms_line_subtitle)
        populateValues(binding.commsLineCard, campaignData[3], this)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.actionBarMain.toolbarHome)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    private fun setUpNavigationView() {
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, binding.main, R.string.nav_open, R.string.nav_close)
        binding.main.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        binding.navHeader.closeImgNav.setOnClickListener {
            binding.main.closeDrawer(GravityCompat.START)
        }
    }

    private fun setUpExpandableListView() {
        val adapter = ExpandableListAdapter(this, getNavMenuList())
        binding.expandableList.animation = AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_popup_enter)
        binding.expandableList.setAdapter(adapter)
        for (i in 0 until adapter.groupCount) {
            binding.expandableList.expandGroup(i)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun populateQuickQuote() {
        binding.quickQuoteTitle.title.text = "Quick Quote"

        binding.healthQuote.iconItemQuickQuote.setImageResource(R.drawable.ic_health_right_hand)
        binding.healthQuote.textItemQuickQuote.text = resources.getString(R.string.health_subtitle)

        binding.motorQuote.iconItemQuickQuote.setImageResource(R.drawable.ic_motor)
        binding.motorQuote.textItemQuickQuote.text = resources.getString(R.string.motor_subtitle)

        binding.travelQuote.iconItemQuickQuote.setImageResource(R.drawable.ic_travel)
        binding.travelQuote.textItemQuickQuote.text = resources.getString(R.string.travel_subtitle)

        binding.commsQuote.iconItemQuickQuote.setImageResource(R.drawable.ic_comm_lines)
        binding.commsQuote.textItemQuickQuote.text =
            resources.getString(R.string.comms_line_subtitle)
    }

    private fun populateBusinessSummary() {
        binding.summaryActionTitle.title.text = "Business Summary"
        binding.dateSummary.text = "02 May - 02 Aug’24"
        binding.totalPolicies.titleST.text = "Total Policies"
        binding.totalPolicies.numberST.text = "3,456"
        binding.totalGwp.titleST.text = "Total GWP"
        binding.totalGwp.numberST.text = "₹25.7Cr"
    }

    private fun populateQuickActions() {
        binding.claimsCard.typeQuickAction.text = "Claims"
        binding.claimsCard.countQuickAction.text = "2"

        binding.renewalCard.typeQuickAction.text = "Renewals"
        binding.renewalCard.countQuickAction.text = "12"
        binding.renewalCard.remainingDaysQuickAction.visibility = View.VISIBLE
        binding.renewalCard.remainingDaysQuickAction.text = "07 Days"

        binding.paymentsCard.typeQuickAction.text = "Payments"
        binding.paymentsCard.countQuickAction.text = "08"
    }


    override fun onClick(v: View?) {
        if (v == null) {
            return
        }
        val tooltipBinding = TooltipLayoutBinding.inflate(layoutInflater)
        val constraintLayout = tooltipBinding.root
        val popupWindow = PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

        constraintLayout.measure(widthMeasureSpec, heightMeasureSpec)

        val rootHeight = constraintLayout.measuredHeight

        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.contentView = constraintLayout

        val xOffset = tooltipBinding.topBalloonTooltip.run {
            -(drawable.intrinsicWidth / 2 + marginStart - v.width / 2)
        }

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenHeight = displayMetrics.heightPixels
        val imagePos = intArrayOf(2, 2)
        v.getLocationOnScreen(imagePos)
        val position = imagePos[1] + v.height + rootHeight + v.paddingBottom + v.marginBottom
        Log.d(TAG, "$screenHeight > $position")
        val yOffset: Int =
            if (screenHeight > position) {
                tooltipBinding.bottomBalloonTooltip.setImageIcon(null)
                0
            } else {
                tooltipBinding.topBalloonTooltip.setImageIcon(null)
                -(v.top + rootHeight)
            }

        popupWindow.showAsDropDown(v, xOffset, yOffset, Gravity.START)
        popupWindow.setBackgroundDrawable(null)
    }
}