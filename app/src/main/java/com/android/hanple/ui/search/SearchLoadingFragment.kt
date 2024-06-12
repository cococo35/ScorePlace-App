package com.android.hanple.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchLoadingBinding
import com.android.hanple.viewmodel.SearchViewModel
import com.android.hanple.viewmodel.SearchViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchLoadingFragment : Fragment() {
    private var _binding : FragmentSearchLoadingBinding? = null
    private val binding get() = _binding!!
    private var time: Int = 0
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchLoadingBinding.inflate(layoutInflater)
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

    //로딩창에서 기기로 뒤로가기 누르기 막기
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchLoadingFragment, callback)
    }
    private fun initView(){
        lifecycleScope.launch {
            whenStarted{
                viewModel.getToTalScore()
                while (true){
                    delay(1000)
                    time += 1
                    if(time == 2){
                        time = 0
                        val scoreFragment = ScoreFragment()
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.fr_main, scoreFragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                        break
                    }
                }
            }
        }
    }
}