package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Chat
import kotlinx.android.synthetic.main.cardview_chat.view.*

class ChatListAdapter : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    private val chatItemList: ArrayList<Chat> = ArrayList<Chat>()

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val user_imageview = itemView.user_imageview
        val username_textview = itemView.username_textview
        val last_message_textview = itemView.last_message_textview
        val date_textview = itemView.date_textview
    }

    fun aggiornaLista(chatItemList: List<Chat>) {
        this.chatItemList.addAll(chatItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_chat,
            parent, false
        )

        return ChatViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.chatItemList.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatItemList[position]
        holder.username_textview.text = chat.username
        holder.last_message_textview.text = chat.last_message
        holder.date_textview.text = chat.date
    }
}