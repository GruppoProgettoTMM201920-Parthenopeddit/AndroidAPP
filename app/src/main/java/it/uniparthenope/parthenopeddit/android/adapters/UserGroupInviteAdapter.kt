package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.UserGroupInviteActivity
import it.uniparthenope.parthenopeddit.model.GroupInvite
import kotlinx.android.synthetic.main.cardview_usergroup_invite.view.*

class UserGroupInviteAdapter : RecyclerView.Adapter<UserGroupInviteAdapter.UserGroupInviteViewHolder>() {

    private val InviteList: ArrayList<GroupInvite> = ArrayList()
    private var listener:UserGroupInviteItemClickListeners? = null

    fun setItemClickListener(listener: UserGroupInviteActivity) {
        this.listener = listener
    }

    interface UserGroupInviteItemClickListeners {
        fun onInviteClick(group_id: Int?)
    }

    fun addInvite(GroupInviteItemList: List<GroupInvite>) {
        this.InviteList.addAll(GroupInviteItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupInviteViewHolder  {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_usergroup_invite,
            parent, false)

        return UserGroupInviteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserGroupInviteViewHolder, position: Int) {
        val currentItem = InviteList[position]

        holder.group_name_textview.text = currentItem.group?.name
        holder.invited_by_textview.text = currentItem.inviter?.display_name




        if( listener != null ) {

            holder.relativeLayout.setOnClickListener {
                listener!!.onInviteClick(currentItem.group_id)
            }
        }
    }

    override fun getItemCount() = InviteList.size

    class UserGroupInviteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val group_name_textview: TextView = itemView.group_name_textview
        val invited_by_textview: TextView = itemView.invited_by_textview
        val relativeLayout: RelativeLayout = itemView.invite_relativelayout

    }
}