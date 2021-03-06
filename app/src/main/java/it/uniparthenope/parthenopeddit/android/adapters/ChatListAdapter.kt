package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.model.UsersChat
import it.uniparthenope.parthenopeddit.util.DateParser
import kotlinx.android.synthetic.main.cardview_chat.view.*
import kotlinx.android.synthetic.main.cardview_post.view.username_textview

class ChatListAdapter() : RecyclerView.Adapter<ChatListAdapter.ChatListViewHolder>() {

    private val chatList: ArrayList<UsersChat> = ArrayList()
    private var listener:ChatListItemClickListeners? = null

    fun aggiungiChat(chatItemList: List<UsersChat>) {
        this.chatList.addAll(chatItemList)
        notifyDataSetChanged()
    }

    fun setItemClickListener( listener:ChatListItemClickListeners? ) {
        this.listener = listener
    }

    interface ChatListItemClickListeners {
        fun onChatClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_chat,
            parent, false)

        return ChatListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        val currentItem = chatList[position]

        holder.user_imageview.setImageResource(R.drawable.default_user_image)

        val other_user = currentItem.other_user_chat!!.of_user!!
        holder.username_textview.text = other_user.display_name?:other_user.id
        holder.latest_message_textview.text = currentItem.last_message!!.body
        holder.date_textview.text = DateParser.prettyParse(currentItem.last_message!!.timestamp)

        if( listener != null ) {
            holder.user_imageview.setOnClickListener {
                //TODO: show user image
            }

            holder.username_textview.setOnClickListener {
            }

            holder.latest_message_textview.setOnClickListener {
            }

            holder.date_textview.setOnClickListener {
            }

            holder.relativelayout.setOnClickListener {
                listener!!.onChatClick(other_user)
            }
        }
    }

    override fun getItemCount() = chatList.size

    class ChatListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val user_imageview: ImageView = itemView.user_imageview
        val username_textview: TextView = itemView.username_textview
        val latest_message_textview: TextView = itemView.latest_message_textview
        val date_textview: TextView = itemView.date_textview
        val relativelayout: RelativeLayout = itemView.chat_relativelayout
    }
}