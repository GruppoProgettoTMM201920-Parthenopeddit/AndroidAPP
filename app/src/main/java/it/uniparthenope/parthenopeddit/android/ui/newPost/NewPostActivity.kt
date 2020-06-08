package it.uniparthenope.parthenopeddit.android.ui.newPost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import it.uniparthenope.parthenopeddit.api.requests.ReviewsRequests
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.activity_new_post.*
import kotlinx.android.synthetic.main.activity_new_post.empty_title_textview
import kotlinx.android.synthetic.main.activity_new_post.title_edittext
import kotlinx.android.synthetic.main.activity_new_review.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewPostActivity : BasicActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        val actionBar = supportActionBar
        actionBar!!.title = "Nuovo post"
        var boardId : Int = intent.getIntExtra("id_group",0)
        var board_name : String = intent.extras!!.getString("name_group", "")
        var board_name_textview : TextView = findViewById<TextView>(R.id.board_name_textview)
        board_name_textview.text = board_name

        var boards_name: ArrayList<String> = ArrayList()
        val publish_button = findViewById<Button>(R.id.publish_button)

        publish_button.setOnClickListener {

            if(title_edittext.text.isEmpty()){ empty_title_textview.visibility = View.VISIBLE }
            else if(user_post_edittext.text.isEmpty()){ empty_body_textview.visibility = View.VISIBLE }
            else {


               PostsRequests(this, app.auth).publishNewPost(
                    title_edittext.text.toString(),
                   user_post_edittext.text.toString(),
                   boardId,
                    {it: Post ->
                        Toast.makeText(this, "Post pubblicato", Toast.LENGTH_SHORT).show()
                    },{it: String ->
                        Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
                    })

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}