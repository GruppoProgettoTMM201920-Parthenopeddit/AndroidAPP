package it.uniparthenope.parthenopeddit.android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import it.uniparthenope.parthenopeddit.android.ui.user_boards.course.CourseUserBoardFragment
import it.uniparthenope.parthenopeddit.android.ui.user_boards.group.GroupUserBoardFragment

class UserBoardsAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CourseUserBoardFragment()
            }
            else -> {
                GroupUserBoardFragment()
            }
        }

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "CORSI"
            else -> return "GRUPPI"
        }
    }



}