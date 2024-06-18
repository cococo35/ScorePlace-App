package com.android.hanple.ui.search



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
import com.android.hanple.databinding.ActivityMainBinding
import com.android.hanple.room.RecommendDataBase
import com.android.hanple.room.RecommendPlace
import com.android.hanple.room.recommendPlaceGoogleID
import com.android.hanple.ui.archive.ArchiveActivity
import com.android.hanple.ui.archive.ListViewFragment
import com.android.hanple.ui.settings.SettingsActivity
import com.android.hanple.ui.settings.SettingsFragment
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
        sendLogInInfoToSettings()
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

    private fun sendLogInInfoToSettings() {
        val nickname = intent.getStringExtra("nickname")
        if (nickname != null) {
            val bundle = Bundle()
            bundle.putString("nickname", nickname)
            SettingsFragment().arguments = bundle
        }
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
                  val intent = Intent(this, ArchiveActivity::class.java)
                    startActivity(intent)
            }
        }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }



    //API키 수정은 여기서만
    private fun initPlaceSDK() {
        // Define a variable to hold the Places API key.
        //secret에서 정의한 API KEY가 안불러와져서 그냥 때려 박았습니다
        val apiKey = "AIzaSyA1xkP-fgyKPmglkt5Dh3Nn_xr4MREvw0k"

        // Log an error if apiKey is not set.
        if (apiKey.isEmpty()) {
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
            val firstJob = launch {
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
            firstJob.join()
            val secondJob = launch {
                val list = randomNumberPlace()
                Log.d("데이터 출력", "")
                viewModel.getRecommendPlace(list, recommendDAO)
            }
            secondJob.join()
            val searchFragment = SearchFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fr_main, searchFragment)
            transaction.commit()
            Log.d("프래그먼트 전환", "")
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
