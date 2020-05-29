package it.uniparthenope.parthenopeddit.android.ui.chat

import android.view.View
import android.widget.TextView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Message

class MessageViewHolders {

    class MyMessageViewHolder(val view: View) : MessageViewHolder<Message>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: Message) {
            messageContent.text = item.body
        }


    }
    class FriendMessageViewHolder(val view: View) : MessageViewHolder<Message>(view) {
        private val messageContent = view.findViewById<TextView>(R.id.message)

        override fun bind(item: Message) {
            messageContent.text = item.body
        }


    }
    
}