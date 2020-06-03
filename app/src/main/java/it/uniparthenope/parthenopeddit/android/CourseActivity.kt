package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CourseAdapter
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.android.ui.newReview.NewReviewActivity
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import kotlinx.android.synthetic.main.activity_course.*

class CourseActivity : AppCompatActivity() {

    var isOpen = false
    var isFollowed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        var intent: Intent = getIntent()
        var courseId : Int = intent.getIntExtra("id_group",0)        //TODO(DEVE ESSERE OTTENUTO DA QUERY)
        var course_name : String? = ""

        val sectionsPagerAdapter = CourseAdapter(supportFragmentManager,courseId)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)

        val follow_button = findViewById(R.id.follow_button) as Button
        val fab = findViewById(R.id.fab) as FloatingActionButton
        val fab_new_post = findViewById(R.id.fab_new_post) as FloatingActionButton
        val fab_new_review = findViewById(R.id.fab_new_review) as FloatingActionButton
        val fab_new_post_textview = findViewById(R.id.fab_new_post_textview) as TextView
        val fab_new_review_textview = findViewById(R.id.fab_new_review_textview) as TextView
        val rotateClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)


        MockApiData().getCourseInfo( Auth().token, courseId) { courseRating:Float, courseDifficulty:Float, numReviews: Int, courseName: String?, isFollowedCourse: Boolean, error: String? ->

            if(isFollowedCourse){
                isFollowed = true
                follow_button.text = "Non seguire"
                val imgResource: Int = R.drawable.ic_unfollow_themecolor_24dp
                follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0);
            }
            course_name_textview.text = courseName
            course_name = courseName
            course_enjoyment_rating.text = courseRating.toString()+"/5"
            course_difficulty_rating.text = courseDifficulty.toString()+"/5"
            if(numReviews>1 || numReviews!=0){num_reviews_textview.text = numReviews.toString()+" recensioni"} else{num_reviews_textview.text = numReviews.toString()+" recensione"}


            setEnjoymentStars(courseRating)
            setDifficultyStars(courseDifficulty)
        }

        follow_button.setOnClickListener {
            if(isFollowed){
                //TODO: unfollow course
                Toast.makeText(this, "Hai smesso di seguire ${course_name_textview.text}",Toast.LENGTH_LONG).show()
                follow_button.text = "Segui"
                val imgResource: Int = R.drawable.ic_follow_themecolor_24dp
                follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0)
                isFollowed = false
            } else {
                //TODO: follow course
                Toast.makeText(this, "Hai seguito ${course_name_textview.text}",Toast.LENGTH_LONG).show()
                follow_button.text = "Non seguire"
                val imgResource: Int = R.drawable.ic_unfollow_themecolor_24dp
                follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0)
                isFollowed = true
            }
        }

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

        fab_new_post.setOnClickListener{ onClickNewPost(courseId, course_name!!) }
        fab_new_post_textview.setOnClickListener{ onClickNewPost(courseId, course_name!!) }
        fab_new_review.setOnClickListener{ onClickNewReview(courseId) }
        fab_new_review_textview.setOnClickListener{ onClickNewReview(courseId) }
    }

    fun setEnjoymentStars(score_liking: Float){
        if(score_liking == 1.0F){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >1 && score_liking <2){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_half_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 2.0F){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >2 && score_liking <3){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_half_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 3.0F){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >3 && score_liking <4){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_half_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 4.0F){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >4 && score_liking <5){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_half_24dp)
        }
        else if(score_liking == 5.0F){
            star_enj_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_enj_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }

    fun setDifficultyStars(score_difficulty: Float){
        if(score_difficulty == 1.0F){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >1 && score_difficulty <2){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_half_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty == 2.0F){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >2 && score_difficulty <3){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_half_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty == 3.0F){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >3 && score_difficulty <4){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_half_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty == 4.0F){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_difficulty >4 && score_difficulty <5){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_half_24dp)
        }
        else if(score_difficulty == 5.0F){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }

    fun onClickNewPost(courseId: Int, course_name: String){
        //crea dialogo
        //passi fuonzione da effettuare onSuccess
        //uploiad to api
        //notifidatasetchanged()
        val intent = Intent(this, NewPostActivity::class.java)
        intent.putExtra("id_group", courseId)
        intent.putExtra("name_group", course_name)
        startActivity(intent)
    }

    fun onClickNewReview(courseId: Int){
        val intent = Intent(this, NewReviewActivity::class.java)
        intent.putExtra("id_group", courseId)
        startActivity(intent)
    }
}