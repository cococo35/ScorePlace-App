package com.android.hanple.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
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


    @RequiresApi(Build.VERSION_CODES.O)
    private fun initView(){
        val localDateTime: LocalDateTime = LocalDateTime.now()
        viewModel.selectPlace.observe(viewLifecycleOwner){
            binding.tvScoreTitle.text = "${it.name}, ${localDateTime.toString()} "
        }
        viewModel.totalScore.observe(viewLifecycleOwner){
            binding.tvScoreScore.text = "${it.toString()}점"
        }
    }
}