package com.example.e_commerce.pojo


import com.example.e_commerce.Constants.PROCESS
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Order(val id : String = "", val productId : String = "" , val price : Double = 0.0, val productName : String = "", val merchantId : String = "", val customerId : String = "", val customerPhone : String = "", val customerLocation : String = "", val nameCustomer : String = "" , val count : Int = 0 , val image : String ?= null, var isFavorite : Boolean = false , val status : String = PROCESS, @ServerTimestamp val start_timeStamp: Date? = null, val end_timeStamp: Date? = null)


