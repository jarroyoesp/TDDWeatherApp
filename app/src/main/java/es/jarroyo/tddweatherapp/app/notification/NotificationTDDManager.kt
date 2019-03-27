package es.jarroyo.tddweatherapp.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import es.jarroyo.tddweatherapp.BuildConfig
import es.jarroyo.tddweatherapp.R
import es.jarroyo.tddweatherapp.ui.App

class NotificationTDDManager(private val app: App) {

    /**
     * NOTIFICATION WORKER
     */
    fun showNotificationWorker(context: Context?, id: String, notificationTitle: String, notificationSubtitle: String, intent: Intent) {

        val pendingIntent = PendingIntent.getActivity(context, id.hashCode(), intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = BuildConfig.NOTIFICATION_CHANNEL_ALARMS_ID
        //val defaultSoundUri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/raw/notification_sound")

        val notificationBuilder = NotificationCompat.Builder(context!!, channelId)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(notificationTitle)
                .setContentText(notificationSubtitle)
                .setAutoCancel(true)
                //.setSound(defaultSoundUri)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    BuildConfig.NOTIFICATION_CHANNEL_ALARMS_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT)

            val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            //channel.setSound(defaultSoundUri, audioAttributes)


            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id.hashCode(), notificationBuilder.build())
    }


    companion object {
        val ID_NOTIFICATION_ALARM = 1001
        val ID_NOTIFICATION_ABSENCE = 1002
    }
}