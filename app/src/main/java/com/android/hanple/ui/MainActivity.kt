package com.android.hanple.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.android.hanple.R
import com.android.hanple.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()
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

