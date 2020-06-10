package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.messages.CommentActivitiesFragment
import it.uniparthenope.parthenopeddit.android.ui.messages.PostActivitiesFragment
import it.uniparthenope.parthenopeddit.android.ui.messages.ReviewActivitiesFragment
import kotlinx.android.synthetic.main.activity_course.*

class UserContentActivity : LoginRequiredActivity() {

    lateinit var user_id: String

    private lateinit var viewPager: ViewPager2

    private lateinit var postActivitiesFragment: PostActivitiesFragment
    private lateinit var commentActivitiesFragment: CommentActivitiesFragment
    private lateinit var reviewActivitiesFragment: ReviewActivitiesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_content)

        val extras = intent.extras
        user_id = extras?.getString("user_id")?:app.auth.username!!

        postActivitiesFragment = PostActivitiesFragment(user_id)
        commentActivitiesFragment = CommentActivitiesFragment(user_id)
        reviewActivitiesFragment = ReviewActivitiesFragment(user_id)

        viewPager = findViewById(R.id.view_pager)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "Posts"}
                    1 -> { tab.text = "Comments"}
                    2 -> { tab.text = "Reviews"}
                }
            }
        ).attach()
    }

    private inner class ScreenSlidePagerAdapter(activity: LoginRequiredActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 3
        override fun createFragment(position: Int): Fragment {
            return when( position ) {
                0 -> postActivitiesFragment
                1 -> commentActivitiesFragment
                2 -> reviewActivitiesFragment
                else ->Fragment()
            }
        }
    }
}