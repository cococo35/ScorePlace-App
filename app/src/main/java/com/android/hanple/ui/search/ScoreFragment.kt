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
import com.android.hanple.R
import com.android.hanple.adapter.CategoryPlace
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
        getScoreDescription()
        getWeatherDescription()
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
            override fun onItemClick(data: CategoryPlace) {
                data.name?.let { Log.d("event", it.toString()) }
            }
        })
        binding.recyclerviewScoreCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.nearByPlace.observe(viewLifecycleOwner){
            if(it.size == 0){
                binding.recyclerviewScoreCategory.visibility = View.GONE
                binding.tvNotFound.visibility = View.VISIBLE
            }
            else {
                binding.recyclerviewScoreCategory.visibility = View.VISIBLE
                binding.tvNotFound.visibility = View.GONE
                (binding.recyclerviewScoreCategory.adapter as PlaceScoreCategoryAdapter).submitList(it)
            }
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
    private fun getScoreDescription(){
        viewModel.totalScore.observe(viewLifecycleOwner){
            if(it < 50){
                binding.tvScoreDescription.text = "해당 장소를 추천하지 않아요."
            }
            else if(it >= 50 && it < 75){
                binding.tvScoreDescription.text = "놀러 가기 적당해요~"
            }
            else {
                binding.tvScoreDescription.text = "매우 추천합니다. 꼭 다녀오세요!"
            }
        }
    }
    private fun getWeatherDescription(){
        viewModel.readWeatherDescription.observe(viewLifecycleOwner){
            if(it.contains("Rain")){
                binding.tvScoreWeatherDescription.text = "비가 올 수 있어요"
                binding.tvScoreWeatherDescription2.text = "우산을 준비하세요"
                binding.ivScoreWeather.setBackgroundResource(R.drawable.ic_weather_rain)
            }
            else if(it.contains("Rain") == false && it.count { it.contains("Clouds") } >= 3){
                binding.tvScoreWeatherDescription.text = "전반적으로 날씨가 흐려요"
                binding.tvScoreWeatherDescription2.text = ""
                binding.ivScoreWeather.setBackgroundResource(R.drawable.iv_weather_cloud)
            }
            else {
                binding.tvScoreWeatherDescription.text = "맑은 날씨에요"
                binding.tvScoreWeatherDescription2.text = ""
                binding.ivScoreWeather.setBackgroundResource(R.drawable.iv_weather_sun)
            }
        }
    }
}