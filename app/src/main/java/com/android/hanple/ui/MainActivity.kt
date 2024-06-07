package com.android.hanple.ui

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
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

    private lateinit var callback: OnBackPressedCallback
    private var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment()
        setNavigation()
        initTest()
//
//        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                replace(R.id.fr_main, MapFragment())
//            }
//        }

//        각 메뉴 탭의 id를 setOf 안에 작성
//        val appBarConfiguration = AppBarConfiguration(setOf(..., R.id.navigation_settings))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    if (System.currentTimeMillis() - backPressedTime >= 2000) {
                        backPressedTime = System.currentTimeMillis()
                        Toast.makeText(this@MainActivity, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
                            .show()
                    } else if (System.currentTimeMillis() - backPressedTime < 2000) {
                        AlertDialog.Builder(this@MainActivity)
                            .setTitle("종료")
                            .setMessage("앱을 종료하시겠습니까?")
                            .setPositiveButton("YES", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    this@MainActivity.finish()
                                }
                            })
                            .setNegativeButton("NO", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                }
                            })
                            .create().show()
                    }
                }
            }
        }
        onBackPressedDispatcher.addCallback(this,callback)
        //맵 테스트 용
//        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                replace(R.id.mapView, MapFragment())
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        binding.btnMainMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    private fun initFragment() {
        supportFragmentManager.commit {
            replace(R.id.fr_main, SearchFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }


    private fun setNavigation() {

        val navView: NavigationView = binding.navView

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_account -> {
                    // 액티비티 이동
//                    val intent = Intent(this, ArchiveActivity::class.java)
//                    startActivity(intent)
                }

                R.id.nav_search_log -> {
                    // 액티비티 이동
                    val intent = Intent(this, ArchiveActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_bookmark -> {
                    // 액티비티 이동
                    val intent = Intent(this, ArchiveActivity::class.java)
                    startActivity(intent)
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initTest() {
        // Define a variable to hold the Places API key.
        // secret에서 정의한 API KEY가 안불러와져서 그냥 때려 박았습니다
        val apiKey = "AIzaSyCdjyOxbTIwn_f13N9XhrLnKtFJ2kpsG7M"

        // Log an error if apiKey is not set.
        if (apiKey.isEmpty() || apiKey == "DEFAULT_API_KEY") {
            Log.e("Places test", "No api key")
            finish()
            return
        }

        // Initialize the SDK
        // Place SDK를 초기화 하는 메소드
        Places.initialize(applicationContext, apiKey)
        val placesClient = Places.createClient(this)
        viewModel.setPlacesAPIClient(placesClient)

        // Create a new PlacesClient instance
        // placeField 안에 뽑아넣을 변수를 설정함. 해당 예시의 경우 ID, NAME(음식점 이름), ADDRESS(주소), RATING(평점), TYPES(장소 유형)만 뽑아냄.
        // 해당 설정은 언제든 바꿀 수 있음. Arrays.asList 안에 무엇을 설정하느냐에 따라 바뀜.
//        var placeField : List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.RATING, Place.Field.TYPES)
//        // 초기화한 Place SDK 클라이언트를 변수에 연결지음, createClient 안에는 context가 들어감, Activity일경우 this, Fragment이면 this.context
//        val placesClient = Places.createClient(this)
//        // includeType -> 뽑아낼 장소 유형 리스트 담기. 예시의 경우 음식점, 카페, 빵집을 넣음.
//        var includeType = listOf("restaurant", "cafe", "bakery")
//        // LatLng 클래스 안에 좌표를 넣은 것. 테스트를 위해 하드코딩
//        var latLng = LatLng(37.5, 126.9)
//        // Request 변수 안에 넣어 줄 파라미터 생성. CircularBounds가 필요함
//        var circle = CircularBounds.newInstance(latLng, 8000.0)
//        // 이따 searchNearby 메소드에 넣어줄 SearchNearByRequest 오브젝트 만들어주기
//        val searchNearbyRequest = SearchNearbyRequest.builder(circle, placeField) // 위에 var, val로 선언한 변수 참고해주세요
//            .setIncludedTypes(includeType) // 이것도 includeType 확인해주세요
//            .setMaxResultCount(10) // 말 그대로 결괏값의 max를 Int로 지정
//            .build()
//        placesClient.searchNearby(searchNearbyRequest) // 바로 위에 선언한 SearchNearByRequest를 해당 메소드에 꽂아줌
//            .addOnSuccessListener { response ->
//                var place: List<Place>
//                place = response.places // place에 해당 결괏값 대입
//                Log.d("데이터 확인", place.toString())
//            }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(0.0, 0.0))
                .title("Marker")
        )
    }
}