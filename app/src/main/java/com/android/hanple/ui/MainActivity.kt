package com.android.hanple.ui

import MapFragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.ActivityMainBinding
import com.android.hanple.ui.search.SearchFragment
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory())[SearchViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment()
        setNavigation()
        initPlaceSDK()

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.mapView, MapFragment())
            }
        }

//        각 메뉴 탭의 id를 setOf 안에 작성
//        val appBarConfiguration = AppBarConfiguration(setOf(..., R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initFragment() {
        supportFragmentManager.commit {
            replace(R.id.fr_main, SearchFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun setNavigation() {

        val navView : NavigationView = binding.navView

       navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_account -> {
                    // 액티비티 이동
                    val intent = Intent(this, ArchiveActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_view -> {
                    // 액티비티 이동
                }

                R.id.nav_bookmark -> {
                    // 액티비티 이동
                }
            }
           binding.drawerLayout.closeDrawer(GravityCompat.START)
           true
        }

        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        binding.drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
//                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            override fun onDrawerClosed(drawerView: View) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

  @SuppressLint("SuspiciousIndentation")
    private fun initPlaceSDK(){
        // Define a variable to hold the Places API key.
        //secret에서 정의한 API KEY가 안불러와져서 그냥 때려 박았습니다
        val apiKey = "AIzaSyCdjyOxbTIwn_f13N9XhrLnKtFJ2kpsG7M"

        // Log an error if apiKey is not set.
        if (apiKey.isEmpty() || apiKey == "DEFAULT_API_KEY") {
            Log.e("Places test", "No api key")
            finish()
            return
        }
        // Initialize the SDK
        // Place SDK를 초기화 하는 메소드
        Places.initializeWithNewPlacesApiEnabled(applicationContext, apiKey)
        val placesClient = Places.createClient(this)
        viewModel.setPlacesAPIClient(placesClient)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }
}
