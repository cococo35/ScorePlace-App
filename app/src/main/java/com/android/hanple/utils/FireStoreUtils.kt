//package com.android.hanple.utils
//
//import android.util.Log
//import com.google.firebase.firestore.FirebaseFirestore
//
////https://firebase.google.com/docs/firestore/quickstart?hl=ko#kotlin+ktx
//class FireStoreUtils {
//    val db = FirebaseFirestore.getInstance()
//
//    val user = hashMapOf(
//        "id" to "user123",
//        "name" to " 홍길동",
//        "password" to "password123",
//        "phoneNumber" to "+821012345678",
//    )
//
//    fun things(){
//        db.collection("user-info")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    Log.d("FireStore", "${document.id} => ${document.data}")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("FireStore", "Error getting documents.", exception)
//            }
//    }
//
//}