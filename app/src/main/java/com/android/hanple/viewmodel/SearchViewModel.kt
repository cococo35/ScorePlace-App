package com.android.hanple.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.android.hanple.Address.AddressRemoteImpl
import com.android.hanple.Dust.DustRemoteImpl
import com.android.hanple.Room.RecommendDAO
import com.android.hanple.Weather.WeatherRemoteImpl
import com.android.hanple.adapter.CategoryPlace
import com.android.hanple.data.congestion.remote.CongestionRemoteImpl
import com.android.hanple.network.AddressRetrofit
import com.android.hanple.network.CongestionRetrofit
import com.android.hanple.network.DustRetrofit
import com.android.hanple.network.WeatherRetrofit
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import kotlinx.coroutines.launch
import java.util.Arrays

class SearchViewModel(
    private val addressRemoteImpl: AddressRemoteImpl,
    private val dustRemoteImpl: DustRemoteImpl,
    private val congestionRemoteImpl: CongestionRemoteImpl,
    private val weatherRemoteImpl: WeatherRemoteImpl,
) : ViewModel() {

    private val _Lat = MutableLiveData<String>()
    private val _Lng = MutableLiveData<String>()
    private val _selectPlace = MutableLiveData<Place?>()
    val selectPlace : LiveData<Place?> get() = _selectPlace
    private val timeStamp = MutableLiveData<List<String>>()
    private val placeClient = MutableLiveData<PlacesClient>()
    private val notDrivingCar = MutableLiveData<Boolean>()

    private val parkingList = MutableLiveData<List<Place>>()
    private val weatherDescription = MutableLiveData<List<String>>()
    val readWeatherDescription : LiveData<List<String>> get() = weatherDescription
    private val dustAqi = MutableLiveData<List<String>>()
    private val congestionDescription = MutableLiveData<List<String>>()
    private val _nearByPlace = MutableLiveData<MutableList<CategoryPlace>>()
    val nearByPlace : LiveData<MutableList<CategoryPlace>> get() = _nearByPlace

    private val nearByPlaceBuffer = MutableLiveData<List<Place>>()

    private val congestScore = MutableLiveData<Int>()
    val readCongestScore : LiveData<Int> get() = congestScore
    private val weatherScore = MutableLiveData<Int>()
    val readWeatherScore : LiveData<Int> get() = weatherScore
    private val dustScore = MutableLiveData<Int>()
    val readDustScore : LiveData<Int> get() = dustScore
    private val transportScore = MutableLiveData<Int>()
    val readTransportScore : LiveData<Int> get() = transportScore

    private val costScore = MutableLiveData<Int>()

    val readCostScore : LiveData<Int> get() = costScore

    private val _totalScore = MutableLiveData<Int>()

    val totalScore: LiveData<Int> get() = _totalScore


    val _recommandPlace = MutableLiveData<List<CategoryPlace>>()
    val recommendPlace : LiveData<List<CategoryPlace>> get() = _recommandPlace
    //선택지 좌표 넣기
    fun getSelectPlaceLatLng(data: LatLng){
        _Lat.postValue(data.latitude.toString())
        _Lng.postValue(data.longitude.toString())
    }
    fun getPlacedata(data: Place){
        _selectPlace.value = data
    }
    fun resetPlaceData(){
        val empty : Place? = null
        _selectPlace.value = empty
    }
    //구글 클라이언트 설정
    fun setPlacesAPIClient(placesClient: PlacesClient){
        placeClient.postValue(placesClient)
    }
    fun getTimeStamp(str1: String, str2: String){
        val list = listOf(str1, str2)
        timeStamp.postValue(list)
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
                list.add(response!!.seoulRtdCitydataPpltn?.get(0)!!.areaCongestLv!!)
                response.seoulRtdCitydataPpltn!!.forEach {
                    val data = it.fcstPpltn
                    data!!.forEach { item ->
                        list.add(item?.fcstCongestLv!!)
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
                response.list!!.forEach {
                    list.add(it.main!!.aqi!!)
                }
                dustAqi.value = list
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
                    _Lat.value!!,
                    _Lng.value!!,
                    "48b0c79a814c79a5a38bb17b9109a288"
                )
                response.list?.forEach { item ->
                    val data = item.weather
                    data.forEach {
                        list.add(it.main!!)
                    }
                }
                weatherDescription.value = list
                Log.d("날씨 데이터", "${weatherDescription.value.toString()}")
            }.onFailure { e ->
                Log.d("날씨 데이터 갱신 실패", e.toString())
            }
        }
    }

    fun getParkingData() {
        notDrivingCar.value = false
        var placeField: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME)
        var includeType = listOf("parking")
        var latLng = LatLng(_Lat.value!!.toDouble(), _Lng.value!!.toDouble())
        var circle = CircularBounds.newInstance(latLng, 500.0)
        val searchNearbyRequest = SearchNearbyRequest.builder(circle, placeField)
            .setIncludedTypes(includeType)
            .setMaxResultCount(10)
            .build()
        placeClient.value!!.searchNearby(searchNearbyRequest)
            .addOnSuccessListener { response ->
                parkingList.postValue(response.places)
            }
            .addOnFailureListener { e ->
                Log.d("주차장 정보 불러오기 실패", e.toString())
            }
    }
    fun usePublic(){
        notDrivingCar.value = true
    }

    fun getNearByPlace(type: String) {
        val categoryPlaceList = mutableListOf<CategoryPlace>()
        var placeField: List<Place.Field> = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.RATING,
            Place.Field.OPENING_HOURS,
            Place.Field.ADDRESS,
            Place.Field.PHOTO_METADATAS
        )
        var includeType = listOf(type)
        var latLng = LatLng(_Lat.value!!.toDouble(), _Lng.value!!.toDouble())
        var circle = CircularBounds.newInstance(latLng, 500.0)
        val searchNearbyRequest = SearchNearbyRequest.builder(circle, placeField)
            .setIncludedTypes(includeType)
            .setMaxResultCount(10)
            .build()
        placeClient.value!!.searchNearby(searchNearbyRequest)
            .addOnSuccessListener { response ->
                nearByPlaceBuffer.value = response.places
                response.places.forEach { it ->
                    val data = CategoryPlace(
                        it.address,
                        it.rating,
                        null,
                        it.id,
                        it.name,
                        false,
                        it.openingHours
                    )
                    categoryPlaceList.add(data)
                }
                getCategoryImage(categoryPlaceList)
                _nearByPlace.value = categoryPlaceList
            }
            .addOnFailureListener { e ->
                Log.d("근처 장소 정보 불러오기 실패", e.toString())
            }
    }

    private fun getCategoryImage(list: MutableList<CategoryPlace>) {
        val size = list.size
        val bufferList = nearByPlaceBuffer.value
        for (i in 0..size - 1) {
            val meta = bufferList?.get(i)?.photoMetadatas?.get(0)
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
     fun getCongestionScore(type: Int) : Int{
        var score: Int = 0
        val list = congestionDescription.value
         if(list == null){
             congestScore.value = 10
             Log.d("혼잡도 점수", score.toString())
             return 0
         }
         else {
           when(type){
               1 -> score = getCongestionScoreType1()
               2 -> score = getCongestionScoreType2()
               3 -> score = getCongestionScoreType3()
               4 -> score = getCongestionScoreType4()
               else -> score = getCongestionScoreType5()
           }
             congestScore.value = score
             Log.d("혼잡도 점수", score.toString())
             return score
         }
    }
    private fun getCongestionScoreType1() : Int{
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "여유" -> score += 2
                "보통" -> score += 1
                "약간 붐빔" -> score += 0
                else -> score += -1
            }
        }
        if(score < 0){
            score = 0
        }
        else if(score > 20){
            score = 20
        }
        return score
    }
    private fun getCongestionScoreType2() : Int{
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "약간 붐빔" -> score += 1
                "붐빔" -> score += -1
                else -> score += 2
            }
        }
        if(score < 0){
            score = 0
        }
        else if(score > 20){
            score = 20
        }
        return score
    }
    private fun getCongestionScoreType3() : Int{
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "보통" -> score += 2
                "약간 붐빔" -> score += 1
                else -> score += 0
            }
        }
        if(score < 0){
            score = 0
        }
        else if(score > 20){
            score = 20
        }
        return score
    }
    private fun getCongestionScoreType4() : Int{
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "약간 붐빔" -> score += 1
                "붐빔" -> score += 2
                else -> score += 0
            }
        }
        if(score < 0){
            score = 0
        }
        else if(score > 20){
            score = 20
        }
        return score
    }
    private fun getCongestionScoreType5() : Int{
        var score = 0
        val list = congestionDescription.value
        list?.forEach {
            when (it) {
                "약간 붐빔" -> score += 1
                "붐빔" -> score += 2
                else -> score += -1
            }
        }
        if(score < 0){
            score = 0
        }
        else if(score > 20){
            score = 20
        }
        return score
    }
    fun getWeatherScore() {
        var score: Int = 20
        var count = 0
        val list = weatherDescription.value
        list?.forEach {
            when (it) {
                "Clear" -> count += 2
                "Clouds" -> count += 1
                else -> count = 0
            }
        }
        if(count >= 10){
            count = 10
        }
        var importance = count / 10
        score = score * importance
        Log.d("날씨 점수", score.toString())
        weatherScore.value = score
    }

    fun getDustScore() {
        var score: Int = 0
        var sum: Int = 0
        var average: Int
        val list = dustAqi.value
        list?.forEach {
            sum += it.toInt()
        }
        average = sum / list!!.size
        if (average <= 2) {
            score = 20
        } else if (average > 2 && average <= 3) {
            score = 16
        } else if (average > 3 && average <= 4) {
            score = 12
        } else
            score = 6
        Log.d("미세먼지 점수", score.toString())
        dustScore.value = score
    }

    fun getCostScore(data: String)  {
        Log.d("비용 인풋", "${data}")
        Log.d("가격 수준", "${_selectPlace?.value?.priceLevel.toString()}")
        var score: Int = 0
        if(data == null){
            score = 5
        }
        else {
        val price = data.toInt()
        if(_selectPlace?.value?.priceLevel == null){
            costScore.value = 10
        }
        else {
            if (price <= 50000) {
                when (_selectPlace?.value?.priceLevel) {
                    0 -> score = 10
                    1 -> score = 20
                    2 -> score = 15
                    else -> score = 0
                }
            }
            if (price > 50000 && price <= 150000) {
                when (_selectPlace.value?.priceLevel) {
                    3 -> score = 15
                    4 -> score = 10
                    5 -> score = 0
                    else -> score = 20
                }
            }
            if (price > 150000) {
                when (_selectPlace.value?.priceLevel) {
                    5 -> score = 10
                    else -> score = 20
                }
            }
            costScore.value = score
        }
        }
    }
    fun getTransportScore(){
        var inputScore: Int = 0
        if(notDrivingCar.value == true){
            val score = (weatherScore.value!! + dustScore.value!!) / 4
            Log.d("교통 점수", score.toString())
            transportScore.value = score
        }
        else{
           if(parkingList.value == null){
               inputScore = 0
           }
           else{
               if(parkingList.value!!.size >= 1 && parkingList.value!!.size < 3){
                   inputScore = 10
               }
               else if(parkingList.value!!.size >= 3){
                   inputScore = 20
               }
           }
            Log.d("교통 점수", inputScore.toString())
           transportScore.value = inputScore
        }
    }

    fun getToTalScore() {
        _totalScore.postValue(weatherScore.value!! + dustScore.value!! + transportScore.value!! + costScore.value!! + congestScore.value!!)
    }
    fun resetScore(){
        weatherScore.value = 0
        dustScore.value = 0
        transportScore.value = 0
        congestScore.value = 0
        congestScore.value = 0
        _totalScore.value = 0
    }

    fun getRecommendPlace(list: List<Int>, dao: RecommendDAO){
        val recommendListBuffer = mutableListOf<CategoryPlace>()
        viewModelScope.launch {
            list.forEach { it ->
                val recommendID = dao.getRecommendPlaceById(it).name
                val placeFields =
                    Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.RATING, Place.Field.OPENING_HOURS, Place.Field.PHOTO_METADATAS)
                val request = FetchPlaceRequest.newInstance(recommendID, placeFields)
                val placeTask : Task<FetchPlaceResponse> = placeClient.value!!.fetchPlace(request)
                placeTask.addOnSuccessListener { reponse ->
//                    val uri = getImage(reponse.place)
                    val data = CategoryPlace(
                        reponse.place.address,
                        reponse.place.rating,
                        null,
                        reponse.place.id,
                        reponse.place.name,
                        false,
                        reponse.place.openingHours
                    )
//                    data.setImgUri(uri!!)
                    recommendListBuffer.add(data)
                }
            }
            _recommandPlace.value = recommendListBuffer
        }
    }
//    private fun getImage(data: Place) : Uri? {
//            val meta = data.photoMetadatas?.get(0)
//            var uri : Uri? = null
//            val request = FetchResolvedPhotoUriRequest.builder(meta)
//                .setMaxWidth(500)
//                .setMaxHeight(300)
//                .build()
//            placeClient.value!!.fetchResolvedPhotoUri(request)
//                .addOnSuccessListener { it ->
//                    uri = it.uri
//                }
//        return uri
//    }
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