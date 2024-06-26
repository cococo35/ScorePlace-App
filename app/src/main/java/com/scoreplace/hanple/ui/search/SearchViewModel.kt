package com.scoreplace.hanple.ui.search

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.OpeningHours
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.scoreplace.hanple.Address.AddressRemoteImpl
import com.scoreplace.hanple.Dust.DustRemoteImpl
import com.scoreplace.hanple.Weather.WeatherRemoteImpl
import com.scoreplace.hanple.data.CategoryPlace
import com.scoreplace.hanple.data.congestion.CongestionRemoteImpl
import com.scoreplace.hanple.network.AddressRetrofit
import com.scoreplace.hanple.network.CongestionRetrofit
import com.scoreplace.hanple.network.DustRetrofit
import com.scoreplace.hanple.network.WeatherRetrofit
import com.scoreplace.hanple.room.RecommendDAO
import com.scoreplace.hanple.room.RecommendPlace
import com.scoreplace.hanple.room.recommendPlaceGoogleID
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Calendar
import java.util.Date

class SearchViewModel(
    private val addressRemoteImpl: AddressRemoteImpl,
    private val dustRemoteImpl: DustRemoteImpl,
    private val congestionRemoteImpl: CongestionRemoteImpl,
    private val weatherRemoteImpl: WeatherRemoteImpl,
) : ViewModel() {

    private val _Lat = MutableLiveData<String>()
    private val _Lng = MutableLiveData<String>()
    private val _selectPlace = MutableLiveData<Place?>()
    val selectPlace: LiveData<Place?> get() = _selectPlace

    private val _selectRecommendPlace = MutableLiveData<CategoryPlace?>()
    val selectRecommendPlace: LiveData<CategoryPlace?> get() = _selectRecommendPlace

    private val _startTime = MutableLiveData<String>()
    private val _endTime = MutableLiveData<String>()
    val startTime: LiveData<String> get() = _startTime
    val endTime: LiveData<String> get() = _endTime

    private val timeStamp = MutableLiveData<List<String>>()
    val readTimeStamp: LiveData<List<String>> get() = timeStamp

    private val placeClient = MutableLiveData<PlacesClient>()
    private val notDrivingCar = MutableLiveData<Boolean>()

    private val parkingList = MutableLiveData<List<Place>>()
    private val weatherDescription = MutableLiveData<List<String>>()
    val readWeatherDescription: LiveData<List<String>> get() = weatherDescription
    private val dustAqi = MutableLiveData<List<String>>()
    private val congestionDescription = MutableLiveData<List<String>>()
    private val _nearByPlace = MutableLiveData<MutableList<CategoryPlace>>()
    val nearByPlace: LiveData<MutableList<CategoryPlace>> get() = _nearByPlace

    private val nearByPlaceBuffer = MutableLiveData<List<Place>>()

    private val congestScore = MutableLiveData<Int>()
    val readCongestScore: LiveData<Int> get() = congestScore
    private val weatherScore = MutableLiveData<Int>()
    val readWeatherScore: LiveData<Int> get() = weatherScore
    private val dustScore = MutableLiveData<Int>()
    val readDustScore: LiveData<Int> get() = dustScore
    private val transportScore = MutableLiveData<Int>()
    val readTransportScore: LiveData<Int> get() = transportScore

    private val costScore = MutableLiveData<Int>()

    val readCostScore: LiveData<Int> get() = costScore

    private val _totalScore = MutableLiveData<Int>()

    val totalScore: LiveData<Int> get() = _totalScore
    private var additionalCount = 0
    private var addtionalCountCongest = 0
    private var addtionalCountWeather = 0
    private var addtionalCountDust = 0
    private var additionalCountCost = 0
    private var additionalCountTransport = 0

    val _recommandPlace = MutableLiveData<List<CategoryPlace>>()
    val recommendPlace: LiveData<List<CategoryPlace>> get() = _recommandPlace

    private val placeBuffer = MutableLiveData<Place>()

    private val _selectCategoryPlaceImg = MutableLiveData<Uri?>()
    private val _selectCategoryPlaceAddress = MutableLiveData<String?>()
    private val _selectCategoryPlaceName = MutableLiveData<String?>()
    private val _selectCategoryPlaceSummary = MutableLiveData<String?>()
    private val _selectCategoryPlaceOpeningHour = MutableLiveData<OpeningHours?>()

    val selectCategoryPlaceImg: LiveData<Uri?> get() = _selectCategoryPlaceImg
    val selectCategoryPlaceAddress: LiveData<String?> get() = _selectCategoryPlaceAddress
    val selectCategoryPlaceName: LiveData<String?> get() = _selectCategoryPlaceName
    val selectCategoryPlaceSummary: LiveData<String?> get() = _selectCategoryPlaceSummary
    val selectCategoryPlaceOpeningHour: LiveData<OpeningHours?> get() = _selectCategoryPlaceOpeningHour

    //선택지 좌표 넣기
    fun getSelectPlaceLatLng(data: LatLng) {
        _Lat.postValue(data.latitude.toString())
        _Lng.postValue(data.longitude.toString())
    }

    fun getPlacedata(data: Place) {
        _selectPlace.value = data
        _selectRecommendPlace.value = null
    }

    fun resetPlaceData() {
        val empty: Place? = null
        _selectPlace.value = empty
    }

    fun getSelectRecommendData(data: CategoryPlace) {
        _selectRecommendPlace.value = data
        _selectPlace.value = null
    }

    fun resetRecommendPlaceData() {
        val empty: CategoryPlace? = null
        _selectRecommendPlace.value = empty
    }

    //구글 클라이언트 설정
    fun setPlacesAPIClient(placesClient: PlacesClient) {
        placeClient.postValue(placesClient)
    }

    fun getTimeStamp(str1: String, str2: String) {
        val list = listOf(str1, str2)
        timeStamp.postValue(list)
    }

    fun resetTimeStamp() {
        timeStamp.value = emptyList()
    }

    fun resetTime() {
        _startTime.value = "not input"
        _endTime.value = "not input"
    }

    fun getStartTime(str: String) {
        _startTime.value = str
    }

    fun getEndTime(str: String) {
        _endTime.value = str
    }

    fun getCongestionData(query: String) {
        viewModelScope.launch {
            val list = mutableListOf<String>()
            runCatching {
                val response = congestionRemoteImpl.getCongestion(
                    "6e776654506e736934345155596167",
                    "json",
                    "citydata_ppltn",
                    1,
                    5,
                    query
                )
                list.add(response.seoulRtdCitydataPpltn?.get(0)!!.areaCongestLv!!)
                response.seoulRtdCitydataPpltn.forEach {
                    val data = it.fcstPpltn
                    data!!.forEach { item ->
                        list.add(item.fcstCongestLv!!)
                    }
                }
                Log.d("리스트 보기", list.toString())
                congestionDescription.value = list
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
                    _Lat.value!!,
                    _Lng.value!!,
                    "48b0c79a814c79a5a38bb17b9109a288"
                )
                if(response != null) {
                    response.list.forEach {
                        list.add(it.main!!.aqi!!)
                    }
                    dustAqi.value = list
                }
            }.onFailure { e ->
                Log.d("미세먼지 데이터 갱신 실패", e.toString())
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeatherData(today: String) {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val endTimeBuffer : Date? = _endTime.value?.let { format.parse(it) }
        val startTimeBuffer : Date? = _startTime.value?.let { format.parse(it) }
        var EndTime : Date? = null
        val cal = Calendar.getInstance()
        if (endTimeBuffer != null) {
            cal.setTime(endTimeBuffer)
            cal.add(Calendar.HOUR , 2)
            EndTime  = cal.time
        }
        Log.d("end time", EndTime.toString())
        Log.d("start time", startTimeBuffer.toString() )

        viewModelScope.launch {
            val list = mutableListOf<String>()
            runCatching {
                val response = weatherRemoteImpl.getWeather(
                    _Lat.value!!,
                    _Lng.value!!,
                    "48b0c79a814c79a5a38bb17b9109a288"
                )
                response.list?.forEach { item ->
                    Log.d("전체 시간", item.dt_txt.toString())
                    val bufferDate : Date = item.dt_txt!!.let { format.parse(it) }!!
                    if (bufferDate < EndTime && bufferDate >= startTimeBuffer) {
                        Log.d("범위에 포함된 날씨", item.dt_txt.toString())
                        val data = item.weather
                        data.forEach {
                            list.add(it.main!!)
                        }
                    }
                }
                weatherDescription.value = list
                Log.d("날씨 데이터", weatherDescription.value.toString())
            }.onFailure { e ->
                Log.d("날씨 데이터 갱신 실패", e.toString())
            }
        }
    }

    // radius 500 -> 1000
    fun getParkingData() {
        notDrivingCar.value = false
        val placeField: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME)
        val includeType = listOf("parking")
        val latLng = LatLng(_Lat.value!!.toDouble(), _Lng.value!!.toDouble())
        val circle = CircularBounds.newInstance(latLng, 1000.0)
        val searchNearbyRequest = SearchNearbyRequest.builder(circle, placeField)
            .setIncludedTypes(includeType)
            .setMaxResultCount(6)
            .build()
        placeClient.value!!.searchNearby(searchNearbyRequest)
            .addOnSuccessListener { response ->
                parkingList.postValue(response.places)
            }
            .addOnFailureListener { e ->
                Log.d("주차장 정보 불러오기 실패", e.toString())
            }
    }

    fun usePublic() {
        notDrivingCar.value = true
    }

    @SuppressLint("SuspiciousIndentation")
    fun getNearByPlace(type: String) {
        viewModelScope.launch {
            val categoryPlaceList = mutableListOf<CategoryPlace>()
            val placeField: List<Place.Field> = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.RATING,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG,
                Place.Field.PRICE_LEVEL,
                Place.Field.PHOTO_METADATAS,
            )
            var includeType = listOf(type)
            var latLng = LatLng(_Lat.value!!.toDouble(), _Lng.value!!.toDouble())
            var circle = CircularBounds.newInstance(latLng, 800.0)
            val searchNearbyRequest = SearchNearbyRequest.builder(circle, placeField)
                .setIncludedTypes(includeType)
                .setMaxResultCount(6)
                .build()
            placeClient.value!!.searchNearby(searchNearbyRequest)
                .addOnSuccessListener { response ->
                    Log.d("주변 장소 응답 확인", response.places.toString())
                    if (response.places.isEmpty()) {
                        _nearByPlace.value = emptyList<CategoryPlace>().toMutableList()
                    } else {
                        nearByPlaceBuffer.value = response.places
                        response.places.forEach {
                            val data = CategoryPlace(
                                it.address,
                                it.rating,
                                null,
                                it.id,
                                it.name,
                                it.latLng,
                                it.priceLevel,
                                null,
                                false,
                                null,
                            )
                            categoryPlaceList.add(data)
                            getCategoryImage(categoryPlaceList)
                            _nearByPlace.value = categoryPlaceList
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.d("근처 장소 정보 불러오기 실패", e.toString())
                }

        }
    }

    fun getCategoryImage(list: MutableList<CategoryPlace>) {
        val size = list.size
        val bufferList = nearByPlaceBuffer.value
        viewModelScope.launch {
            for (i in 0..size - 1) {
                val meta = bufferList?.get(i)?.photoMetadatas?.get(0)
                if (meta != null) {
                    val request = FetchResolvedPhotoUriRequest.builder(meta)
                        .setMaxWidth(500)
                        .setMaxHeight(300)
                        .build()
                    placeClient.value!!.fetchResolvedPhotoUri(request)
                        .addOnSuccessListener { it ->
                            list[i].setImgUri(it.uri)
                        }
                }
            }
        }
    }


    // congestionScore 가중치 20 -> 30
    // getCongestionScoreType 별 점수 계산 수정
    fun getCongestionScore(type: Int): Int {
        addtionalCountCongest = 0
        var score = 15
        val list = congestionDescription.value
        Log.d("혼잡도 리스트", list.toString())
        if (list == null) {
            congestScore.value = 15
            return 0
        } else {
            score = when (type) {
                1 -> getCongestionScoreType1()
                2 -> getCongestionScoreType2()
                3 -> getCongestionScoreType3()
                4 -> getCongestionScoreType4()
                else -> getCongestionScoreType5()
            }
            Log.d("혼잡도 점수", score.toString())
            congestScore.postValue(score)
            if (score >= 15) {
                addtionalCountCongest++
            } else {
                addtionalCountCongest--
            }
        }
        return score
    }

    private fun getCongestionScoreType1(): Int {
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "여유" -> score += 4
                "보통" -> score++
                "약간 붐빔" -> score--
                else -> score -= 2
            }
        }
        if (score < 0) {
            score = 0
        } else if (score > 30) {
            score = 30
        }
        return score
    }

    private fun getCongestionScoreType2(): Int {
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "여유" -> score += 3
                "보통" -> score += 2
                "약간 붐빔" -> score += 0
                else -> score--
            }
        }
        if (score < 0) {
            score = 0
        } else if (score > 30) {
            score = 30
        }
        return score
    }

    private fun getCongestionScoreType3(): Int {
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "여유" -> score++
                "보통" -> score += 2
                "약간 붐빔" -> score++
                else -> score += 0
            }
        }
        if (score < 0) {
            score = 0
        } else if (score > 30) {
            score = 30
        }
        return score
    }

    private fun getCongestionScoreType4(): Int {
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "보통" -> score++
                "약간 붐빔" -> score += 3
                "붐빔" -> score += 4
                else -> score += 0
            }
        }
        if (score < 0) {
            score = 0
        } else if (score > 30) {
            score = 30
        }
        return score
    }

    private fun getCongestionScoreType5(): Int {
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "약간 붐빔" -> score += 2
                "붐빔" -> score += 4
                else -> score--
            }
        }
        if (score < 0) {
            score = 0
        } else if (score > 30) {
            score = 30
        }
        return score
    }

    // weatherScore 가중치 20 -> 30
    // score 계산 방식 변경
    fun getWeatherScore() {
        addtionalCountWeather = 0
        val score: Int
        var count = 0
        val list = weatherDescription.value
        list?.forEach {
            when (it) {
                "Clear" -> count += 6
                "Clouds" -> count += 3
                "Rain" -> count -= 4
                else -> count = 0
            }
        }

        score = if (count >= 40) {
            30
        } else if (count >= 34) {
            25
        } else if (count >= 28) {
            20
        } else if (count >= 22) {
            15
        } else if (count >= 16) {
            10
        } else if (count >= 10) {
            5
        } else {
            0
        }

        Log.d("날씨 점수", score.toString())
        weatherScore.postValue(score)

        if (score >= 15) {
            addtionalCountWeather++
        } else {
            addtionalCountWeather--
        }
    }

    // dustScore 가중치 20 -> 10
    fun getDustScore() {
        addtionalCountDust = 0
        var sum = 0
        var score: Int = 0
        var average: Int = 0
        val list = dustAqi.value
        viewModelScope.launch {
            if (list != null) {
                list?.forEach {
                    sum += it.toInt()
                }

                average = sum / list!!.size
                score = if (average <= 2) {
                    10
                } else if (average <= 3) {
                    8
                } else if (average <= 4) {
                    6
                } else {
                    3
                }
                Log.d("미세먼지 점수", score.toString())
                dustScore.postValue(score)

                if (score >= 6) {
                    addtionalCountDust++
                } else {
                    addtionalCountDust--
                }
            }
            else
                dustScore.postValue(5)
        }
    }


    // costScore 가중치 20 -> 10

    fun getCostScore(price: Int) {
        additionalCountCost = 0
        Log.d("비용 인풋", price.toString())
        var score = 5

        if (_selectPlace.value != null && _selectRecommendPlace.value == null) {
            if (_selectPlace.value?.priceLevel == null) {
                costScore.value = 5
            } else {
                if (price <= 50000) {
                    score = when (_selectPlace.value?.priceLevel) {
                        0 -> 6
                        1 -> 10
                        2 -> 8
                        else -> 0
                    }
                }
                if (price in 50001..150000) {
                    score = when (_selectPlace.value?.priceLevel) {
                        3 -> 10
                        4 -> 9
                        5 -> 1
                        else -> 8
                    }
                }
                if (price > 150000) {
                    score = when (_selectPlace.value?.priceLevel) {
                        4 -> 9
                        5 -> 10
                        else -> 8
                    }
                }
                costScore.postValue(score)
            }
            if (score >= 5) {
                additionalCountCost++
            } else {
                additionalCountCost--
            }
        } else if (_selectPlace.value == null && _selectRecommendPlace.value != null) {
            if (_selectRecommendPlace.value?.priceLevel == null) {
                costScore.value = 5
            } else {
                if (price <= 50000) {
                    score = when (_selectRecommendPlace.value?.priceLevel) {
                        0 -> 6
                        1 -> 10
                        2 -> 8
                        else -> 0
                    }
                }
                if (price in 50001..150000) {
                    score = when (_selectRecommendPlace.value?.priceLevel) {
                        3 -> 10
                        4 -> 9
                        5 -> 1
                        else -> 8
                    }
                }
                if (price > 150000) {
                    score = when (_selectRecommendPlace.value?.priceLevel) {
                        4 -> 9
                        5 -> 10
                        else -> 8
                    }
                }
                costScore.postValue(score)
            }
            if (score >= 5) {
                additionalCountCost++
            } else {
                additionalCountCost--
            }
        }
    }

    fun getTransportScore() {
        additionalCountTransport = 0
        var inputScore = 10
        if (notDrivingCar.value == true) {
            val score = (weatherScore.value!! + dustScore.value!!) / 2
            Log.d("교통 점수", score.toString())
            transportScore.value = score
        } else {
            if (parkingList.value == null) {
                inputScore = 0
            } else {
                if (parkingList.value!!.size in 1..2) {
                    inputScore = 10
                } else if (parkingList.value!!.size >= 3) {
                    inputScore = 20
                }
            }
            Log.d("교통 점수", inputScore.toString())
            transportScore.postValue(inputScore)
        }
        if (inputScore >= 10) {
            additionalCountTransport++
        } else {
            additionalCountTransport--
        }
    }

    fun getToTalScore() {
        additionalCount = 0
        val score =
            weatherScore.value!! + dustScore.value!! + transportScore.value!! + costScore.value!! + congestScore.value!!
        additionalCount =
            additionalCountCost + additionalCountTransport + addtionalCountDust + addtionalCountCongest + addtionalCountWeather
        val addScore: Double = if (additionalCount >= 0) {
            (score * 0.5 + 25) + (additionalCount * additionalCount)          // 0 ~ 100 까지인 score 의 범위를 25 ~ 75 로 바꾸는 식 + additionalCount 제곱
        } else {
            (score * 0.5 + 25) - (additionalCount * additionalCount)          // additionalCount 가 양수일 때, 음수일 때 if문으로 나눠 계산
        }
        _totalScore.postValue(score)
        Log.d("날씨 점수", weatherScore?.value.toString())
        Log.d("미세먼지 점수", dustScore?.value.toString())
        Log.d("교통 점수", transportScore?.value.toString())
        Log.d("비용 점수", costScore?.value.toString())
        Log.d("혼잡도 점수", congestScore?.value.toString())
    }

    fun resetScore() {
        weatherScore.value = 0
        dustScore.value = 0
        transportScore.value = 0
        congestScore.value = 0
        congestScore.value = 0
        _totalScore.value = 0
        additionalCount = 0
        addtionalCountDust = 0
        addtionalCountCongest = 0
        additionalCountCost = 0
        additionalCountTransport = 0
        addtionalCountWeather = 0
    }

    @SuppressLint("SuspiciousIndentation")
    fun getRecommendPlace(list: List<Int>, dao: RecommendDAO) {
        val recommendListBuffer = mutableListOf<CategoryPlace>()
        var uri: Uri? = null
        var data: CategoryPlace? = null
        viewModelScope.launch {
            list.forEach { it ->
                val recommendID = dao.getRecommendPlaceById(it).name
                val placeFields =
                    Arrays.asList(
                        Place.Field.ID,
                        Place.Field.NAME,
                        Place.Field.RATING,
                        Place.Field.ADDRESS,
                        Place.Field.LAT_LNG,
                        Place.Field.PRICE_LEVEL,
                        Place.Field.PHOTO_METADATAS,
                    )
                val request = FetchPlaceRequest.newInstance(recommendID, placeFields)

                val placeTask: Task<FetchPlaceResponse> = placeClient.value!!.fetchPlace(request)
                placeTask.addOnSuccessListener { response ->
                    placeBuffer.value = response.place
                    data = CategoryPlace(
                        placeBuffer.value?.address,
                        placeBuffer.value?.rating,
                        null,
                        placeBuffer.value?.id,
                        placeBuffer.value?.name,
                        placeBuffer.value?.latLng,
                        placeBuffer.value?.priceLevel,
                        null,
                        false,
                        null,
                    )
                    getImage(data!!)
                    recommendListBuffer.add(data!!)
                }
            }
            _recommandPlace.value = recommendListBuffer
        }
        Log.d("룸 데이터 출력", "")
    }

    private fun getImage(data: CategoryPlace) {
        viewModelScope.launch {
            val meta = placeBuffer.value?.photoMetadatas?.get(0)
            Log.d("meta", meta.toString())
            if (meta != null) {
                val request = FetchResolvedPhotoUriRequest.builder(meta)
                    .setMaxWidth(500)
                    .setMaxHeight(300)
                    .build()
                placeClient.value!!.fetchResolvedPhotoUri(request)
                    .addOnSuccessListener { it ->
                        data.setImgUri(it.uri)
                    }
            }
        }
    }

    fun setSelectPlaceImg(img: Uri?) {
        _selectCategoryPlaceImg.value = img
    }

    fun setSelectPlaceAddress(str: String) {
        _selectCategoryPlaceAddress.value = str
    }

    fun setSelectPlaceName(str: String) {
        _selectCategoryPlaceName.value = str
    }

    fun setSelectPlaceSummary(str: String) {
        _selectCategoryPlaceSummary.value = str
    }

    fun setSelectPlaceOpeningHour(data: OpeningHours) {
        _selectCategoryPlaceOpeningHour.value = data
    }

    fun insertRoomData(list: List<Int>, dao: RecommendDAO) {
        viewModelScope.launch {
            val firstJob = async {
                for (i in recommendPlaceGoogleID.indices) {
                    dao.insertRecommendPlace(
                        RecommendPlace(
                            i + 1,
                            recommendPlaceGoogleID[i]
                        )
                    )
                }
                Log.d("룸 데이터 삽입", "")
            }
            firstJob.await()
            getRecommendPlace(list, dao)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun properTimeInput() : Boolean {
        var result : Boolean
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val endTimeBuffer : Date? = _endTime.value?.let { format.parse(it) }
        val startTimeBuffer : Date? = _startTime.value?.let { format.parse(it) }
        Log.d("시간 확인", endTimeBuffer.toString())
        Log.d("시간 확인", startTimeBuffer.toString())
        if(startTimeBuffer!! >= endTimeBuffer!!){
            result = false
        }
        else
            result = true

        return result
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
        weatherRepository,
    ) as T
}