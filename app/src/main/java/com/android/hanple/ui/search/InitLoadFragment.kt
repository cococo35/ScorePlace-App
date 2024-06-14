package com.android.hanple.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.android.hanple.R
import com.android.hanple.Room.RecommendDataBase
import com.android.hanple.Room.RecommendPlace
import com.android.hanple.Room.recommendPlaceGoogleID
import com.android.hanple.databinding.FragmentInitLoadBinding
import com.android.hanple.databinding.FragmentSearchBinding
import com.android.hanple.ui.MainActivity
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random


class InitLoadFragment : Fragment() {
    private var _binding : FragmentInitLoadBinding? = null
    private val binding get() = _binding!!
    private var time: Int = 0
    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    private val recommendDAO by lazy {
        RecommendDataBase.getMyRecommendPlaceDataBase(requireContext()).getMyRecommendPlaceDAO()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitLoadBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@InitLoadFragment, callback)
        (activity as MainActivity).hideDrawerView()
    }

    private fun initView(){
        lifecycleScope.launch {
            var value = 0
            whenStarted{
                while(value != 100){
                    delay(25)
                    value += 1
                    binding.initLoadProgessbar.progress = value
                    binding.initLoadProgressValue.text = "$value%"
                }
                delay(500)
                val searchFragment = SearchFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, searchFragment)
                transaction.commit()
            }
        }
    }



}