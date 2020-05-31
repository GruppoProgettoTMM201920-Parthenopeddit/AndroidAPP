package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.chat.UserChatFragment
import it.uniparthenope.parthenopeddit.model.User
import kotlinx.android.synthetic.main.chat_activity.*


class ChatActivity(/*private val userChat: User*/) : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        val type = "user"//= intent.getParcelableExtra<String>(NewMessageActivity.USER_KEY)
        val user : User = User("user2", "Marco Bottino","01/01/1970")//userChat //intent.getParcelableExtra<String>(NewMessageActivity.USER_KEY)
        val groupID = null//intent.getParcelableExtra<Int>(NewMessageActivity.USER_KEY)

        username_chat_textview.text = user.display_name

        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    //if(type=="user") UserChatFragment.newInstance(user) else GroupChatFragment.newInstance(groupID))
                    UserChatFragment.newInstance(user))
                .commitNow()
        }



    }
}
