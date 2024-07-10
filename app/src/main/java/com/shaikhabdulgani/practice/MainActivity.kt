package com.shaikhabdulgani.practice

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.shaikhabdulgani.practice.databinding.ActivityMainBinding
import com.shaikhabdulgani.practice.databinding.CardCampaignEligibilityLayoutBinding


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpToolbar()
        setUpNavigationView()
        setUpExpandableListView()

        populateQuickActions()
        populateBusinessSummary()
        populateQuickQuote()

        populateLargeCard()
    }

    private fun populateLargeCard() {
        val giantStepsData = CampaignCardData(
            "",
            dateQuarterly = "FY 24-25",
            progressImageIcon = 0,
            targetTitle = "Target Premium",
            targetCount = 11_000000,
            achievedTitle = "Earned Premium",
            achievedCount = 1250000,
            isEligible = false,
            bottomLayout = CampaignBottomLayout.ClubLayout()
        )

        val campaignData = getAllCampaignData()

        binding.giantStepsTitle.title.text = resources.getString(R.string.giant_steps)
        binding.giantStepsTitle.title.setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            ContextCompat.getDrawable(this, R.drawable.ic_info),
            null
        )
        populateValues(binding.giantStepsCard, giantStepsData, this)

        binding.campaignTitle.title.text = resources.getString(R.string.campaign_title)

        binding.healthSubtitle.subtitle.text = resources.getString(R.string.health_subtitle)
        populateValues(binding.healthCard, campaignData[0], this)

        binding.motorSubtitle.subtitle.text = resources.getString(R.string.motor_subtitle)
        populateValues(binding.motorCard, campaignData[1], this)

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

        binding.travelQuote.iconItemQuickQuote.setImageResource(R.drawable.ic_health_right_hand)
        binding.travelQuote.textItemQuickQuote.text = resources.getString(R.string.travel_subtitle)

        binding.commsQuote.iconItemQuickQuote.setImageResource(R.drawable.ic_comm_lines)
        binding.commsQuote.textItemQuickQuote.text = resources.getString(R.string.comms_line_subtitle)
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
}