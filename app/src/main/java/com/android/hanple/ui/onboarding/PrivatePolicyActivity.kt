package com.android.hanple.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.hanple.databinding.ActivityPrivatePolicyBinding

class PrivatePolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivatePolicyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivatePolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDismiss.setOnClickListener {
            finish()
        }
    }
}