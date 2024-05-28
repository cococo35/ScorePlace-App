package com.android.hanple.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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

//        layout xml에 있는 navigation view 가져오기
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_fragment_host)
        //nav_fragment_host 에 fragment가 들어가도록 한다.

//        각 메뉴 탭의 id를 setOf 안에 작성
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_search, R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }
}

