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

class GroupAdapter(groupId: Int) /*: GroupNamespace*/ {

    var groupID = groupId


    /*override fun getGroupInfo(
        token: String,
        groupId: Int,
        completion: (groupName: String?, groupUsers: String?, error: String?) -> Unit
    ) {
        for (group in MockDatabase.instance.group_table) {
            if (group.id == groupId) {
                completion.invoke(group.rating, course.difficulty, course.numReview, course.course_name, null)
                return
            }
        }
        completion.invoke(0F, 0F, 0,"no course with id $courseId", "no course with id $courseId")
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

     */
}