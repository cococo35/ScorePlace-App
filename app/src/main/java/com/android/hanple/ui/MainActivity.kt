package com.android.hanple.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.android.hanple.Dust.DustViewModel
import com.android.hanple.Dust.DustViewModelFactory
import com.android.hanple.R
import com.android.hanple.Weather.WeatherViewModel
import com.android.hanple.Weather.WeatherViewModelFactory
import com.android.hanple.data.congestion.CongestionViewModel
import com.android.hanple.data.congestion.CongestionViewModelFactory
import com.android.hanple.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory())[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getStartAddress("경복궁")
        viewModel.getEndAddress("코엑스")
        viewModel.getWeatherData()
        viewModel.getDustData()
        viewModel.getCongestionData()
        setNavigation()

//        각 메뉴 탭의 id를 setOf 안에 작성
//        val appBarConfiguration = AppBarConfiguration(setOf(..., R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    private fun setNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navFragmentHost.id) as NavHostFragment
        val navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_search -> {
                    navController.popBackStack(route = "search_route", inclusive = false)
                }

                R.id.navigation_recommend -> {
                    navController.popBackStack(route = "recommend_route", inclusive = false)
                }

                R.id.navigation_settings -> {
                    navController.popBackStack(route = "settings_route", inclusive = false)
                }
            }

            item.onNavDestinationSelected(navController)
        }
    }
}

