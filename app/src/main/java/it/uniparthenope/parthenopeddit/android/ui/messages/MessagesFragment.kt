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
import it.uniparthenope.parthenopeddit.android.adapters.MessagesAdapter
import it.uniparthenope.parthenopeddit.model.Chat

class MessagesFragment : Fragment() {

    private lateinit var messagesViewModel: MessagesViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        messagesViewModel =
                ViewModelProviders.of(this).get(MessagesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_messages, container, false)
        val textView: TextView = root.findViewById(R.id.text_messages)
        messagesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        //val postAdapter = MessagesAdapter(ArrayList<Chat>(), this)
        //recycler_view.adapter = postAdapter
       // recycler_view.layoutManager = LinearLayoutManager(requireContext())
       // recycler_view.setHasFixedSize(true)

        return root
    }
}
