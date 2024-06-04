package com.android.hanple.ui.search

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.hanple.adapter.OnDataClick
import com.android.hanple.adapter.PlaceScoreCategoryAdapter
import com.android.hanple.databinding.FragmentScoreBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import com.google.android.libraries.places.api.model.Place
import java.time.LocalDateTime

class ScoreFragment : Fragment() {
    private val binding by lazy {
        FragmentScoreBinding.inflate(layoutInflater)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView(){
        val spinnerList = listOf(
            "음식점", "카페", "주차장", "영화관", "쇼핑몰", "지하철역", "버스정류장", "공원"
        )
        val typeList = listOf(
            "restaurant", "cafe", "parking", "movie_theater", "shopping_mall", "subway_station", "bus_station", "park"
        )
        viewModel.getNearByPlace(typeList[0])
        binding.recyclerviewScoreCategory.adapter = PlaceScoreCategoryAdapter(object : OnDataClick{
            override fun onItemClick(data: Place) {
                data.name?.let { Log.d("event", it.toString()) }
            }
        })
        binding.recyclerviewScoreCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.nearByPlace.observe(viewLifecycleOwner){
            (binding.recyclerviewScoreCategory.adapter as PlaceScoreCategoryAdapter).submitList(it)
        }
        val spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, spinnerList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spScoreCategory.adapter = spinnerAdapter
        val itemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getNearByPlace(typeList[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        binding.spScoreCategory.onItemSelectedListener = itemSelectedListener
        val localDateTime: LocalDateTime = LocalDateTime.now()
        viewModel.selectPlace.observe(viewLifecycleOwner){
            binding.tvScoreTitle.text = "${it.name}, ${localDateTime.toString()} "
        }
        viewModel.totalScore.observe(viewLifecycleOwner){
            binding.tvScoreScore.text = "${it.toString()}점"
        }
    }
}