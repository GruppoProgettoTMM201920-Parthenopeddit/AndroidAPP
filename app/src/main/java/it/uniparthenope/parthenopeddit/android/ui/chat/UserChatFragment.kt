package it.uniparthenope.parthenopeddit.android.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Message
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.model.UsersChat
import kotlinx.android.synthetic.main.cardview_message_received.view.*
import kotlinx.android.synthetic.main.chat_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class UserChatFragment(private val user: User) : Fragment() {

    companion object {
        fun newInstance(user: User) = UserChatFragment(user)
    }

    private lateinit var auth: AuthManager
    private lateinit var viewModel: UserChatViewModel
    private lateinit var recyclerview_chat_log: RecyclerView
    private lateinit var myMessageList : ArrayList<Message>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.chat_fragment, container, false)
        val adapter = GroupAdapter<GroupieViewHolder>()
        var send_button_chat_log = view.findViewById<ImageButton>(R.id.send_button_chat_log)

        auth = (activity as BasicActivity).app.auth
/*
        MockApiData().getChatMessages( auth.token!!, "user1", user.id) { userChat: UsersChat, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                myMessageList = userChat.chat_log!!

                for(message in myMessageList){                     //PER OGNI MESSAGGIO RICEVUTO
                    if(message.sender_id==auth.username){                              //CONTROLLA SE E' IL TUO
                        adapter.add(ChatToItem(message))
                    } else{                                             //ALTRIMENTI E' DELL'ALTRO UTENTE
                        adapter.add(ChatFromItem(message))
                    }

                }
            }
        }
        */

        recyclerview_chat_log = view.findViewById(R.id.recyclerview_chat_log) as RecyclerView

        recyclerview_chat_log.adapter = adapter
        recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)

/*
        send_button_chat_log.setOnClickListener {
            if(edittext_chat_log.text.isEmpty()){
                return@setOnClickListener
            }

            var date = Date()
            val formatter = SimpleDateFormat("HH:mm")


            MockApiData().newMessage(
                sender = "user1",
                receiver = user.id,
                body = edittext_chat_log.text.toString(),
                timestamp = formatter.format(date),
                completion = { message:Message, error: String? ->
                    adapter.add(ChatToItem(message))
                    adapter.notifyDataSetChanged()
                    recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                    edittext_chat_log.text.clear()
                }
            )
        }

 */
        //TODO: send last message to messagesfragment

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

class ChatFromItem(private val message: Message): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview.text = message.body
        viewHolder.itemView.date_textview.text = message.timestamp

    }

    override fun getLayout(): Int {
        return R.layout.cardview_message_received
    }
}

class ChatToItem(private val message: Message): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview.text = message.body
        viewHolder.itemView.date_textview.text = message.timestamp

    }

    override fun getLayout(): Int {
        return R.layout.cardview_message_sent
    }
}
