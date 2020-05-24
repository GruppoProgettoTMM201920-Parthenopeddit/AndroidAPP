package it.uniparthenope.parthenopeddit.android.ui.newReview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.activity_new_review.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class NewReviewActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_review)

        var enjoy_rating = 0
        var difficulty_rating = 0

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

            var date = Date()
            val formatter = SimpleDateFormat("dd MMM yyyy")
            var newReview: Review? = Review(MockDatabase.instance.reviews_table.maxBy { it -> it.id  }?.id!! + 1,
                title_edittext.text.toString(), user_review_edittext.text.toString(), formatter.format(date), MockDatabase.instance.users_table.find { it.id == "user1" }!!, 1, enjoy_rating, difficulty_rating)
            newReview?.reviewed_course =  MockDatabase.instance.course_table.find { it.id == 1 }


            MockDatabase.instance.reviews_table.add(newReview!!)
            MockDatabase.instance.course_table.find { it.id == 1 }?.reviews?.add(newReview)
            MockDatabase.instance.users_table.find{ it.id == "user1" }!!.reviews?.add(newReview)

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
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