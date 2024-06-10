package com.android.hanple.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
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

    private fun initView(){
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT)
        viewModel.readDustScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreDust.text = "미세먼지 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
        }
        viewModel.readWeatherScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreWeather.text = "날씨 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
        }
        viewModel.readCongestScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreCongestion.text = "여행 성향 점수 : ${"%.0f".format(it.toDouble() / 30 * 100)}점"
        }
        viewModel.readCostScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreCost.text = "비용 점수 : ${"%.0f".format(it.toDouble() / 10 * 100)}점"
        }
        viewModel.readTransportScore.observe(viewLifecycleOwner){
            binding.tvDetailDialogScoreTraffic.text = "교통 점수 : ${"%.0f".format(it.toDouble() / 20 * 100)}점"
        }
    }
}