package com.example.e_commerce.pojo

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Order(val id : String = "", val productId : String = "", val merchantId : String = "", val customerId : String = "", val count : Int = 0, @ServerTimestamp val timeStamp: Date? = null, val status : String = "Process")
