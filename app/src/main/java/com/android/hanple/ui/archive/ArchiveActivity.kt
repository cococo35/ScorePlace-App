package com.android.hanple.ui.archive

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.hanple.R
import com.android.hanple.adapter.PlaceStorageListAdapter
import com.android.hanple.data.CategoryPlace
import com.android.hanple.databinding.ActivityArchiveBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class ArchiveActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityArchiveBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("favorite_places", Context.MODE_PRIVATE)
        initView()
    }

    private fun initView() {
        val places = loadPlacesFromPreferences()
        supportFragmentManager.commit {
            replace(R.id.fr_archive_map, MapFragment())
            addToBackStack(null)
        }
        val bottomViewBehavior = BottomSheetBehavior.from(binding.recyclerMapList)
        bottomViewBehavior.isDraggable = true
        bottomViewBehavior.peekHeight = 120
        bottomViewBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // 슬라이드 상태 변화에 활용할 메소드를 넣을 때 넣기
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드 시 활용할 메소드가 있으면 넣기
            }
        })

        val adapter = PlaceStorageListAdapter(this, { place ->
            // 아이템 클릭 시 처리할 로직
        }, places)

        adapter.onAddressClick = { place ->
            val mapFragment = MapFragment.newInstance(setOf(place))
            supportFragmentManager.commit {
                replace(R.id.fr_archive_map, mapFragment)
                addToBackStack(null)
            }
        }

        binding.recyclerMapList.adapter = adapter
        binding.recyclerMapList.layoutManager = LinearLayoutManager(this)
    }

    private fun loadPlacesFromPreferences(): MutableList<CategoryPlace> {
        val placeCount = sharedPreferences.getInt("place_count", 0)
        val places = mutableListOf<CategoryPlace>()
        for (i in 0 until placeCount) {
            val address = sharedPreferences.getString("address_$i", null)
            val score = sharedPreferences.getFloat("score_$i", 0f)
            if (address != null) {
                places.add(CategoryPlace(address, score.toDouble(), null, null, null, null, null, null, true, null))
            }
        }
        return places
    }
}
