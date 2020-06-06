package it.uniparthenope.parthenopeddit.android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.AddMemberActivity
import it.uniparthenope.parthenopeddit.model.User
import kotlinx.android.synthetic.main.cardview_user.view.*

class AddMemberAdapter() : RecyclerView.Adapter<AddMemberAdapter.AddMemberAdapterViewHolder>() {

    private val userList: ArrayList<User> = ArrayList()
    private var listener: UserListItemClickListeners? = null

    fun addUser(userList: List<User>) {
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    fun setItemClickListener(listener: AddMemberActivity) {
        this.listener = listener
    }

    interface UserListItemClickListeners {
        fun onUserClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMemberAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_user,
            parent, false
        )

        return AddMemberAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AddMemberAdapterViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.user_imageview.setImageResource(R.drawable.default_user_image)
        holder.username_textview.text = currentItem.display_name

        if (listener != null) {
            holder.user_imageview.setOnClickListener {
                //TODO: show user image
            }

            holder.username_textview.setOnClickListener {
            }

            holder.user_list_relativelayout.setOnClickListener {
                listener!!.onUserClick(currentItem)
            }
        }
    }

    override fun getItemCount() = userList.size

    class AddMemberAdapterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val user_imageview: ImageView = itemView.user_imageview
        val username_textview: TextView = itemView.username_textview
        val user_list_relativelayout: RelativeLayout = itemView.user_list_relativelayout
    }
}