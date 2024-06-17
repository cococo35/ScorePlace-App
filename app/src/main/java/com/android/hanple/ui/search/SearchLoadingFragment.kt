package com.android.hanple.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.android.hanple.R
import com.android.hanple.databinding.FragmentSearchLoadingBinding
import com.github.penfeizhou.animation.apng.APNGDrawable
import com.github.penfeizhou.animation.loader.AssetStreamLoader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchLoadingFragment : Fragment() {
    private var _binding : FragmentSearchLoadingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity(), SearchViewModelFactory())[SearchViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchLoadingBinding.inflate(layoutInflater)
        //로딩 이미지
        val imageView: ImageView = binding.initLoadImg
        val assetLoader: AssetStreamLoader = AssetStreamLoader(requireContext(), "animated_writing_hand.png")
        val apngDrawable = APNGDrawable(assetLoader)
        imageView.setImageDrawable(apngDrawable)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        (activity as MainActivity).hideDrawerView()
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
    @SuppressLint("SetTextI18n")
    private fun initView(){
        lifecycleScope.launch {
            var value = 0
            whenStarted{
                viewModel.getToTalScore()
                while(value != 100){
                    delay(25)
                    value += 1
                    binding.scoreLoadProgessbar.progress = value
                    binding.scoreLoadProgressValue.text = "$value%"
                }
                delay(500)
                val scoreFragment = ScoreFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.fr_main, scoreFragment)
                transaction.commit()
            }
        }
    }
}