package com.android.hanple.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.hanple.R
import com.android.hanple.databinding.FragmentListViewBinding

class ListViewFragment : Fragment() {

    private var _binding: FragmentListViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TextView 클릭 리스너 설정
        binding.tvListViewMap.setOnClickListener {
            val address = binding.tvListViewMap.text.toString()
            val mapFragment = MapFragment.newInstance(address)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fr_main, mapFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
