package it.uniparthenope.parthenopeddit.android.adapters

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.course.post.CoursePostFragment
import it.uniparthenope.parthenopeddit.android.ui.course.review.CourseReviewFragment
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.api.ReviewNamespace
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.cardview_review.view.*

class CourseAdapter(fm: FragmentManager, courseId: Int) : FragmentPagerAdapter(fm), ReviewNamespace {

    var courseID = courseId
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                CourseReviewFragment(courseID)
            }
            else -> {
                return CoursePostFragment(courseID)
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

    //INUTILE, MI SERVIVA SOLO GET COURSE RATING
    override fun getAllReview(
        token: String,
        completion: (reviewList: List<Review>?, error: String?) -> Unit
    ) { }

    override fun getCourseInfo(
        token: String,
        courseId: Int,
        completion: (courseRating: Float, courseDifficulty: Float, numReviews: Int, courseName: String?, isFollowed: Boolean, error: String?) -> Unit
    ) {
        var isFollowed: Boolean = false
        for (course in MockDatabase.instance.course_table) {
            if (course.id == courseId) {
                if( MockDatabase.instance.users_table.filter{ it.id == "user1"}.single().followed_courses?.filter { it.id == courseId } != null ){ isFollowed=true }
                completion.invoke(course.average_liking_score!!.toFloat(), course.average_difficulty_score!!.toFloat(), course.reviews_count!!, course.name, isFollowed, null)
                return
            }
        }
        completion.invoke(0F, 0F, 0,"no course with id $courseId", isFollowed,"no course with id $courseId")
    }

    override fun getCourseReviews(
        token: String,
        courseId: Int,
        completion: (reviewList: List<Review>?, error: String?) -> Unit
    ) {
        for(course in MockDatabase.instance.course_table) {
            if (course.id == courseId) {
                completion.invoke(course.reviews, null)
                return
            }
        }

        completion.invoke(null, "course not found")
    }
}