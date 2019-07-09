package com.mahesaiqbal.mynotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.NotificationCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager
    lateinit var builder: NotificationCompat.Builder

    companion object {
        val NOTIFICATION_ID = 1
        var CHANNEL_ID = "channel_01"
        var CHANNEL_NAME = "mahesa channel"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_send_notif.setOnClickListener { v -> sendNotification() }
    }

    val runnable = Runnable { notificationManager.notify(NOTIFICATION_ID, builder.build()) }

    fun sendNotification() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://dicoding.com"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications_white)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_notifications_white))
            .setContentTitle(resources.getString(R.string.content_title))
            .setContentText(resources.getString(R.string.content_text))
            .setSubText(resources.getString(R.string.subtext))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
            }
        }

        val notification: Notification = builder.build()

        if (notificationManager != null) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }

        Handler().postDelayed(runnable, 5000)
    }
}
