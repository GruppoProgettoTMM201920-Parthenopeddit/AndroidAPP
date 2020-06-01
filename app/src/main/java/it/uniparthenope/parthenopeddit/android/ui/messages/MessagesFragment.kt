package it.uniparthenope.parthenopeddit.android.ui.messages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ChatActivity
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.User

class MessagesFragment : Fragment(), ChatListAdapter.ChatListItemClickListeners {

    companion object {
        var currentUser: User? = null
    }

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
        recyclerview_latest_messages = root.findViewById(R.id.recyclerview_latest_messages)
        recyclerview_latest_messages.adapter = chatListAdapter
        recyclerview_latest_messages.layoutManager = LinearLayoutManager(requireContext())
        recyclerview_latest_messages.setHasFixedSize(true)

        MockApiData().getChat( Auth().token, 1) { chatItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                chatItemList!!

                chatListAdapter.aggiungiChat(chatItemList)
            }
        }

        return root
    }

    override fun onChatClick(user: User) {
        val intent = Intent(requireContext(), ChatActivity()::class.java)
        var myUser: String = ""
       /* try{
            myUser = user.toJSON()
        } catch(e: Exception){
            Log.d("DEBUG", user.toString())
        }
        intent.putExtra("user", myUser)
        */
        Log.d("DEBUG", user.toString())
        startActivity(intent)
    }


}