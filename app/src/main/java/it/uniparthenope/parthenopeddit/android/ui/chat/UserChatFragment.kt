package it.uniparthenope.parthenopeddit.android.ui.chat

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import it.uniparthenope.parthenopeddit.R

import com.xwray.groupie.GroupieViewHolder
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.User
import kotlinx.android.synthetic.main.chat_fragment.*

class UserChatFragment(private val user: User) : Fragment() {

    companion object {
        fun newInstance(user: User) = UserChatFragment(user)
    }

    private lateinit var viewModel: UserChatViewModel
    private lateinit var recyclerview_chat_log: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.chat_fragment, container, false)


        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        adapter.add(ChatFromItem())
        adapter.add(ChatToItem())
        recyclerview_chat_log = view.findViewById(R.id.recyclerview_chat_log) as RecyclerView

        recyclerview_chat_log.adapter = adapter
        recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

class ChatFromItem: Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.cardview_message_received
    }
}

class ChatToItem: Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.cardview_message_sent
    }
}
