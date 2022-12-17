package com.example.e_commerce.pojo.notification

import com.google.gson.annotations.SerializedName

data class RootModel(@SerializedName("to") val token : String = "" , @SerializedName("notification") val notification : NotificationModel)

