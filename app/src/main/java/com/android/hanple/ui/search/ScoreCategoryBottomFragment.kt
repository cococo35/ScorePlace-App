package com.android.hanple.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.databinding.FragmentScoreCategoryBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ScoreCategoryBottomFragment : BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentScoreCategoryBottomBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initView()
    }


    // * 만들다보니 xml만 활용하게 되어 이 파일은 사용하지 않습니다!!


//    private fun initView(){
//        val spinnerList = listOf(
//            "음식점", "카페", "주차장", "영화관", "쇼핑몰", "지하철역", "버스정류장", "공원"
//        )
//        binding.bottomScoreCategoryList.adapter = ScoreCategoryListAdapter(spinnerList, object : ScoreCategoryListAdapter.OnItemClickListener {
//            override fun onItemClick(position: Int) {
//                viewModel.getNearByPlace(typeList[position])
//            }
//        })
//        binding.bottomScoreCategoryList.layoutManager = LinearLayoutManager(requireActivity())
//        binding.tvScoreCategoryButton.setOnClickListener {
//            this.dismiss()
//        }
//    }
}