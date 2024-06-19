package com.android.hanple.ui.archive

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView(){
        val address = intent.getStringExtra("address") ?: ""
        val score = intent.getDoubleExtra("score", 0.0)
        val placeList = mutableListOf<CategoryPlace>().apply {
            add(CategoryPlace(address, score, null, null, null, null, null, null, true, null))
        }

        supportFragmentManager.commit {
            replace(R.id.fr_archive_map, MapFragment())
            addToBackStack(null)
        }

        val bottomViewBehavior = BottomSheetBehavior.from(binding.recyclerMapList)
        bottomViewBehavior.isDraggable = true
        bottomViewBehavior.peekHeight = 120
        bottomViewBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // BottomSheet가 확장되었을 때 수행할 작업
                        binding.recyclerMapList.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        // BottomSheet가 축소되었을 때 수행할 작업
                        binding.recyclerMapList.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // BottomSheet가 숨겨졌을 때 수행할 작업
                        binding.recyclerMapList.visibility = View.GONE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // 슬라이드 중일 때 수행할 작업
                binding.recyclerMapList.alpha = slideOffset
            }
        })

        binding.recyclerMapList.adapter = PlaceStorageListAdapter(this, { place ->
            // 아이템 클릭 시 처리할 로직
        }, placeList)
        binding.recyclerMapList.layoutManager = LinearLayoutManager(this)
    }
}
