package com.scoreplace.hanple.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.FragmentDetailScoreDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailScoreDialogFragment : DialogFragment() {

    private var _binding : FragmentDetailScoreDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    private fun initView(){
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        viewModel.readDustScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreDust.text = "미세먼지 점수 ${"%.0f".format(it.toDouble() / 10 * 100)}점"
            if (it >= 8) {
                binding.tvDetailDialogScoreDust.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreDustDescription.text = resources.getStringArray(R.array.score_detail_dust_description)[0]
            } else if (it >= 4) {
                binding.tvDetailDialogScoreDust.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreDustDescription.text = resources.getStringArray(R.array.score_detail_dust_description)[1]
            } else {
                binding.tvDetailDialogScoreDust.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreDustDescription.text = resources.getStringArray(R.array.score_detail_dust_description)[2]
            }
        }
        viewModel.readWeatherScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreWeather.text = "날씨 점수 ${"%.0f".format(it.toDouble() / 30 * 100)}점"
            if (it >= 24) {
                binding.tvDetailDialogScoreWeather.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreWeatherDescription.text = resources.getStringArray(R.array.score_detail_weather_description)[0]
            } else if (it >= 12) {
                binding.tvDetailDialogScoreWeather.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreWeatherDescription.text = resources.getStringArray(R.array.score_detail_weather_description)[1]
            } else {
                binding.tvDetailDialogScoreWeather.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreWeatherDescription.text = resources.getStringArray(R.array.score_detail_weather_description)[2]
            }
        }
        viewModel.readCongestScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreCongestion.text = "여행 성향 점수 ${"%.0f".format(it.toDouble() / 30 * 100)}점"
            if (it >= 24) {
                binding.tvDetailDialogScoreCongestion.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreCongestionDescription.text = resources.getStringArray(R.array.score_detail_congestion_description)[0]
            } else if (it >= 12) {
                binding.tvDetailDialogScoreCongestion.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreCongestionDescription.text = resources.getStringArray(R.array.score_detail_congestion_description)[1]
            } else {
                binding.tvDetailDialogScoreCongestion.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreCongestionDescription.text = resources.getStringArray(R.array.score_detail_congestion_description)[2]
            }
        }
        viewModel.readCostScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreCost.text = "비용 점수 ${"%.0f".format(it.toDouble() / 10 * 100)}점"
            if (it >= 8) {
                binding.tvDetailDialogScoreCost.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreCostDescription.text = resources.getStringArray(R.array.score_detail_cost_description)[0]
            } else if (it >= 4) {
                binding.tvDetailDialogScoreCost.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreCostDescription.text = resources.getStringArray(R.array.score_detail_cost_description)[1]
            } else {
                binding.tvDetailDialogScoreCost.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreCostDescription.text = resources.getStringArray(R.array.score_detail_cost_description)[2]
            }
        }
        viewModel.readTransportScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreTraffic.text = "교통 점수 ${"%.0f".format(it.toDouble() / 20 * 100)}점"
            if (it >= 15) {
                binding.tvDetailDialogScoreTraffic.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreTrafficDescription.text = resources.getStringArray(R.array.score_detail_traffic_description)[0]
            } else if (it >= 8) {
                binding.tvDetailDialogScoreTraffic.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreTrafficDescription.text = resources.getStringArray(R.array.score_detail_traffic_description)[1]
            } else {
                binding.tvDetailDialogScoreTraffic.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreTrafficDescription.text = resources.getStringArray(R.array.score_detail_traffic_description)[2]
            }
        }
    }
}