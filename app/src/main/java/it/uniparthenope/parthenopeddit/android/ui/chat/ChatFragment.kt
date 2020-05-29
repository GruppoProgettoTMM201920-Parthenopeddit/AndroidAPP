package it.uniparthenope.parthenopeddit.android.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import it.uniparthenope.parthenopeddit.android.adapters.ChatAdapter

class ChatFragment : Fragment() {

    /*private lateinit var viewModel: ChatViewModel

    private var chatAdapter = ChatAdapter(mutableListOf())

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        chatAdapter.data=viewModel.messages
        subscribeOnAddMessage()
    }

    private fun subscribeOnAddMessage() {
        viewModel.notifyNewMessageInsertedLiveData.observe(this, Observer {
            chatAdapter.notifyItemInserted(it)
        })
    }


    override fun initView() {
        super.initView()
        bot_conversation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                .apply {
                    stackFromEnd = true
                    isSmoothScrollbarEnabled = true
                }
            adapter = chatAdapter
        }

        input_layout.onSendClicked = {
            viewModel.sendMessage(it)
            hideKeyboard(activity)
        }
    }

     */
}