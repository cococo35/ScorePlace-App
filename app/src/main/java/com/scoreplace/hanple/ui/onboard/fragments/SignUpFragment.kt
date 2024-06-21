package com.scoreplace.hanple.ui.onboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.FragmentPrivacyPolicyBinding
import com.scoreplace.hanple.databinding.FragmentSignUpBinding
import com.scoreplace.hanple.ui.onboard.OnboardViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        //ViewModel -> View 정보 받아오기
        viewModel.scrolled.observe(viewLifecycleOwner) { isChecked -> //ViewModel에서
            binding.cbPrivacyPolicy.isChecked = isChecked
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPrivacyPolicy.setOnClickListener{
            findNavController().navigate(R.id.action_signUpFragment_to_privacyPolicyFragment)
        }
        binding.btnSignUp.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}