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
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ChatActivity
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableListChatAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableSwipeAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.model.UsersChat
import it.uniparthenope.parthenopeddit.util.SwipeItemTouchListener

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



        MockApiData().getChat( Auth().token, 1) { chatItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                chatItemList!!
                adapter.setData(getData(chatItemList!!))
                //TODO: getData for GroupChat

                //chatListAdapter.aggiungiChat(chatItemList)
            }
        }

        return root
    }

    override fun onChatClick(user: User) {
        val intent = Intent(requireContext(), ChatActivity()::class.java)
        intent.putExtra("user", user.toJSON())
        startActivity(intent)
    }

    private fun getData(chatItemList: ArrayList<UsersChat>) : List<ExpandableListChatAdapter.Item> {
        val ret = ArrayList<ExpandableListChatAdapter.Item>()

        val userchat_header = ExpandableListChatAdapter.Item.Builder()
            .type(ExpandableSwipeAdapter.HEADER)
            .title("Utenti")
            .num(chatItemList.size.toString())
            .build()


        val groupchat_header = ExpandableListChatAdapter.Item.Builder()
            .type(ExpandableSwipeAdapter.HEADER)
            .title("Gruppi")
            //.num( if(user_arraylist.isNotEmpty()) user_arraylist.size.toString() else "0" )
            .num("0")
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

        if(chatItemList.isNotEmpty()) {
            for (j in 0..(chatItemList.size!! - 1)) {
                val content = ExpandableListChatAdapter.Item.Builder()
                    .type(ExpandableSwipeAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(chatItemList?.get(j)?.other_user?.display_name!!)
                    .latestmessage(chatItemList?.get(j)?.latest_message)
                    .date("4:20")
                    .user(chatItemList?.get(j)?.other_user)
                    .build()

                Log.d("DEBUG", "user has id ${chatItemList?.get(j)?.other_user.id}")

                ret.add(content)
            }
        }


        ret.add(groupchat_header)




        return ret
    }

    private fun generateRandomString(length: Int) : String = (1..length)
        .map { kotlin.random.Random.nextInt(0, charList.size) }
        .map(charList::get)
        .joinToString("")

    private fun generateRandomImageUrl(max: Int) : String = resources.getString(R.string.random_image_url, (1..max).shuffled().first())

    // SwipeItemTouchListener override area

    override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {

        if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
            val swipedIndex = holder.adapterPosition
            val removedItem = adapter.remove(swipedIndex)

            val snackBar = Snackbar.make(recyclerview_latest_messages, resources.getString(R.string.remove, removedItem.title), Snackbar.LENGTH_LONG)
            snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            if(removedItem.type == ExpandableSwipeAdapter.CONTENT) {

                snackBar.setAction("Undo") {
                    adapter.add(swipedIndex, removedItem)
                }
            } else if(removedItem.type == ExpandableSwipeAdapter.HEADER) {
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
                if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
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
                if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
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
                if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                    uiUtil.onDrawOver(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }
    }

    override fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, uiUtil: ItemTouchUIUtil) {
        if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
            uiUtil.clearView(holder.container)
        }
    }
}