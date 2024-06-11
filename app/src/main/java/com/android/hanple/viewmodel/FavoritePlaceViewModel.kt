package com.android.hanple.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.hanple.data.CategoryPlace

class FavoritePlaceViewModel : ViewModel() {
    private val _favoritePlace = MutableLiveData<MutableList<CategoryPlace>>(mutableListOf())
    val favoritePlace: LiveData<MutableList<CategoryPlace>> get() = _favoritePlace

    fun addPlace(place: CategoryPlace) {
        val updatedList = _favoritePlace.value ?: mutableListOf()
        if (!updatedList.any { it.address == place.address && it.score == place.score }) {
            updatedList.add(place)
            _favoritePlace.value = updatedList
        }
    }
}
