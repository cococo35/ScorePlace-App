package com.scoreplace.hanple.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.FragmentSearchPeopleBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchPeopleFragment : Fragment() {


    private var _binding : FragmentSearchPeopleBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy{
        ViewModelProvider(requireActivity())[SearchViewModel::class.java]
    }
    private lateinit var callback : OnBackPressedCallback
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchPeopleBinding.inflate(layoutInflater)
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
                    val searchTransportationFragment = SearchTransportationFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.to_left, R.anim.from_left)
                    transaction.replace(R.id.fr_main, searchTransportationFragment)
                    transaction.commit()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this@SearchPeopleFragment, callback)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun initView(){
        var preferType = 0

        val typeList = listOf(
            binding.btnSearchPeopleButton1,
            binding.btnSearchPeopleButton2,
            binding.btnSearchPeopleButton3,
            binding.btnSearchPeopleButton4,
            binding.btnSearchPeopleButton5,
        )

        for(i in typeList.indices){
            typeList[i].apply {
                this.setOnClickListener {
                    for(j in 0..<typeList.size){
                        typeList[j].setImageResource(R.drawable.ic_check_blank_20dp)
                    }
                    this.setImageResource(R.drawable.ic_check_fill_20dp)
                    preferType = i+1
                }
            }
        }

        binding.apply {
            tvSearchPeopleNext.setOnClickListener {

                if (preferType == 0) {
                    Toast.makeText(requireContext(), getString(R.string.search_enter_congestion), Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.getCongestionScore(preferType)
                    val searchCostFragment = SearchCostFragment()
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
                    transaction.replace(R.id.fr_main, searchCostFragment)
                    transaction.commit()
                }
            }

            tvSearchPeopleSkip.setOnClickListener {
                viewModel.getCongestionScore(3)
                val searchCostFragment = SearchCostFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.to_right, R.anim.from_right)
                transaction.replace(R.id.fr_main, searchCostFragment)
                transaction.commit()
            }
        }
    }
    private fun getScore(){
        viewModel.getTransportScore()
    }
}