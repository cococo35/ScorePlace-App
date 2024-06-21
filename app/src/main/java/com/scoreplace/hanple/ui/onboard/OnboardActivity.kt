package com.scoreplace.hanple.ui.onboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scoreplace.hanple.R
import com.scoreplace.hanple.databinding.ActivityOnboardBinding
import com.scoreplace.hanple.ui.onboard.fragments.LogInFragment

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fcv_parent, LogInFragment())
        fragmentTransaction.commit()
    }
}