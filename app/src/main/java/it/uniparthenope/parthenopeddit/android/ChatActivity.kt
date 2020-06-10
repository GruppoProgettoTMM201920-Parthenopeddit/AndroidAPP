package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.chat.UserChatFragment
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.toObject
import kotlinx.android.synthetic.main.activity_chat.*


class ChatActivity() : LoginRequiredActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val extras = intent.extras
        var user:User? = extras?.getString("user")?.toObject()

        val type = "user"//= intent.getParcelableExtra<String>(NewMessageActivity.USER_KEY)
        val groupID = null//intent.getParcelableExtra<Int>(NewMessageActivity.USER_KEY)

        username_chat_textview.text = user?.display_name

        if(user==null){
            finish()
        } else {

            if (savedInstanceState == null) {

                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        //if(type=="user") UserChatFragment.newInstance(user) else GroupChatFragment.newInstance(groupID))
                        UserChatFragment.newInstance(user)
                    )
                    .commitNow()
            }
        }



    }
}
