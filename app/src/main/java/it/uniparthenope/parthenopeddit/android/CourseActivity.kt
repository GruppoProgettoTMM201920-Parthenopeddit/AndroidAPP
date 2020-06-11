package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.ui.course.post.CoursePostFragment
import it.uniparthenope.parthenopeddit.android.ui.course.review.CourseReviewFragment
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.android.ui.newReview.NewReviewActivity
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.model.Course
import kotlinx.android.synthetic.main.activity_course.*

class CourseActivity : LoginRequiredActivity() {

    private lateinit var course: Course
    private lateinit var viewPager: ViewPager2

    private var isOpen = false
    private var isFollowed: Boolean = false

    private lateinit var fragmentPosts: CoursePostFragment
    private lateinit var fragmentReviews: CourseReviewFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        val extras = intent.extras
        val id_course:Int = extras?.getInt("id_course")?:0

        if(id_course == 0) finish()

        fragmentPosts = CoursePostFragment(id_course)
        fragmentReviews = CourseReviewFragment(id_course)

        CoursesRequests(this, app.auth).getCourseByID(
            id_course,
            {
                course = it

                course_name_textview.text = course.name
                num_reviews_textview.text = "${course.reviews_count} recensioni"
                rating_enj_bar.rating = (course.average_liking_score?:0.0).toFloat()
                course_enjoyment_rating.text = "%2.1f/5".format(course.average_liking_score?:0)
                rating_diff_bar.rating = (course.average_difficulty_score?:0.0).toFloat()
                course_difficulty_rating.text = "%2.1f/5".format(course.average_difficulty_score?:0)
            }, {}
        )

        viewPager = findViewById(R.id.view_pager)
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "Posts"}
                    1 -> { tab.text = "Reviews"}
                }
            }
        ).attach()

        CoursesRequests(this, app.auth).getFollowedCourses(
            {
                val is_followed = it.filter{ it.id == course.id }.firstOrNull() != null
                setBottoneFollow(is_followed)
            }, {}
        )

        follow_button.setOnClickListener {
            if( isFollowed ) {
                CoursesRequests(this, app.auth).unfollowCourse(
                    course.id,
                    {
                        setBottoneFollow(false)
                    }, {}
                )
            } else {
                CoursesRequests(this, app.auth).followCourse(
                    course.id,
                    {
                        setBottoneFollow(true)
                    }, {}
                )
            }
        }

        val rotateClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)

        fab.setOnClickListener{
            if(isOpen){
                fab.startAnimation(rotateClockwise)

                fab_new_post.animate().translationY(200F)
                fab_new_review.animate().translationY(400F)
                fab_new_post_textview.animate().translationY(200F)
                fab_new_review_textview.animate().translationY(400F)
                fab_new_post_textview.animate().alpha(0F)
                fab_new_review_textview.animate().alpha(0F)
                fab_new_post_textview.visibility = View.GONE
                fab_new_review_textview.visibility = View.GONE
                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)

                fab_new_post.animate().translationY(-200F)
                fab_new_review.animate().translationY(-400F)
                fab_new_post_textview.animate().translationY(-200F)
                fab_new_review_textview.animate().translationY(-400F)
                fab_new_post_textview.visibility = View.VISIBLE
                fab_new_review_textview.visibility = View.VISIBLE
                fab_new_post_textview.animate().alpha(1F)
                fab_new_review_textview.animate().alpha(1F)
                isOpen = true
            }
        }

        fab_new_post.setOnClickListener {
            onClickNewPost(course)
        }
        fab_new_post_textview.setOnClickListener{
            onClickNewPost(course)
        }
        fab_new_review.setOnClickListener{
            onClickNewReview(course)
        }
        fab_new_review_textview.setOnClickListener{
            onClickNewReview(course)
        }
    }

    fun setBottoneFollow(isCourseFollowed: Boolean) {
        if(!isCourseFollowed){
            follow_button.text = "Segui"
            val imgResource: Int = R.drawable.ic_follow_themecolor_24dp
            follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0)
            isFollowed = isCourseFollowed
        } else {
            follow_button.text = "Non seguire"
            val imgResource: Int = R.drawable.ic_unfollow_themecolor_24dp
            follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0)
            isFollowed = isCourseFollowed
        }
    }

    fun onClickNewPost(course: Course) {
        val intent = Intent(this, NewPostActivity::class.java)
        intent.putExtra("id_group", course.id)
        intent.putExtra("name_group", course.name)
        startActivity(intent)
    }

    fun onClickNewReview(course: Course) {
        val intent = Intent(this, NewReviewActivity::class.java)
        intent.putExtra("id_course", course.id)
        intent.putExtra("name_course", course.name)
        startActivity(intent)
    }

    private inner class ScreenSlidePagerAdapter(activity: LoginRequiredActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2
        override fun createFragment(position: Int): Fragment {
            return when( position ) {
                0 -> fragmentPosts
                1 -> fragmentReviews
                else ->Fragment()
            }
        }
    }
}