package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mancj.materialsearchbar.MaterialSearchBar
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.search.courses.SearchCoursesFragment
import it.uniparthenope.parthenopeddit.android.ui.search.posts.SearchPostsFragment
import it.uniparthenope.parthenopeddit.android.ui.search.users.SearchUsersFragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : LoginRequiredActivity() {

    private lateinit var viewPager: ViewPager2

    private lateinit var searchCoursesFragment: SearchCoursesFragment
    private lateinit var searchPostsFragment: SearchPostsFragment
    private lateinit var searchUsersFragment: SearchUsersFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val extras = intent.extras
        val query: String? = intent.getStringExtra("query")

        lateinit var searchQuery:String

        if(query == null) finish()
        else searchQuery = query

        searchCoursesFragment = SearchCoursesFragment(searchQuery)
        searchPostsFragment = SearchPostsFragment(searchQuery)
        searchUsersFragment = SearchUsersFragment(searchQuery)

        viewPager = findViewById(R.id.view_pager)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabs, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "Corsi"}
                    1 -> { tab.text = "Post"}
                    2 -> { tab.text = "Utenti"}
                }
            }
        ).attach()

        val searchBar = findViewById<MaterialSearchBar>(R.id.searchBar)

        searchBar.text = searchQuery

        searchBar.setSpeechMode(false)
        searchBar.setCardViewElevation(10)
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {}

            override fun onSearchConfirmed(text: CharSequence) {
                if(text.isNotBlank()){
                    val newQuery = text.toString()
                    searchCoursesFragment.search(newQuery)
                    searchPostsFragment.search(newQuery)
                    searchUsersFragment.search(newQuery)
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

    private inner class ScreenSlidePagerAdapter(activity: LoginRequiredActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
            return when( position ) {
                0 -> searchCoursesFragment
                1 -> searchPostsFragment
                2 -> searchUsersFragment
                else -> Fragment()
            }
        }
    }
}