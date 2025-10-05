package com.example.twogisapp.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.twogisapp.R
import com.example.twogisapp.databinding.ActivityFormBinding
import com.example.twogisapp.databinding.ActivityFormInfoBinding
import com.example.twogisapp.domain.entities.Request

class FormInfoActivity : AppCompatActivity() {

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
        ActivityFormInfoBinding.inflate(layoutInflater)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initViews()
        setupBottomNavigation()
        setActiveTab(4)
        val requestForm = intent.getSerializableExtra("REQUEST_FORM") as Request
        binding.ivInvalidType.setImageResource(requestForm.category)
        binding.btnSendMessage.setOnClickListener {
            Toast.makeText(this, "Открывается чат...", Toast.LENGTH_LONG).show()
        }
        binding.btnBuildRoad.setOnClickListener {
            Toast.makeText(this, "Маршрут построился на карте...", Toast.LENGTH_LONG).show()
        }
        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnAccept.setOnClickListener {
            Toast.makeText(this, "Заявка принята. Хорошего вечера!", Toast.LENGTH_LONG).show()
        }
        binding.tvFrom.text = requestForm.from
        binding.tvTo.text = requestForm.to
        binding.tvTime.text = requestForm.date
        binding.tvComment.text = requestForm.comment
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

        navItems = listOf(navSearch, navRides, navNavigator, navFriends, navVolunteers,  navProfile)
        navIcons = listOf(searchIcon, ridesIcon, navigatorIcon, friendsIcon, volunteersIcon,  profileIcon)
    }

    private fun setupBottomNavigation() {
        navSearch.setOnClickListener { setActiveTab(0) }
        navRides.setOnClickListener {
            setActiveTab(1)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()}
        navNavigator.setOnClickListener { setActiveTab(2) }
        navFriends.setOnClickListener { setActiveTab(3) }
        navVolunteers.setOnClickListener {
            setActiveTab(4)
            val intent = Intent(this, VolunteersActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
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