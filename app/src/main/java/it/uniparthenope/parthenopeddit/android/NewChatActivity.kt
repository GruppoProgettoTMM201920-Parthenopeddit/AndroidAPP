package it.uniparthenope.parthenopeddit.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mancj.materialsearchbar.MaterialSearchBar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.chat.NewChatUserListFragment
import it.uniparthenope.parthenopeddit.android.ui.search.courses.SearchCoursesFragment

class NewChatActivity : BasicActivity() {

    public lateinit var query: String
    private lateinit var newChatUserListFragment: NewChatUserListFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_chat)

        newChatUserListFragment = NewChatUserListFragment()
        supportFragmentManager.beginTransaction().replace(R.id.container, newChatUserListFragment).commit()

        val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)
        searchBar.setSpeechMode(false)
        searchBar.setCardViewElevation(10)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence) {
                if(text.isNotBlank()){
                    val newQuery = text.toString()
                    newChatUserListFragment.search(newQuery)
                }
            }

            override fun onButtonClicked(buttonCode: Int) {
                when (buttonCode) {
                    MaterialSearchBar.BUTTON_NAVIGATION -> {
                    }
                    MaterialSearchBar.BUTTON_SPEECH -> {
                    }
                    MaterialSearchBar.BUTTON_BACK -> searchBar.closeSearch()
                }
            }
        })
    }
}