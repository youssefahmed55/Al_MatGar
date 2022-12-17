package com.example.e_commerce.services


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import com.example.e_commerce.R
import com.example.e_commerce.ui.login.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFireBaseMessagingService:FirebaseMessagingService(){

    override fun handleIntent(intent: Intent?) {
        //if you don't know the format of your FCM message,
        //just print it out, and you'll know how to parse it
        val bundle = intent!!.extras
        if (bundle != null) {
            for (key in bundle.keySet()) {
                val value = bundle[key]
                Log.d("FCM", "Key: $key Value: $value")
            }
        }

        super.handleIntent(intent)

        //remove the Notificaitons
        //removeFirebaseOriginalNotifications()

        if (bundle == null) return

        //pares the message
        val body = bundle["gcm.notification.body"] as String?
        var title: String? = ""
        if (bundle.containsKey("gcm.notification.title"))
            title = bundle["gcm.notification.title"] as String?
        Log.d("FCMMMM Third", " $body , $title")
       if (body != null && title != null) sendNotification(body,title)
    }

    private fun sendNotification(messageBody: String, title: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_MUTABLE
        )
        val channelId = "My channel ID"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_app_icon)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setStyle(NotificationCompat.BigTextStyle())
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                title,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }


    /*private fun removeFirebaseOriginalNotifications() {

        //check notificationManager is available
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            ?: return

        //check api level for getActiveNotifications()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //if your Build version is less than android 6.0
            //we can remove all notifications instead.
            //notificationManager.cancelAll();
            return
        }

        //check there are notifications
        val activeNotifications = notificationManager.activeNotifications ?: return

        //remove all notification created by library(super.handleIntent(intent))
        for (tmp in activeNotifications) {
            Log.d(
                "FCM StatusBarNo",
                "StatusBarNotification tag/id: " + tmp.tag + " / " + tmp.id
            )
            val tag = tmp.tag
            val id = tmp.id

            //trace the library source code, follow the rule to remove it.
            if (tag != null && tag.contains("FCM-Notification")) notificationManager.cancel(tag, id)
        }
    }*/
    override fun onMessageReceived(@NonNull remoteMessage:RemoteMessage){
        super.onMessageReceived(remoteMessage)
        /*title = remoteMessage.data["Title"].toString()
        message = remoteMessage.data["Message"].toString()
        val builder =NotificationCompat.Builder(applicationContext)
            .setSmallIcon(R.drawable.ic_app_icon)
            .setContentTitle(title)
            .setContentText(message)
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, builder.build())*/
    }
}