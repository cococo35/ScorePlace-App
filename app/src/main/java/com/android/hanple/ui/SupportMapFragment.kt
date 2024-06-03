package com.android.hanple.ui

import androidx.fragment.app.Fragment
import com.android.hanple.R
import com.google.android.gms.maps.OnMapReadyCallback

class SupportMapFragment : Fragment(R.layout.fragment_support_map), OnMapReadyCallback {

    val mapFragment = SupportMapFragment.newInstance() {
    supportFragmentManager
    .beginTransaction()
    .add(R.id.my_container, mapFragment)
    .commit()
}
