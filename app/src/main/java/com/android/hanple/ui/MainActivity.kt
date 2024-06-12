package com.android.hanple.ui



import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.Room.RecommendDataBase
import com.android.hanple.Room.RecommendPlace
import com.android.hanple.Room.recommendPlaceGoogleID
import com.android.hanple.databinding.ActivityMainBinding
import com.android.hanple.ui.search.InitLoadFragment
import com.android.hanple.ui.settings.SettingsActivity
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import com.google.android.libraries.places.api.Places
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy {
        ViewModelProvider(this, SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private val recommendDAO by lazy {
        RecommendDataBase.getMyRecommendPlaceDataBase(this).getMyRecommendPlaceDAO()
    }
    private lateinit var callback: OnBackPressedCallback
    private var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insertRoomData()
        initFragment()
        setNavigation()
        initPlaceSDK()
        setBackPressFeature()
//        deleteItem()
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
            replace(R.id.fr_main, InitLoadFragment())
            setReorderingAllowed(true)
        }
    }

    private fun setNavigation() {

        val navView: NavigationView = binding.navView

        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_account -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }

                R.id.nav_view -> {
                    // 액티비티 이동
                }

                R.id.nav_bookmark -> {
                    val listViewFragment = ListViewFragment()
                    supportFragmentManager.commit {
                        replace(R.id.fr_main, listViewFragment)
                        addToBackStack(null)
                }
            }
        }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun initPlaceSDK() {
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

    private fun setBackPressFeature() {
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
        onBackPressedDispatcher.addCallback(this, callback)
    }



    private fun deleteItem(){
        Log.d("데이터 삭제 성공", "")
        runBlocking {
            recommendDAO.deleteItem()
        }
    }


    private fun insertRoomData() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                Log.d("데이터 삽입", "")
                for (i in 0..recommendPlaceGoogleID.size - 1) {
                    recommendDAO.insertRecommendPlace(
                        RecommendPlace(
                            i + 1,
                            recommendPlaceGoogleID[i]
                        )
                    )
                }
            }
            job.join()
            delay(1000)
            val list = randomNumberPlace()
            Log.d("데이터 출력", "")
            viewModel.getRecommendPlace(list, recommendDAO)
        }
    }
    private fun randomNumberPlace(): List<Int> {
        val edge = recommendPlaceGoogleID.size
        val list = mutableListOf<Int>()
        var number: Int = 0
        while (list.size < 5) {
            number = Random.nextInt(edge) + 1
            if (list.contains(number))
                continue
            else
                list.add(number)
        }
        return list
    }

    fun visibleDrawerView(){
        binding.btnMainMenu.visibility = View.VISIBLE
    }

    fun hideDrawerView(){
        binding.btnMainMenu.visibility = View.GONE
    }

}
