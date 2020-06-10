package it.uniparthenope.parthenopeddit.android.ui.messages

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ChatActivity
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableListChatAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableUserListAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
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


    private lateinit var adapter: ExpandableListChatAdapter

    private lateinit var recyclerview_latest_messages: RecyclerView
    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var authManager: AuthManager
    //val adapter = GroupAdapter<GroupieViewHolder>()

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
        /*recyclerview_latest_messages.adapter = chatListAdapter
        recyclerview_latest_messages.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_latest_messages.setHasFixedSize(true)*/


        recyclerview_latest_messages.adapter = adapter

        authManager = (activity as BasicActivity).app.auth

        var chatList: ArrayList<UsersChat> = ArrayList<UsersChat>()
        var groupChatList: ArrayList<GroupChat> = ArrayList<GroupChat>()

        MockApiData().getChat( authManager.token!!, 1) { chatItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                chatItemList!!

                chatList = chatItemList!!
            }
        }

        MockApiData().getGroupChat( authManager.token!!, 1) { chatItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                chatItemList!!

                groupChatList = chatItemList!!
            }
        }

        adapter.setData(getData(chatList!!, groupChatList!!))

            return root
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

        //GRUPPI
        /*
        for(j in 0..(admin_arraylist.size!!-1)) {
            val content = ExpandableSwipeAdapter.Item.Builder()
                .type(ExpandableSwipeAdapter.CONTENT)
                .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                .username(admin_arraylist?.get(j)?.user?.display_name!!)
                .joindate(admin_arraylist?.get(j)?.join_date!!)
                .build()

            ret.add(content)
        }
        */

        if(userChatItemList.isNotEmpty()) {
            for (j in 0..(userChatItemList.size!! - 1)) {
                val content = ExpandableListChatAdapter.Item.Builder()
                    .type(ExpandableUserListAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(userChatItemList?.get(j)?.of_user?.display_name!!)
                    .latest_message(userChatItemList?.get(j)?.last_message?.body?:"")
                    .date("4:20")
                    .user(userChatItemList?.get(j)?.last_message?.sender_user)
                    .build()

                Log.d("DEBUG", "user has id ${userChatItemList?.get(j)?.last_message?.sender_id}")

                ret.add(content)
            }
        }


        ret.add(groupchat_header)
        
        if(groupChatItemList.isNotEmpty()) {
            for (j in 0..(groupChatItemList.size!! - 1)) {
                val content = ExpandableListChatAdapter.Item.Builder()
                    .type(ExpandableUserListAdapter.GROUP_CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(groupChatItemList?.get(j)?.of_group!!.name)
                    .group_user_latest(groupChatItemList?.get(j)?.last_message?.sender_user?.display_name)
                    .latest_message(groupChatItemList?.get(j)?.last_message?.body?:"")
                    .date("4:20")
                    .group(groupChatItemList?.get(j)?.of_group)
                    .build()


                ret.add(content)

            }
        }


        Log.d("DEBUG", "group has id ${groupChatItemList?.get(0)?.of_group?.id}")
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