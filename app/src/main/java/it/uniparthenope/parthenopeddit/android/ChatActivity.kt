package it.uniparthenope.parthenopeddit.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.chat.UserChatFragment
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.User

class ChatActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        val type = "user"//= intent.getParcelableExtra<String>(NewMessageActivity.USER_KEY)
        val user = User("user2", "Marco Bottino", "01/01/1970") //intent.getParcelableExtra<String>(NewMessageActivity.USER_KEY)
        val groupID = null//intent.getParcelableExtra<Int>(NewMessageActivity.USER_KEY)
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    //if(type=="user") UserChatFragment.newInstance(user) else GroupChatFragment.newInstance(groupID))
                    UserChatFragment.newInstance(user))
                .commitNow()
        }




        supportActionBar?.title = user.display_name



    }
}
