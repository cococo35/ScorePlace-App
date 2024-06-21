package com.scoreplace.hanple.ui.onboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.FragmentPrivacyPolicyBinding

class LogInFragment : Fragment() {

    private var _binding: FragmentPrivacyPolicyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrivacyPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}