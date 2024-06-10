package com.android.hanple.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.activity.OnBackPressedCallback

import androidx.core.content.ContextCompat

import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchTimeBinding
import com.android.hanple.databinding.FragmentSearchTransportationBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory

class SearchTransportationFragment : Fragment() {
    private var _binding : FragmentSearchTransportationBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private lateinit var callback : OnBackPressedCallback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTransportationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getScore()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val searchTimeFragment = SearchTimeFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchTimeFragment)
                transaction.commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchTransportationFragment, callback)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView(){
        binding.apply {
            cdSearchTransportationCar.setOnClickListener {
                viewModel.getParkingData()
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            btnSearchTransportationCar.setOnClickListener {
                viewModel.getParkingData()
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            cdSearchTransportationPublic.setOnClickListener {
                viewModel.usePublic()
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            btnSearchTransportationPublic.setOnClickListener {
                viewModel.usePublic()
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            }
            tvSearchTransportationNext.setOnClickListener {
                val searchPeopleFragment = SearchPeopleFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchPeopleFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    private fun getScore(){
        viewModel.getDustScore()
        viewModel.getWeatherScore()
    }
}