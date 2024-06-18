package com.android.hanple.ui.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.hanple.databinding.ActivityLocalSignUpBinding

class LocalSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLocalSignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}