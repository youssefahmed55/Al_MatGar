package com.example.e_commerce.sendNotificationApi


import com.example.e_commerce.BuildConfig
import com.example.e_commerce.pojo.notification.RootModel
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIService {

    @Headers(
        "Authorization: key=${BuildConfig.FCM_API_KEY}",
        "Content-Type:application/json"
    )
    @POST("fcm/send")
    suspend fun sendNotification(@Body rootModel: RootModel): ResponseBody
}