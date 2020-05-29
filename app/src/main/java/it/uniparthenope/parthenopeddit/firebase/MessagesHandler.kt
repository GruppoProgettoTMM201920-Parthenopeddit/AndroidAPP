package it.uniparthenope.parthenopeddit.firebase

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import com.google.firebase.messaging.RemoteMessage

class MessagesHandler {
    companion object {
        val NOTIFICATION_TYPE_LABEL: String = "NOTIFICATION_TYPE"
        val NOTIFICATION_ID_LABEL: String = "NOTIFICATION_ID"

        fun handleMessage(ctx: Context, remoteMessage: RemoteMessage) {
            val type = NotificationType.valueOf(remoteMessage.data[NOTIFICATION_TYPE_LABEL]?:return)
            val id = (remoteMessage.data[NOTIFICATION_ID_LABEL]?:return).toInt()

            val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notification: Notification = type.maker.make(ctx, notificationManager, remoteMessage)
            notificationManager.notify(id, notification)

            return
        }

        enum class NotificationType(val maker: NotificationMaker) {
            TEST(TestingNotificationMaker())
        }
    }

    interface NotificationMaker {
        fun make( ctx: Context,
                  notificationManager: NotificationManager,
                  remoteMessage: RemoteMessage ) : Notification
    }
}

