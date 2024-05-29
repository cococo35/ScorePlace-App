package com.android.hanple.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.hanple.Address.AddressRemoteImpl
import com.android.hanple.Dust.DustRemoteImpl
import com.android.hanple.Weather.WeatherRemoteImpl
import com.android.hanple.data.congestion.remote.CongestionRemoteImpl
import com.android.hanple.network.AddressRetrofit
import com.android.hanple.network.CongestionRetrofit
import com.android.hanple.network.DustRetrofit
import com.android.hanple.network.WeatherRetrofit
import kotlinx.coroutines.launch

class SearchViewModel(
    private val addressRemoteImpl: AddressRemoteImpl,
    private val dustRemoteImpl: DustRemoteImpl,
    private val congestionRemoteImpl: CongestionRemoteImpl,
    private val weatherRemoteImpl: WeatherRemoteImpl,
) : ViewModel() {

    private val _startLat = MutableLiveData<String>()
    val startLat: LiveData<String> get() = _startLat
    private val _startLng = MutableLiveData<String>()
    val startLng: LiveData<String> get() = _startLng
    private val _endLat = MutableLiveData<String>()
    val endLat: LiveData<String> get() = _endLat
    private val _endLng = MutableLiveData<String>()
    val endLng: LiveData<String> get() = _endLng

    private val weatherDescription = MutableLiveData<List<String>>()
    private val dustAqi = MutableLiveData<List<String>>()
    private val congestionDescription = MutableLiveData<List<String>>()

    private val _totalScore = MutableLiveData<Int>()
    val totalScore: LiveData<Int> get() = _totalScore

    fun getStartAddress(query: String) {
        viewModelScope.launch {
            runCatching {
                val response =
                    addressRemoteImpl.getAddress("KakaoAK c2e35e66f144e5d544ec6782c56b342b", query)
                if (response != null) {
                    _startLng.postValue(response.documents?.get(0)?.x.toString())
                    _startLat.postValue(response.documents?.get(0)?.y.toString())
                }
            }.onFailure { e ->
                Log.d("출발지 좌표 받기 실패", e.toString())
            }
        }
    }

    fun getEndAddress(query: String) {
        viewModelScope.launch {
            runCatching {
                val response =
                    addressRemoteImpl.getAddress("KakaoAK c2e35e66f144e5d544ec6782c56b342b", query)

                if (response != null) {
                    _endLng.postValue(response.documents?.get(0)?.x.toString())
                    _endLat.postValue(response.documents?.get(0)?.y.toString())
                }
            }.onFailure { e ->
                Log.d("도착지 좌표 받기 실패", e.toString())
            }
        }
    }

    fun getCongestionData() {
        viewModelScope.launch {
            val list = mutableListOf<String>()
            runCatching {
                val response = congestionRemoteImpl.getCongestion(
                    "6e776654506e736934345155596167",
                    "json",
                    "citydata_ppltn",
                    1,
                    5,
                    "POI002"
                )
                list.add(response!!.SeoulRtd_citydata_ppltn?.get(0)!!.areaCongestLv!!)
                response.SeoulRtd_citydata_ppltn!!.forEach {
                    val data = it.fcstPpltn
                    data!!.forEach { item ->
                        list.add(item?.fcstCongestLv!!)
                    }
                }
                congestionDescription.postValue(list)
            }.onFailure { e ->
                Log.d("인구 데이터 갱신 실패", e.toString())
            }
        }
    }

    fun getDustData() {
        viewModelScope.launch {
            runCatching {
                val list = mutableListOf<String>()
                val response = dustRemoteImpl.getDustData(
                    "37.5847538065233",
                    "126.957855840177",
                    "48b0c79a814c79a5a38bb17b9109a288"
                )
                response.list!!.forEach {
                    list.add(it.main!!.aqi!!)
                }
                dustAqi.postValue(list)
            }.onFailure { e ->
                Log.d("미세먼지 데이터 갱신 실패", e.toString())
            }
        }
    }

    fun getWeatherData() {
        viewModelScope.launch {
            val list = mutableListOf<String>()
            runCatching {
                val response = weatherRemoteImpl.getWeather(
                    "37.5847538065233",
                    "126.957855840177",
                    "48b0c79a814c79a5a38bb17b9109a288"
                )
                response.list?.forEach { item ->
                    val data = item.weather
                    data.forEach {
                        list.add(it.main!!)
                    }
                }
                weatherDescription.postValue(list)
            }.onFailure { e ->
                Log.d("날씨 데이터 갱신 실패", e.toString())
            }
        }
    }

    private fun getCongestionScore(): Int {
        var score: Int = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "여유" -> score += 3
                "보통" -> score += 2
                "약간 붐빔" -> score += 1
                else -> score += 0
            }
        }
        if (score >= 30) {
            score = 30
        }
        return score
    }

    private fun getWeatherScore(): Int {
        var score: Int = 0
        val list = weatherDescription.value
        list?.forEach {
            when (it) {
                "Clear" -> score += 2
                "Clouds" -> score += 1
                else -> score += 0
            }
        }
        if (score >= 40) {
            score = 40
        }
        return score
    }

    private fun getDustScore(): Int {
        var score: Int = 0
        var sum: Int = 0
        var average: Int
        val list = dustAqi.value
        list?.forEach {
            sum += it.toInt()
        }
        average = sum / list!!.size
        if (average <= 2) {
            score = 30
        } else if (average > 2 && average <= 3) {
            score = 25
        } else if (average > 3 && average <= 4) {
            score = 15
        } else
            score = 5

        return score
    }

    fun getToTalScore() {
       var score1 = getDustScore()
       var score2 = getCongestionScore()
       var score3 = getWeatherScore()
       var totalScore = score1 + score2 + score3
        Log.d("총 점수", "여행 점수는 총 ${totalScore}점 입니다")
    }

}

class SearchViewModelFactory : ViewModelProvider.Factory {

    private val addressRepository = AddressRemoteImpl(AddressRetrofit.search)
    private val dustRepository = DustRemoteImpl(DustRetrofit.search)
    private val congestionRepository = CongestionRemoteImpl(CongestionRetrofit.search)
    private val weatherRepository = WeatherRemoteImpl(WeatherRetrofit.search)

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T = SearchViewModel(
        addressRepository,
        dustRepository,
        congestionRepository,
        weatherRepository
    ) as T
}