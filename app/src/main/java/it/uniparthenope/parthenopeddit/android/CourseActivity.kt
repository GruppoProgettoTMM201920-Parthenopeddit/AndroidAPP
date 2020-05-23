package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CourseAdapter
import it.uniparthenope.parthenopeddit.android.ui.ui.main.SectionsPagerAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import kotlinx.android.synthetic.main.activity_course.*

class CourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        var intent: Intent = getIntent()
        val courseId = intent.getIntExtra("id_group",0)        //TODO(DEVE ESSERE OTTENUTO DA QUERY)

        val sectionsPagerAdapter = CourseAdapter(supportFragmentManager,courseId)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        tabLayout.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        MockApiData().getCourseInfo( Auth().token, courseId) { courseRating:Float, courseDifficulty:Float, numReviews: Int, courseName: String?, error: String? ->
            course_name_textview.text = courseName
            course_enjoyment_rating.text = courseRating.toString()+"/5"
            course_difficulty_rating.text = courseDifficulty.toString()+"/5"
            if(numReviews>1 || numReviews!=0){num_reviews_textview.text = numReviews.toString()+" recensioni"} else{num_reviews_textview.text = numReviews.toString()+" recensione"}


            setEnjoymentStars(courseRating)
            setDifficultyStars(courseDifficulty)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
}