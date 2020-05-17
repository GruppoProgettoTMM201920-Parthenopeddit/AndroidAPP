package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.messages.MessagesViewModel
import it.uniparthenope.parthenopeddit.model.Chat
import kotlinx.android.synthetic.main.cardview_chat.view.*

class MessagesAdapter(private var exampleList: List<Chat>, private var listener: MessagesItemClickListeners) : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

    interface MessagesItemClickListeners {
        fun onClickChat(id_user:Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_post,
            parent, false)

        return MessagesViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.user_imageview.setImageResource(R.drawable.ic_launcher_background)
        //holder.user_imageview.foreground(R.drawable.circle_user_image)        TODO: add foreground after user image update
        holder.username_textview.text = currentItem.username
        holder.last_message_textview.text = currentItem.last_message
        holder.date_textview.text = currentItem.date
    }

    override fun getItemCount(): Int = exampleList.size

    class MessagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val user_imageview: ImageView = itemView.user_imageview
        val username_textview: TextView = itemView.username_textview
        val last_message_textview: TextView = itemView.last_message_textview
        val date_textview: TextView = itemView.date_textview

    }
}