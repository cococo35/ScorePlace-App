package com.android.hanple.ui.archive

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
        val dummmyList = mutableListOf<CategoryPlace>()
        for(i in 0..10){
            dummmyList.add(CategoryPlace("주소 ${i+1}", 4.5, null, null, null, null, null,null,true, null))
        }
        supportFragmentManager.commit {
            replace(R.id.fr_archive_map, MapFragment())
            addToBackStack(null)
        }
        val bottomViewBehavior = BottomSheetBehavior.from(binding.recyclerMapList)
        bottomViewBehavior.isDraggable = true
        bottomViewBehavior.peekHeight = 120
        bottomViewBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //슬라이드 상태 변화에 활용할 메소드를 넣을 때 넣기
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //슬라이드 시 활용할 메소드가 있으면 넣기
            }
        })
        //현재는 더미 리스트를 어댑터에 꽂아 넣었는데 이것도 바꾸기
        binding.recyclerMapList.adapter = PlaceStorageListAdapter(this, { place ->
        }, dummmyList)
        binding.recyclerMapList.layoutManager = LinearLayoutManager(this)
    }

}