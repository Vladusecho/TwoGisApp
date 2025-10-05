package com.example.twogisapp.presentation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.twogisapp.R
import com.example.twogisapp.databinding.ActivityFormBinding
import com.example.twogisapp.domain.entities.Request
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

class FormActivity : AppCompatActivity() {

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

    private lateinit var catElements: List<ImageView>

    private lateinit var invalidDriverIcon: ImageView
    private lateinit var blindPersonIcon: ImageView
    private lateinit var oldPersonIcon: ImageView
    private lateinit var notHearPersonIcon: ImageView
    private lateinit var notSmartPersonIcon: ImageView

    private var checkCat by Delegates.notNull<Int>()
    val binding by lazy {
        ActivityFormBinding.inflate(layoutInflater)
    }

    private val calendar = Calendar.getInstance()

    private val viewToDrawableMap = mapOf(
        R.id.ivInvalidDriver to R.drawable.ic_invalid_driver_cat_active,
        R.id.ivBlindPerson to R.drawable.ic_blind_cat_active,
        R.id.ivOldMan to R.drawable.ic_old_cat_active,
        R.id.ivNotHear to R.drawable.ic_not_hear_cat_active,
        R.id.ivNotSmartPerson to R.drawable.ic_not_smart_cat_active
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        initViews()
        setupBottomNavigation()
        setActiveTab(1)
        setupDatePicker()
        setCatActive(0)
        setupCategory()
        setOnClickListeners()
        getRoute()
    }

    private fun getRoute() {
        binding.etFromReceived.setText(intent.getStringExtra(ARRIVAL_FROM))
        binding.etToReceived.setText(intent.getStringExtra(ARRIVAL_TO))
    }

    private fun setOnClickListeners() {
        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnSendform.setOnClickListener { l ->
            binding.etFromReceived.checkNotValid()
            binding.etToReceived.checkNotValid()
            if (binding.etFromReceived.error != null || binding.etToReceived.error != null) return@setOnClickListener
            if (binding.tvTime.text == "" || binding.tvTime.text == TIME_ERROR) {
                binding.tvTime.text = TIME_ERROR
                binding.tvTime.setTextColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.holo_red_light
                    )
                )
                return@setOnClickListener
            }
            val intent = Intent(this, VolunteersActivity::class.java)
            intent.putExtra(
                "READY_FORM", Request(
                    viewToDrawableMap[checkCat]!!,
                    binding.etFromReceived.text.toString(),
                    binding.etToReceived.text.toString(),
                    binding.tvTime.text.toString(),
                    binding.etComment.text.toString()
                )
            )
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }
    }

    private fun EditText.checkNotValid() {
        if (this.text.isEmpty()) {
            this.error = EDIT_TEXT_ERROR
        } else {
            this.error = null
        }
    }

    private fun setupDatePicker() {
        binding.btnSetDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)

                showTimePickerDialog()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.show()
    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateDateTimeInEditText()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )

        timePicker.show()
    }

    private fun updateDateTimeInEditText() {
        binding.tvTime.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        binding.tvTime.text = dateFormat.format(calendar.time)

        Toast.makeText(this, "Дата и время установлены", Toast.LENGTH_SHORT).show()
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

        invalidDriverIcon = binding.ivInvalidDriver
        blindPersonIcon = binding.ivBlindPerson
        oldPersonIcon = binding.ivOldMan
        notHearPersonIcon = binding.ivNotHear
        notSmartPersonIcon = binding.ivNotSmartPerson

        catElements = listOf(
            invalidDriverIcon,
            blindPersonIcon,
            oldPersonIcon,
            notHearPersonIcon,
            notSmartPersonIcon
        )

        navItems = listOf(navSearch, navRides, navNavigator, navFriends, navVolunteers, navProfile)
        navIcons =
            listOf(searchIcon, ridesIcon, navigatorIcon, friendsIcon, volunteersIcon, profileIcon)
    }

    private fun setupBottomNavigation() {
        navSearch.setOnClickListener { setActiveTab(0) }
        navRides.setOnClickListener {

        }
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

    private fun setupCategory() {
        invalidDriverIcon.setOnClickListener { setActiveCat(0) }
        blindPersonIcon.setOnClickListener { setActiveCat(1) }
        oldPersonIcon.setOnClickListener { setActiveCat(2) }
        notHearPersonIcon.setOnClickListener { setActiveCat(3) }
        notSmartPersonIcon.setOnClickListener { setActiveCat(4) }
        checkCat = catElements[0].id
    }

    private fun setActiveTab(activeIndex: Int) {
        resetAllTabs()
        setTabActive(activeIndex)
    }

    private fun setActiveCat(activeIndex: Int) {
        resetAllCats()
        setCatActive(activeIndex)
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

    private fun resetAllCats() {
        val inactiveIcons = listOf(
            R.drawable.ic_invalid_driver_cat,
            R.drawable.ic_blind_cat,
            R.drawable.ic_old_cat,
            R.drawable.ic_not_hear_cat,
            R.drawable.ic_not_smart_cat,
        )

        catElements.forEachIndexed { index, imageView ->
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

    private fun setCatActive(activeIndex: Int) {
        val activeIcons = listOf(
            R.drawable.ic_invalid_driver_cat_active,
            R.drawable.ic_blind_cat_active,
            R.drawable.ic_old_cat_active,
            R.drawable.ic_not_hear_cat_active,
            R.drawable.ic_not_smart_cat_active,
        )

        catElements[activeIndex].setImageResource(activeIcons[activeIndex])
        checkCat = catElements[activeIndex].id
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    companion object {

        private const val ARRIVAL_FROM = "ARRIVAL_FROM"
        private const val ARRIVAL_TO = "ARRIVAL_TO"
        private const val TIME_ERROR = "Укажите дату вашего выхода"
        private const val EDIT_TEXT_ERROR = "Введите недостающее значение"
    }
}
