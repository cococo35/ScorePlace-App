package com.android.hanple.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.hanple.databinding.ActivityLoginBinding

import com.android.hanple.databinding.ActivitySignUpBinding

class LogInctivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val etId = binding.etId
        val etPassword = binding.etPassword
    }
}










    }

        }
    }
}