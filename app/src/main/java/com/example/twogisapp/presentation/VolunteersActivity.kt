package com.example.twogisapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.twogisapp.R
import com.example.twogisapp.databinding.ActivityVolunteersBinding
import com.example.twogisapp.domain.entities.Form
import com.example.twogisapp.domain.entities.Request

class VolunteersActivity : AppCompatActivity() {

    private lateinit var formsAdapter: VolunteersAdapter

    private lateinit var navSearch: LinearLayout
    private lateinit var navFriends: LinearLayout
    private lateinit var navRides: LinearLayout
    private lateinit var navProfile: LinearLayout
    private lateinit var navVolunteers: LinearLayout
    private lateinit var navNavigator: LinearLayout

    private lateinit var searchIcon: ImageView
    private lateinit var friendsIcon: ImageView
    private lateinit var ridesIcon: ImageView
    private lateinit var profileIcon: ImageView
    private lateinit var volunteersIcon: ImageView
    private lateinit var navigatorIcon: ImageView

    private lateinit var navItems: List<LinearLayout>
    private lateinit var navIcons: List<ImageView>

    val binding by lazy {
        ActivityVolunteersBinding.inflate(layoutInflater)
    }

    companion object {
        private const val REQUEST_FORM = "REQUEST_FORM"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initViews()
        setupBottomNavigation()
        setActiveTab(4)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val newRequest = intent.getSerializableExtra("READY_FORM") as? Request
        formsAdapter = VolunteersAdapter { onClick ->
            val intent = Intent(this, FormInfoActivity::class.java)
            intent.putExtra(REQUEST_FORM, newRequest)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
        if (newRequest != null) {
            val newForm = Form(
                newRequest.date,
                newRequest.from,
                newRequest.to,
                newRequest.category
            )
            formsAdapter.items.add(newForm)
        }

        with(binding.rvForms) {
            layoutManager = LinearLayoutManager(context)
            adapter = formsAdapter
        }
    }

    private fun initViews() {
        navSearch = binding.llSearchNav
        navFriends = binding.llFriendsNav
        navRides = binding.llRideNav
        navProfile = binding.llProfileNav
        navVolunteers = binding.llVolunteersNav
        navNavigator = binding.llNavigatorNav

        searchIcon = binding.icSearch
        friendsIcon = binding.icFriends
        ridesIcon = binding.icRide
        profileIcon = binding.icProfiles
        volunteersIcon = binding.icVolunteers
        navigatorIcon = binding.icNavigator

        navItems = listOf(navSearch, navRides, navNavigator, navFriends, navVolunteers, navProfile)
        navIcons =
            listOf(searchIcon, ridesIcon, navigatorIcon, friendsIcon, volunteersIcon, profileIcon)
    }

    private fun setupBottomNavigation() {
        navSearch.setOnClickListener { setActiveTab(0) }
        navRides.setOnClickListener {
            setActiveTab(1)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
        navNavigator.setOnClickListener { setActiveTab(2) }
        navFriends.setOnClickListener { setActiveTab(3) }
        navVolunteers.setOnClickListener { setActiveTab(4) }
        navProfile.setOnClickListener { setActiveTab(5) }
    }

    private fun setActiveTab(activeIndex: Int) {
        resetAllTabs()
        setTabActive(activeIndex)
    }

    private fun resetAllTabs() {
        val inactiveIcons = listOf(
            R.drawable.ic_search_navigation_no_active,
            R.drawable.ic_ride_nav_no_active,
            R.drawable.ic_navigator_nav_no_active,
            R.drawable.ic_friends_nav_no_active,
            R.drawable.ic_volunteers_nav_no_active,
            R.drawable.ic_profile_nav_no_active
        )

        navIcons.forEachIndexed { index, imageView ->
            imageView.setImageResource(inactiveIcons[index])
        }
    }

    private fun setTabActive(activeIndex: Int) {
        val activeIcons = listOf(
            R.drawable.ic_search_nav_active,
            R.drawable.ic_ride_nav_active,
            R.drawable.ic_navigator_nav_active,
            R.drawable.ic_friends_nav_active,
            R.drawable.ic_volunteers_nav_active,
            R.drawable.ic_profile_nav_active
        )

        navIcons[activeIndex].setImageResource(activeIcons[activeIndex])
    }
}