<h1 align="center">
한플
</h1>
<p align="center">
</p>
이미지 업로드 예정

<br/>

## 프로젝트 개요
### '한플' 은 여행 장소의 추천도를 점수로 보여주는 앱입니다.

#### 💡 '한플'은 다음과 같은 분들을 위해 탄생되었습니다.

> 📸 여행 장소를 고르는게 어려운 사람들
> 

> 🌟 내가 정한 여행 장소가 좋은 선택인지 확인하고 싶은 사람들
> 

> 🏞 좋았던 여행 장소를 기억하고 싶은 사람들
> 

<br/>

## 팀원 구성

| **정서영** | **황수영** | **김태준** | **진형준** |

<br/>

### 프로젝트 일정
24/05/27 ~ 24/07/03

<br/>

### 기술 스택
| 분류 | 이름 |
| --- | --- |
| Architecture | <img src="https://img.shields.io/badge/MVVM-FDECC8"> |
| Jetpack | <img src="https://img.shields.io/badge/ViewModel-2C593F"> <img src="https://img.shields.io/badge/LiveData-373737"> <img src="https://img.shields.io/badge/LifeCycle-373737"> <img src="https://img.shields.io/badge/ViewBinding-5A5A5A"> |
| 비동기 처리 | <img src="https://img.shields.io/badge/Coroutine-29456C"> |
| 데이터 처리 | <img src="https://img.shields.io/badge/SharedPreferences-69314C"> |
| Firebase | <img src="https://img.shields.io/badge/Authentication-4285F4"> <img src="https://img.shields.io/badge/Firestore-854C1D"> |
| API통신 | <img src="https://img.shields.io/badge/Retrofit-373737">  |
| 활용API | <img src="https://img.shields.io/badge/https://dapi.kakao.com-FFCD00"> <img src="https://img.shields.io/badge/http://openapi.seoul.go.kr:8088/-492F64"> <img src="https://img.shields.io/badge/https://api.openweathermap.org/data/2.5/air_pollution/-373737"> <img src="https://img.shields.io/badge/https://api.openweathermap.org/data/2.5/-492F64">      |
| 이미지 로더 | <img src="https://img.shields.io/badge/Glide-18BED4">  |
| UI Frameworks | <img src="https://img.shields.io/badge/Fragment-492f64"> <img src="https://img.shields.io/badge/RecyclerViewAdapter-6e3630"> <img src="https://img.shields.io/badge/ListAdapter-373737"> <img src="https://img.shields.io/badge/Navigation Drawer-89632a">  <img src="https://img.shields.io/badge/Bottomsheet-492f64"> <img src="https://img.shields.io/badge/Splash-28456c"> 

<br/>

### 앱 디자인 설계
![image](https://github.com/cococo35/ScorePlace-App/assets/161271441/32c925c4-6668-4c82-bb10-6f76833ca883)

<br/>

## 주요 기능

| [홈 화면] | [검색 화면] | [점수 화면] | [지도 화면] |
| --- | --- | --- | --- |
| - 장소 검색하기(검색 자동완성)<br>- 장소 추천 리스트  | - 활동 시간대 입력하기<br>- 이동 수단 입력하기<br>- 여행 성향 입력하기<br>- 지출 예상 비용 입력하기<br> | - 추천도 점수 확인<br>- 북마크 기능<br>- 상세 점수 보기<br>-카테고리별 주변 장소 확인하기 | -북마크한 장소 맵 마커로 확인하기<br>- 북마크한 장소 정보 확인하기 |

<br/>

## TroubleShooting

#### 비동기함수 간 순서 처리 문제
- 문제: 데이터 인풋과 출력이 비동기로 작동하면서 데이터 인풋과 출력이 순서대로 이루어지지 않아 null이 찍히는 오류가 발생
- 해결책: job 과 join을 활용하여 비동기 함수에서도 순서를 지정하여 해당 부분을 해결
- 개선점:  비동기 작업 간의 순서 보장, 동시성 이슈로 인한 null 전달 오류 방지

#### arraylist를 이용해서 아이템이 중복되는 문제
- 문제: 같은 장소를 중복해서 북마크하면 해당 아이템이 리스트에 여러 번 추가되는 문제 발생
- 해결책: 보관함 데이터를 저장하는 코드에서 ArrayList를 Set으로 변경하고, 데이터를 불러올 때도 Set에서 꺼내도록 수정
- 개선점: 같은 장소를 여러 번 북마크해도 보관함 리스트에 해당 아이템이 하나만 표시

#### API 주소에서 Query 파라미터 형식이 아닌경우
- 문제: URL 문자열을 조합하여 API 호출을 시도했지만, 하드코딩된 값을 사용하게 되어 유지보수성이 떨어지는 문제 발생
- 해결책: Retrofit 라이브러리에서 제공하는 @Path 어노테이션을 사용하도록 변경
- 개선점: 유지보수성 향상

#### navigation drawer를 구현하면 액티비티 위에 있는 프래그먼트가 제대로 동작하지 않는 문제
- 문제: DrawerLayout을 생성할 때 View 계층에 문제가 발생하여 프래그먼트가 동작하지 않는 문제
- 해결책: NavigationView가 DrawerLayout 안에 올바르게 위치하도록 설정했고, 프래그먼트가 들어갈 레이아웃을 DrawerLayout의 자식 뷰로 지정하여 해결
- 개선점: 프래그먼트가 문제없이 동작하는 것을 확인

#### Firebase Auth 문제
- 문제: Firebase의 ktx 모듈이 deprecated 될 예정
- 해결책: 우선은 ktx를 그대로 사용하고, 추후 ktx 지원이 중단되면 문서의 마이그레이션 가이드를 참고해 수정하기로 결정
- 개선점: Retrofit보다 더 간단한 데이터 처리

<br/>

## 유지보수 및 개선사항

### 추가 예정
