package it.uniparthenope.parthenopeddit.android.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.MessageLog
import kotlin.collections.ArrayList

class GroupChatFragment (private val group: Group) : Fragment() {

    companion object {
        fun newInstance(group: Group) = GroupChatFragment(group)
    }

    private lateinit var viewModel: GroupChatViewModel
    private lateinit var recyclerview_chat_log: RecyclerView
    private lateinit var myMessageList : ArrayList<MessageLog>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.groupchat_fragment, container, false)
        val adapter = GroupAdapter<GroupieViewHolder>()
        var send_button_chat_log = view.findViewById<ImageButton>(R.id.send_button_chat_log)
/*

        MockApiData().getGroupChatMessages( Auth().token, group.id) { groupChatLog, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                groupChatLog!!
                myMessageList = groupChatLog.messaggi

                for(message in myMessageList){                     //PER OGNI MESSAGGIO RICEVUTO
                    if(message.inviato==true){                              //CONTROLLA SE E' IL TUO
                        adapter.add(ChatToItem(message))
                    } else{                                             //ALTRIMENTI E' DELL'ALTRO UTENTE
                        adapter.add(ChatFromItem(message))
                    }

                }
            }
        }

        recyclerview_chat_log = view.findViewById(R.id.recyclerview_chat_log) as RecyclerView

        recyclerview_chat_log.adapter = adapter
        recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)


        send_button_chat_log.setOnClickListener {
            if(edittext_chat_log.text.isEmpty()){
                return@setOnClickListener
            }

            //TODO: change with Calendar
            var date = Date()
            val formatter = SimpleDateFormat("HH:mm")


            MockApiData().newGroupMessage(
                sender = "user1",
                receiver = group.id,
                body = edittext_chat_log.text.toString(),
                timestamp = formatter.format(date),
                completion = { messageLog:GroupMessageLog, error: String? ->
                    adapter.add(ChatToItem(messageLog))
                    adapter.notifyDataSetChanged()
                    recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                    edittext_chat_log.text.clear()
                }
            )
        }
        //TODO: send last message to messagesfragment

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GroupChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

class ChatFromItem(private val messageLog: GroupMessageLog): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview.text = messageLog.message.body
        viewHolder.itemView.date_textview.text = messageLog.message.timestamp

    }

    override fun getLayout(): Int {
        return R.layout.cardview_groupmessage_received
    }
}

class ChatToItem(private val messageLog: GroupMessageLog): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview.text = messageLog.message.body
        viewHolder.itemView.date_textview.text = messageLog.message.timestamp
        viewHolder.itemView.username.text = messageLog.from_user.display_name

    }

    override fun getLayout(): Int {
        return R.layout.cardview_groupmessage_sent

 */
        return view
    }
}