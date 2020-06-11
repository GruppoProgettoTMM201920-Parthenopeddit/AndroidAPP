package it.uniparthenope.parthenopeddit.android.ui.messages

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ChatActivity
import it.uniparthenope.parthenopeddit.android.NewChatActivity
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableListChatAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableUserListAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.requests.MessagesRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.GroupChat
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.model.UsersChat
import it.uniparthenope.parthenopeddit.android.swipe.SwipeItemTouchListener

class MessagesFragment : Fragment(), ChatListAdapter.ChatListItemClickListeners,
    SwipeItemTouchListener, ExpandableListChatAdapter.ChatListItemClickListeners {

    companion object {
        var currentUser: User? = null

        private const val HEADER_ITEM_COUNT = 5
        private const val CONTENT_ITEM_COUNT = 5
        private const val RANDOM_STRING_LENGTH = 10
        private const val MAX_IMAGE_SIZE = 200
    }

    private val charList : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    var isOpen = false

    private lateinit var adapter: ExpandableListChatAdapter

    private lateinit var recyclerview_latest_messages: RecyclerView
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var authManager: AuthManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        messagesViewModel =
            ViewModelProviders.of(this).get(MessagesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_messages, container, false)
        val chatListAdapter = ChatListAdapter()
        chatListAdapter.setItemClickListener(this)
        adapter = ExpandableListChatAdapter(requireContext(), Glide.with(this))
        adapter.setItemClickListener(this)

        recyclerview_latest_messages = root.findViewById(R.id.recyclerview_latest_messages)
        recyclerview_latest_messages.layoutManager = LinearLayoutManager(requireContext())

        val fab = root.findViewById(R.id.fab) as FloatingActionButton
        val fab_new_chat = root.findViewById(R.id.fab_new_chat) as FloatingActionButton
        val fab_new_chat_textview = root.findViewById(R.id.fab_new_chat_textview) as TextView
        val rotateClockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anticlockwise)

        recyclerview_latest_messages.adapter = adapter

        authManager = (activity as BasicActivity).app.auth

        var chatList: ArrayList<UsersChat> = ArrayList<UsersChat>()
        var groupChatList: ArrayList<GroupChat> = ArrayList<GroupChat>()

        MessagesRequests(requireContext(), authManager).getOpenChats({it: ArrayList<UsersChat> ->
            chatList = it
            adapter.setData(getData(chatList, groupChatList))
        },{it: String ->
            Toast.makeText(requireContext(),"Errore : $it", Toast.LENGTH_LONG).show()
        })

        fab.setOnClickListener{
            if(isOpen){
                fab.startAnimation(rotateClockwise)

                fab_new_chat.animate().translationY(200F)
                fab_new_chat_textview.animate().translationY(200F)
                fab_new_chat_textview.animate().alpha(0F)
                fab_new_chat_textview.visibility = View.GONE

                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)

                fab_new_chat.animate().translationY(-200F)
                fab_new_chat_textview.animate().translationY(-200F)
                fab_new_chat_textview.visibility = View.VISIBLE
                fab_new_chat_textview.animate().alpha(1F)

                isOpen = true
            }
        }

        fab_new_chat.setOnClickListener{ onClickNewChat() }
        fab_new_chat_textview.setOnClickListener{ onClickNewChat() }

        return root
    }

    fun onClickNewChat(){
        val intent = Intent(requireContext(), NewChatActivity()::class.java)
        startActivity(intent)
    }

    override fun onChatClick(user: User) {
        val intent = Intent(requireContext(), ChatActivity()::class.java)
        intent.putExtra("user", user.toJSON())
        startActivity(intent)
    }

    private fun getData(userChatItemList: ArrayList<UsersChat>, groupChatItemList: ArrayList<GroupChat>) : List<ExpandableListChatAdapter.Item> {
        val ret = ArrayList<ExpandableListChatAdapter.Item>()

        val userchat_header = ExpandableListChatAdapter.Item.Builder()
            .type(ExpandableUserListAdapter.HEADER)
            .title("Utenti")
            .num(userChatItemList.size.toString())
            .build()


        val groupchat_header = ExpandableListChatAdapter.Item.Builder()
            .type(ExpandableUserListAdapter.HEADER)
            .title("Gruppi")
            .num( if(groupChatItemList.isNotEmpty()) groupChatItemList.size.toString() else "0" )
            .build()

        ret.add(userchat_header)

        if(userChatItemList.isNotEmpty()) {
            for (userchat in userChatItemList) {
                var user: User = userchat.other_user_chat!!.of_user!!
                var username = user.display_name?:user.id
                val content = ExpandableListChatAdapter.Item.Builder()
                    .type(ExpandableUserListAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(username)
                    .latest_message(userchat.last_message?.body?:"")
                    .date(userchat.last_opened_on)
                    .user(user)
                    .build()

                ret.add(content)
            }
        }


        ret.add(groupchat_header)
        
        if(groupChatItemList.isNotEmpty()) {
            for (groupchat in groupChatItemList) {
                val content = ExpandableListChatAdapter.Item.Builder()
                    .type(ExpandableUserListAdapter.GROUP_CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(groupchat.of_group!!.name)
                    .group_user_latest(groupchat.last_message?.sender_user?.display_name)
                    .latest_message(groupchat.last_message?.body?:"")
                    .date("4:20")
                    .group(groupchat.of_group)
                    .build()


                ret.add(content)

            }
        }

        return ret
    }

    private fun generateRandomString(length: Int) : String = (1..length)
        .map { kotlin.random.Random.nextInt(0, charList.size) }
        .map(charList::get)
        .joinToString("")

    private fun generateRandomImageUrl(max: Int) : String = resources.getString(R.string.random_image_url, (1..max).shuffled().first())

    // SwipeItemTouchListener override area

    override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {

        if(holder is ExpandableUserListAdapter.ContentViewHolder) {
            val swipedIndex = holder.adapterPosition
            val removedItem = adapter.remove(swipedIndex)

            val snackBar = Snackbar.make(recyclerview_latest_messages, resources.getString(R.string.remove, removedItem.title), Snackbar.LENGTH_LONG)
            snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            if(removedItem.type == ExpandableUserListAdapter.CONTENT) {

                snackBar.setAction("Undo") {
                    adapter.add(swipedIndex, removedItem)
                }
            } else if(removedItem.type == ExpandableUserListAdapter.HEADER) {
                snackBar.setText(resources.getString(R.string.header, removedItem.title))
                snackBar.setAction("XD") {
                    snackBar.dismiss()
                }
            }

            snackBar.show()
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
}