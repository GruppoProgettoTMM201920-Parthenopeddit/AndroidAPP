package it.uniparthenope.parthenopeddit.android.ui

import android.os.Bundle
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.chat.GroupChatFragment
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.util.toObject

class GroupChatActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groupchat)
        val extras = intent.extras
        var group: Group? = extras?.getString("group")?.toObject()

        /*val type = "user"//= intent.getParcelableExtra<String>(NewMessageActivity.USER_KEY)
        val groupID = null//intent.getParcelableExtra<Int>(NewMessageActivity.USER_KEY)

        //groupname_chat_textview.text = group?.name

        if(group==null){
            finish()
        } else {

            if (savedInstanceState == null) {

                supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        //if(type=="user") UserChatFragment.newInstance(user) else GroupChatFragment.newInstance(groupID))
                        GroupChatFragment.newInstance(group)
                    )
                    .commitNow()
            }
        }



    }

         */
    }
}