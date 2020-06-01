package it.uniparthenope.parthenopeddit.android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import it.uniparthenope.parthenopeddit.android.ui.messages.CommentActivitiesFragment
import it.uniparthenope.parthenopeddit.android.ui.messages.PostActivitiesFragment
import it.uniparthenope.parthenopeddit.android.ui.messages.ReviewActivitiesFragment

class UserActivityAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PostActivitiesFragment()
            }
            1 -> {
                CommentActivitiesFragment()
            }
            else -> {
                return ReviewActivitiesFragment()
            }
        }

    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "POST"
            1 -> "COMMENTI"
            else -> return "RECENSIONI"
        }
    }
}