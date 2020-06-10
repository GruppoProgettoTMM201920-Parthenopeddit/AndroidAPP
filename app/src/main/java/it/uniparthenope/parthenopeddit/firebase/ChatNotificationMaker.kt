package it.uniparthenope.parthenopeddit.firebase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ChatActivity
import it.uniparthenope.parthenopeddit.model.UsersChat
import it.uniparthenope.parthenopeddit.util.toObject

class ChatNotificationMaker : MessagesHandler.NotificationMaker {
    override fun make(
        ctx: Context,
        notificationManager: NotificationManager,
        remoteMessage: RemoteMessage
    ) : Notification {
        val intent = Intent(ctx, ChatActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(ctx, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = ctx.getString(R.string.userchat_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val obj: UsersChat = remoteMessage.data["OBJECT"]?.toObject()?:throw RuntimeException()
        val user = obj.other_user_chat!!.of_user!!
        val body = obj.last_message!!.body

        intent.putExtra("user",user.toJSON())

        val notificationBuilder = NotificationCompat.Builder(ctx, channelId)
            .setSmallIcon(R.drawable.parthenopeddit_logo_transparent)
            .setContentTitle(user.display_name?:user.id)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Testing notification channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        return notificationBuilder.build()
    }
}