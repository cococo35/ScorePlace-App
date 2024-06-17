package com.android.hanple.ui.archive

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.commit
import com.android.hanple.R
import com.android.hanple.databinding.ActivityArchiveBinding

class ArchiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArchiveBinding

    private lateinit var callback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBackPressFeature()
        initFragment()
    }

    private fun setBackPressFeature() {
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initFragment() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, ListViewFragment())
            addToBackStack(null)
        }
    }
}
