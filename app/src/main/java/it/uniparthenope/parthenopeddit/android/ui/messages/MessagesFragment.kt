package it.uniparthenope.parthenopeddit.android.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.api.MockDatabase
import kotlinx.android.synthetic.main.fragment_messages.*

class MessagesFragment : Fragment() {

    private lateinit var recycler_view: RecyclerView
    private lateinit var messagesViewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        messagesViewModel =
            ViewModelProviders.of(this).get(MessagesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_messages, container, false)
        val adapter = ChatListAdapter()

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        adapter.aggiornaLista( MockDatabase.instance.chat_table )
        recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()
        return root
    }
}
