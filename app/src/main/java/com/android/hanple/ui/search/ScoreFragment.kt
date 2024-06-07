package com.android.hanple.ui.search

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.hanple.R
import com.android.hanple.adapter.CategoryPlace
import com.android.hanple.adapter.OnDataClick
import com.android.hanple.adapter.PlaceScoreCategoryAdapter
import com.android.hanple.databinding.FragmentScoreBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import java.time.LocalDateTime

class ScoreFragment : Fragment() {
    private val binding by lazy {
        FragmentScoreBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private lateinit var callback : OnBackPressedCallback
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
        initDetailDialog()
        onBackPressButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                clearBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    @SuppressLint("SetTextI18n")
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
        viewModel.selectPlace?.observe(viewLifecycleOwner){
            binding.tvScoreTitle.text =
                "${it?.name}, " +
                        localDateTime.toString().substring(5, 7) +
                        "월 " +
                        localDateTime.toString().substring(8, 10) +
                        "일 " +
                        localDateTime.toString().substring(11, 16)
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

    @SuppressLint("SetTextI18n")
    private fun initDetailDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_detail_score_dialog)
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        val dialogCloseButton = dialog.findViewById<TextView>(R.id.tv_detail_dialog_dismiss)
        val scoreCost = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_cost)
        val scoreDust = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_dust)
        val scoreTraffic = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_traffic)
        val scoreCongestion = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_congestion)
        val scoreWeather = dialog.findViewById<TextView>(R.id.tv_detail_dialog_score_weather)
        viewModel.readCostScore.observe(viewLifecycleOwner){
            scoreCost.text = "비용 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
        }
        viewModel.readDustScore.observe(viewLifecycleOwner){
            scoreDust.text = "미세먼지 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
        }
        viewModel.readTransportScore.observe(viewLifecycleOwner){
            scoreTraffic.text = "교통 점수 : ${"%.0f".format(it.toDouble() / 20 * 100)}점"
        }
        viewModel.readCongestScore.observe(viewLifecycleOwner){
            scoreCongestion.text = "여행 성향 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
        }
        viewModel.readWeatherScore.observe(viewLifecycleOwner){
            scoreWeather.text = "날씨 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
        }
        dialogCloseButton.setOnClickListener {
            dialog.dismiss()
        }
        binding.tvScoreDetail.setOnClickListener {
            dialog.show()
        }
    }

    private fun onBackPressButton(){
        binding.ivScoreBack.setOnClickListener {
            clearBackStack()
        }
    }
    private fun clearBackStack() {
        AlertDialog.Builder(requireContext())
            .setMessage("처음 화면으로 돌아가시겠습니까?")
            .setPositiveButton("YES", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val fragmentManager: FragmentManager = parentFragmentManager
                    val searchFragment = SearchFragment()
                    viewModel.resetPlaceData()
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.add(R.id.fr_main, searchFragment)
                    transaction.commit()
                }
            })
            .setNegativeButton("No", object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                }
            })
            .create()
            .show()
    }
}