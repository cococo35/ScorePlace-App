package com.android.hanple.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.activity.OnBackPressedCallback

import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import androidx.lifecycle.ViewModelProvider
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchTransportationBinding

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
        (activity as MainActivity).hideDrawerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val mainDrawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
                if (mainDrawer.isDrawerOpen(GravityCompat.START)) {
                    mainDrawer.closeDrawer(GravityCompat.START)
                } else {
                    val searchTimeFragment = SearchTimeFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.to_left, R.anim.from_left)
                    transaction.replace(R.id.fr_main, searchTimeFragment)
                    transaction.commit()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchTransportationFragment, callback)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView(){
        var isSelected = false
        binding.apply {
            cdSearchTransportationCar.setOnClickListener {
                viewModel.getParkingData()
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                isSelected = true
            }
            btnSearchTransportationCar.setOnClickListener {
                viewModel.getParkingData()
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                isSelected = true
            }
            cdSearchTransportationPublic.setOnClickListener {
                viewModel.usePublic()
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                isSelected = true
            }
            btnSearchTransportationPublic.setOnClickListener {
                viewModel.usePublic()
                binding.cdSearchTransportationPublic.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.neomint))
                binding.cdSearchTransportationCar.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                isSelected = true
            }
            tvSearchTransportationNext.setOnClickListener {
                if (isSelected) {
                    val searchPeopleFragment = SearchPeopleFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
                    transaction.replace(R.id.fr_main, searchPeopleFragment)
                    transaction.commit()
                } else {
                    Toast.makeText(requireContext(), "이동 수단을 선택해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            tvSearchTransportationSkip.setOnClickListener {
                viewModel.usePublic()
                val searchPeopleFragment = SearchPeopleFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
                transaction.replace(R.id.fr_main, searchPeopleFragment)
                transaction.commit()
            }
        }
    }

    private fun getScore(){
        viewModel.getDustScore()
        viewModel.getWeatherScore()
    }
}