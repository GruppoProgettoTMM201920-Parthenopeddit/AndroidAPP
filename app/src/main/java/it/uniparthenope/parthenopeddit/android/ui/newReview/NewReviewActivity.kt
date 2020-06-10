package it.uniparthenope.parthenopeddit.android.ui.newReview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.api.requests.ReviewsRequests
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.activity_new_review.*

class NewReviewActivity : LoginRequiredActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)

        var courseId : Int = intent.getIntExtra("id_course",0)
        var course_name : String = intent.getStringExtra("name_course")

        var enjoy_rating = 0
        var difficulty_rating = 0

        course_name_textview.text = course_name

        star_exp_1.setOnClickListener { setEnjStars(1)
            enjoy_rating = 1 }
        star_exp_2.setOnClickListener { setEnjStars(2)
            enjoy_rating = 2 }
        star_exp_3.setOnClickListener { setEnjStars(3)
            enjoy_rating = 3 }
        star_exp_4.setOnClickListener { setEnjStars(4)
            enjoy_rating = 4 }
        star_exp_5.setOnClickListener { setEnjStars(5)
            enjoy_rating = 5 }

        star_dif_1.setOnClickListener { setDifStars(1)
            difficulty_rating = 1 }
        star_dif_2.setOnClickListener { setDifStars(2)
            difficulty_rating = 2 }
        star_dif_3.setOnClickListener { setDifStars(3)
            difficulty_rating = 3 }
        star_dif_4.setOnClickListener { setDifStars(4)
            difficulty_rating = 4 }
        star_dif_5.setOnClickListener { setDifStars(5)
            difficulty_rating = 5 }



        publish_button.setOnClickListener {
            //TODO: Send review through API

            if(enjoy_rating==0){ empty_enj_textview.visibility = View.VISIBLE }
            else if(difficulty_rating==0){ empty_dif_textview.visibility = View.VISIBLE }
            else if(title_edittext.text.isEmpty()){ empty_title_textview.visibility = View.VISIBLE }
            else if(user_review_edittext.text.isEmpty()){ empty_review_textview.visibility = View.VISIBLE }
            else {
                ReviewsRequests(this, app.auth).publishNewReview(
                    user_review_edittext.text.toString(),
                    courseId,
                    enjoy_rating,
                    difficulty_rating,
                {it: Review ->
                    Toast.makeText(this, "Recensione pubblicata", Toast.LENGTH_SHORT).show()
                },{it: String ->
                    Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
                })

                val intent = Intent(this, CourseActivity::class.java)
                intent.putExtra("id_course", courseId)
                startActivity(intent)
                finish()
            }
        }

    }

    fun setEnjStars(score_liking: Int){
        if(score_liking == 1){
            star_exp_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_2.setImageResource(R.drawable.ic_star_empty_24dp)
            star_exp_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_exp_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_exp_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 2){
            star_exp_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_exp_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_exp_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 3){
            star_exp_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_exp_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 4){
            star_exp_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 5){
            star_exp_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_exp_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }
    fun setDifStars(score_liking: Int){
        if(score_liking == 1){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 2){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 3){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_empty_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 4){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_empty_24dp)
        }
        else if(score_liking == 5){
            star_dif_1.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_2.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_3.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_4.setImageResource(R.drawable.ic_star_full_24dp)
            star_dif_5.setImageResource(R.drawable.ic_star_full_24dp)
        }

    }
    
}