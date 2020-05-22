package it.uniparthenope.parthenopeddit.android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import it.uniparthenope.parthenopeddit.android.ui.course.post.CoursePostFragment
import it.uniparthenope.parthenopeddit.android.ui.course.review.CourseReviewFragment

class CourseAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CourseReviewFragment()
            }
            else -> {
                return CoursePostFragment()
            }
        }

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "RECENSIONI"
            else -> return "POST"
        }
    }
}