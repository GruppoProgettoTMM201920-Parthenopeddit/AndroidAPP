package it.uniparthenope.parthenopeddit.android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.chat.BaseViewHolder
import it.uniparthenope.parthenopeddit.android.ui.chat.MessageViewHolder
import it.uniparthenope.parthenopeddit.model.Message
import it.uniparthenope.parthenopeddit.model.Message.Companion.TYPE_FRIEND_MESSAGE
import it.uniparthenope.parthenopeddit.model.Message.Companion.TYPE_MY_MESSAGE

class ChatAdapter(var data: MutableList<Message>) : RecyclerView.Adapter<MessageViewHolder<*>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        TODO("Not yet implemented")
    }


    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            TYPE_MY_MESSAGE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.cardview_message_sent, parent, false)
                MyMessageViewHolder(view)
            }
            TYPE_FRIEND_MESSAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_message_received, parent, false)
                FriendMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = data[position]
        Log.d("adapter View", position.toString() + item.body)
        when (holder) {
            is MyMessageViewHolder -> holder.bind(item)
            is FriendMessageViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int = data[position].messageType

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

     */
}
