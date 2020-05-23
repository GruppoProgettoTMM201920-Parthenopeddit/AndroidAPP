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

        MockApiData().getCourseInfo( Auth().token, courseId) { courseRating:Float, numReviews: Int, courseName: String?, error: String? ->
            course_name_textview.text = courseName
            course_rating_textview.text = courseRating.toString()+"/5"
            if(numReviews>1 || numReviews!=0){num_reviews_textview.text = numReviews.toString()+" recensioni"} else{num_reviews_textview.text = numReviews.toString()+" recensione"}


            setStars(courseRating)
        }

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    fun setStars(score_liking: Float){
        if(score_liking == 1.0F){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_empty_24dp)
            star_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >1 && score_liking <2){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_half_24dp)
            star_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 2.0F){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >2 && score_liking <3){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_half_24dp)
            star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 3.0F){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >3 && score_liking <4){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_4.setImageResource(R.drawable.ic_star_half_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 4.0F){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking >4 && score_liking <5){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_5.setImageResource(R.drawable.ic_star_half_24dp)
        }
        else if(score_liking == 5.0F){
            star_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }
    
}