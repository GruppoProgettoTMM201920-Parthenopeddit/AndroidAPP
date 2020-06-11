package it.uniparthenope.parthenopeddit.android.ui.group

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.AddMemberActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.android.UserProfileActivity
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableUserListAdapter
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupInvite
import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.android.swipe.SwipeItemTouchHelper
import it.uniparthenope.parthenopeddit.android.swipe.SwipeItemTouchListener

class BackdropFragment(): SwipeItemTouchListener, Fragment(),
    ExpandableUserListAdapter.UserClickListener {
    private lateinit var group: Group
    private lateinit var members: ArrayList<GroupMember>
    private lateinit var invites: ArrayList<GroupInvite>

    private var admin_arraylist: ArrayList<GroupMember> = ArrayList()
    private var user_arraylist: ArrayList<GroupMember> = ArrayList()

    private lateinit var backdrop_group_name_textview: TextView
    private lateinit var creation_date_textview: TextView
    private lateinit var auth: AuthManager
    private var isUserAdmin: Boolean = false

    private lateinit var membersAdapter: ExpandableUserListAdapter
    private lateinit var members_recycler_view: RecyclerView
    private lateinit var add_member_button: Button

    companion object {
        private const val MAX_IMAGE_SIZE = 200
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_backdrop, container, false)
        backdrop_group_name_textview = root.findViewById<TextView>(R.id.backdrop_group_name_textview)
        creation_date_textview = root.findViewById<TextView>(R.id.creation_date_textview)
        membersAdapter = ExpandableUserListAdapter(requireContext(), Glide.with(this), this)

        members_recycler_view = root.findViewById(R.id.members_recycler_view)
        members_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        members_recycler_view.adapter = membersAdapter

        val swipeHelper =
            SwipeItemTouchHelper(
                0,
                ItemTouchHelper.LEFT,
                this
            )
        ItemTouchHelper(swipeHelper).attachToRecyclerView(members_recycler_view)

        auth = (activity as BasicActivity).app.auth

        add_member_button = root.findViewById(R.id.add_member_button)
        add_member_button.setOnClickListener {
            if( admin_arraylist.filter{ it.user_id == auth.username!! }.singleOrNull()?.is_owner!! ){
                val intent = Intent(requireContext(), AddMemberActivity::class.java)
                intent.putExtra("id_group", group.id)
                intent.putExtra("name_group",group.name)
                startActivity(intent)
            } else{
                Toast.makeText(requireContext(), "Solo gli amministratori possono aggiungere membri", Toast.LENGTH_LONG).show()
            }
        }
        return root
    }

    /**
     * get data example.
     * actually, this function should be modified to do some network requests like Retrofit 2.0.
     * after that, add Items to List using builder pattern.
     */
    private fun setMembersRecyclerView() : List<ExpandableUserListAdapter.Item> {
        val ret = ArrayList<ExpandableUserListAdapter.Item>()

        if(this::members.isInitialized) {
            admin_arraylist = members.filter { it.is_owner } as ArrayList<GroupMember>
            user_arraylist = members.filter { !it.is_owner } as ArrayList<GroupMember>

            isUserAdmin = (admin_arraylist.singleOrNull { it.user_id == auth.username } != null)
        }

        val admin_header = ExpandableUserListAdapter.Item.Builder()
            .type(ExpandableUserListAdapter.HEADER)
            .title("Amministratori")
            .num( admin_arraylist.size.toString() )
            .build()

        val member_header = ExpandableUserListAdapter.Item.Builder()
            .type(ExpandableUserListAdapter.HEADER)
            .title("Membri")
            .num( if(user_arraylist.isNotEmpty()) user_arraylist.size.toString() else "0" )
            .build()

        val invite_header = ExpandableUserListAdapter.Item.Builder()
            .type(ExpandableUserListAdapter.HEADER)
            .title("Invitati")
            .num( if(this::invites.isInitialized && invites.isNotEmpty()) invites.size.toString() else "0" )
            .build()

        ret.add(admin_header)
        for(member:GroupMember in admin_arraylist) {
            val content = ExpandableUserListAdapter.Item.Builder()
                .type(ExpandableUserListAdapter.CONTENT)
                .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                .username(member.user!!.display_name?:member.user_id)
                .dateMessage("Entrato il : ")
                .date(member.join_date)
                .build()

            ret.add(content)
        }

        ret.add(member_header)
        if(user_arraylist.isNotEmpty()) {
            for(member:GroupMember in user_arraylist) {
                val content = ExpandableUserListAdapter.Item.Builder()
                    .type(ExpandableUserListAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(member.user!!.display_name?:member.user_id)
                    .dateMessage("Entrato : ")
                    .date(member.join_date)
                    .build()

                ret.add(content)
            }
        }

        ret.add(invite_header)
        if(this::invites.isInitialized && invites.isNotEmpty()) {
            for(invite:GroupInvite in invites) {
                val content = ExpandableUserListAdapter.Item.Builder()
                    .type(ExpandableUserListAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(invite.invited?.display_name?:invite.invited_id)
                    .dateMessage("Invitato : ")
                    .date(invite.timestamp)
                    .build()

                ret.add(content)
            }
        }

        return ret
    }

    private fun generateRandomImageUrl(max: Int) : String = resources.getString(R.string.random_image_url, (1..max).shuffled().first())

    // SwipeItemTouchListener override area

    fun remove_member(member: GroupMember, swipedIndex: Int, removedItem: ExpandableUserListAdapter.Item) {
        val snackBar = Snackbar.make(members_recycler_view, resources.getString(R.string.member_removed, removedItem.username), Snackbar.LENGTH_LONG)

        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        snackBar.setAction("Undo") {
            membersAdapter.add(swipedIndex, removedItem)
        }

        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(snackbar: Snackbar, event: Int) {
                if (event == DISMISS_EVENT_TIMEOUT) {
                    // Snackbar closed on its own
                    GroupsRequests(requireContext(), auth).removeFromGroup(
                        group.id,
                        member.user_id,
                        {}, {
                            membersAdapter.add(swipedIndex, removedItem)
                        }
                    )
                }
            }
            override fun onShown(snackbar: Snackbar) {}
        })

        snackBar.show()
    }

    fun remove_invite(invite: GroupInvite, swipedIndex: Int, removedItem: ExpandableUserListAdapter.Item) {
        val snackBar = Snackbar.make(members_recycler_view, resources.getString(R.string.invite_removed, removedItem.username), Snackbar.LENGTH_LONG)

        snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.red))
        snackBar.setAction("Undo") {
            membersAdapter.add(swipedIndex, removedItem)
        }

        snackBar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(snackbar: Snackbar, event: Int) {
                if (event == DISMISS_EVENT_TIMEOUT) {
                    // Snackbar closed on its own
                    GroupsRequests(requireContext(), auth).undoInvite(
                        group.id,
                        invite.invited_id,
                        {}, {
                            membersAdapter.add(swipedIndex, removedItem)
                        }
                    )
                }
            }
            override fun onShown(snackbar: Snackbar) {}
        })

        snackBar.show()
    }

    fun leave_group(me: GroupMember) {
        GroupsRequests(requireContext(), auth).leaveGroup(
            group.id, {
                Toast.makeText(requireContext(),"Hai lasciato il gruppo ${group.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            },{
                Toast.makeText(requireContext(),"Eri l'ultimo admin. Hai abbandonato la nave, sei peggio di Schettino", Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            },{
                Toast.makeText(requireContext(),"Eri l'ultimo utente. Il gruppo ${group.name} è stato eliminato", Toast.LENGTH_LONG).show()
                val intent = Intent(requireContext(), HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }, {
                Toast.makeText(requireContext(),"Errore : ${it}", Toast.LENGTH_LONG).show()
            }
        )
    }

    override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {

        if(!isUserAdmin) {
            Toast.makeText(requireContext(), "Non puoi se non sei amministratore", Toast.LENGTH_LONG).show()
            return
        }

        if(holder is ExpandableUserListAdapter.ContentViewHolder) {
            val swipedIndex = holder.adapterPosition
            val swipedItem = membersAdapter.getItem(swipedIndex)
            if( swipedItem.type != ExpandableUserListAdapter.CONTENT ) return

            val removedItem = membersAdapter.remove(swipedIndex)

            val admin = admin_arraylist.singleOrNull { member:GroupMember -> removedItem.username == member.user_id }
            val member = user_arraylist.singleOrNull { member:GroupMember -> removedItem.username == member.user_id }
            val invite = invites.singleOrNull { invite:GroupInvite -> removedItem.username == invite.invited_id }

            if(member != null) {
                remove_member(member, swipedIndex, removedItem)
            } else if (invite != null) {
                remove_invite(invite, swipedIndex, removedItem)
            } else if (admin != null){
                if(admin.user_id == auth.username) {
                    leave_group(admin)
                } else {
                    Toast.makeText(requireContext(), "Non puoi cacciare un altro amministratore", Toast.LENGTH_LONG).show()
                    membersAdapter.add(swipedIndex, removedItem)
                }
            }
        }
    }

    override fun onSelectedChanged(holder: RecyclerView.ViewHolder?, actionState: Int, uiUtil: ItemTouchUIUtil) {
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                if(holder is ExpandableUserListAdapter.ContentViewHolder) {
                    uiUtil.onSelected(holder.container)
                }
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        holder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    ) {
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                if(holder is ExpandableUserListAdapter.ContentViewHolder) {
                    uiUtil.onDraw(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        holder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
        uiUtil: ItemTouchUIUtil
    ) {
        when(actionState) {
            ItemTouchHelper.ACTION_STATE_SWIPE -> {
                if(holder is ExpandableUserListAdapter.ContentViewHolder) {
                    uiUtil.onDrawOver(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, uiUtil: ItemTouchUIUtil) {
        if(holder is ExpandableUserListAdapter.ContentViewHolder) {
            uiUtil.clearView(holder.container)
        }
    }

    fun updateGroupData(group: Group){
        this.group = group
        backdrop_group_name_textview.text = group.name
        creation_date_textview.text = group.created_on
    }

    fun updateMembersData(members: ArrayList<GroupMember>){
        this.members = members

        membersAdapter.setData(setMembersRecyclerView())
    }

    fun updateInvitesData(invites: ArrayList<GroupInvite>){
        this.invites = invites

        membersAdapter.setData(setMembersRecyclerView())
    }

    override fun onUserClicked(userId: String) {
        val intent = Intent(requireContext(), UserProfileActivity::class.java)
        intent.putExtra("id_user", userId)
        startActivity(intent)
    }
}
