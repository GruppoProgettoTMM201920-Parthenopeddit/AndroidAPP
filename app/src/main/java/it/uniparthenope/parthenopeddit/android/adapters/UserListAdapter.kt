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
import kotlinx.android.synthetic.main.cardview_user.view.*

class UserListAdapter() : RecyclerView.Adapter<UserListAdapter.AddMemberAdapterViewHolder>() {

    private val userList: ArrayList<User> = ArrayList()
    private var listener: UserListItemClickListeners? = null

    fun addUser(userList: List<User>) {
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    fun setUserList(userList: List<User>) {
        this.userList.clear()
        this.userList.addAll(userList)
        notifyDataSetChanged()
    }

    fun removeUser(user: User) {
        val i = userList.indexOf(user)
        userList.remove(user)
        notifyItemRemoved(i)
    }

    fun setItemClickListener(listener: UserListItemClickListeners) {
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
        holder.username_textview.text = currentItem.display_name?:currentItem.id

        if (listener != null) {
            holder.user_imageview.setOnClickListener {
                //TODO: show user image
            }

            holder.username_textview.setOnClickListener {
                listener!!.onUserClick(currentItem)
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