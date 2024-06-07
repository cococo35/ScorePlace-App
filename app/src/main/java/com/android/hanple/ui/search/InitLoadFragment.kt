package com.android.hanple.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.android.hanple.R
import com.android.hanple.databinding.FragmentInitLoadBinding
import com.android.hanple.databinding.FragmentSearchBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class InitLoadFragment : Fragment() {
    private var _binding : FragmentInitLoadBinding? = null
    private val binding get() = _binding!!
    private var time: Int = 0
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

    private fun initView(){
        lifecycleScope.launch {
            whenStarted{
                while (true){
                    delay(1000)
                    time += 1
                    if(time == 2){
                        time = 0
                        val searchFragment = SearchFragment()
                        val transaction = parentFragmentManager.beginTransaction()
                        transaction.replace(R.id.fr_main, searchFragment)
                        transaction.commit()
                        break
                    }
                }
            }
        }
    }
}