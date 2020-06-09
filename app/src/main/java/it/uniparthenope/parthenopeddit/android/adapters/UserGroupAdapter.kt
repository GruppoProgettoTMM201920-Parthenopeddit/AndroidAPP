package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.user_boards.group.GroupUserBoardFragment
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupMember
import kotlinx.android.synthetic.main.cardview_group.view.*

class UserGroupAdapter : RecyclerView.Adapter<UserGroupAdapter.UserGroupViewHolder>() {

    private val GroupList: ArrayList<GroupMember> = ArrayList()
    private var listener:UserGroupItemClickListeners? = null

    fun setItemClickListener(listener: GroupUserBoardFragment) {
        this.listener = listener
    }

    interface UserGroupItemClickListeners {
        fun onBoardClick(board_id: Int?)
    }

    fun addGroup(GroupItemList: List<GroupMember>) {
        this.GroupList.addAll(GroupItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGroupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_group,
            parent, false)

        return UserGroupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserGroupViewHolder, position: Int) {
        val currentItem = GroupList[position]

        holder.group_name_textview.text = currentItem.group?.name




        if( listener != null ) {

            holder.relativeLayout.setOnClickListener {
                listener!!.onBoardClick(currentItem.group_id)
            }
        }
    }

    override fun getItemCount() = GroupList.size

    class UserGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val group_name_textview: TextView = itemView.group_name_textview
        val relativeLayout: RelativeLayout = itemView.group_cardview_relativelayout

    }
}