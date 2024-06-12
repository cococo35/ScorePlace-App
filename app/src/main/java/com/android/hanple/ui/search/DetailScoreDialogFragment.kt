package com.android.hanple.ui.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentDetailScoreDialogBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory


class DetailScoreDialogFragment : DialogFragment() {

    private var _binding : FragmentDetailScoreDialogBinding? = null
    private val binding get() = _binding!!

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
        initView()
    }

    @SuppressLint("ResourceAsColor")
    private fun initView(){
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        viewModel.readDustScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreDust.text = "미세먼지 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
            if (it >= 8) {
                binding.tvDetailDialogScoreDust.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreDustDescription.text = "미세먼지 농도가 '좋음' 수준이에요"
            } else if (it >= 4) {
                binding.tvDetailDialogScoreDust.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreDustDescription.text = "미세먼지 농도가 '보통' 수준이에요"
            } else {
                binding.tvDetailDialogScoreDust.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreDustDescription.text = "미세먼지 농도가 '나쁨' 수준이에요"
            }
        }
        viewModel.readWeatherScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreWeather.text = "날씨 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
            if (it >= 24) {
                binding.tvDetailDialogScoreWeather.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreWeatherDescription.text = "맑은 날씨에요"
            } else if (it >= 12) {
                binding.tvDetailDialogScoreWeather.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreWeatherDescription.text = "전반적으로 날씨가 흐려요"
            } else {
                binding.tvDetailDialogScoreWeather.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreWeatherDescription.text = "비가 올 수 있어요"
            }
        }
        viewModel.readCongestScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreCongestion.text = "여행 성향 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
            if (it >= 24) {
                binding.tvDetailDialogScoreCongestion.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreCongestionDescription.text = "내가 원하던 장소에요"
            } else if (it >= 12) {
                binding.tvDetailDialogScoreCongestion.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreCongestionDescription.text = "무난한 장소에요"
            } else {
                binding.tvDetailDialogScoreCongestion.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreCongestionDescription.text = "원하던 장소가 아니에요"
            }
        }
        viewModel.readCostScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreCost.text = "비용 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
            if (it >= 8) {
                binding.tvDetailDialogScoreCost.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreCostDescription.text = "마음껏 쓸 수 있어요"
            } else if (it >= 4) {
                binding.tvDetailDialogScoreCost.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreCostDescription.text = "원하는 것들을 할 수 있어요"
            } else {
                binding.tvDetailDialogScoreCost.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreCostDescription.text = "할 수 있는게 많지 않아요"
            }
        }
        viewModel.readTransportScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreTraffic.text = "교통 점수 : ${"%.0f".format(it.toDouble() / 20 * 100)}점"
            if (it >= 15) {
                binding.tvDetailDialogScoreTraffic.setTextColor(ContextCompat.getColor(requireContext(),R.color.green))
                binding.tvDetailDialogScoreTrafficDescription.text = "막힘없이 이동할 수 있어요"
            } else if (it >= 8) {
                binding.tvDetailDialogScoreTraffic.setTextColor(ContextCompat.getColor(requireContext(),R.color.orange))
                binding.tvDetailDialogScoreTrafficDescription.text = "일정에 맞게 이동할 수 있어요"
            } else {
                binding.tvDetailDialogScoreTraffic.setTextColor(ContextCompat.getColor(requireContext(),R.color.red))
                binding.tvDetailDialogScoreTrafficDescription.text = "이동에 시간이 오래 걸려요"
            }
        }
    }
}