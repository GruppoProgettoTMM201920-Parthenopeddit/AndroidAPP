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
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.Board
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.Post
import kotlinx.android.synthetic.main.activity_new_post.*
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

        //TODO: Add sections to spinner

        publish_button.setOnClickListener {

            if(title_edittext.text.isEmpty()){ empty_title_textview.visibility = View.VISIBLE }
            else if(user_post_edittext.text.isEmpty()){ empty_body_textview.visibility = View.VISIBLE }
            else {

                var date = Date()
                val formatter = SimpleDateFormat("dd MMM yyyy")
                var newPost: Post? =
                    Post(
                        id = MockDatabase.instance.posts_table.maxBy { it -> it.id }?.id!! + 1,
                        title = title_edittext.text.toString(),
                        body = user_post_edittext.text.toString(),
                        timestamp = formatter.format(date),
                        author_id = "user1",
                        author = MockDatabase.instance.users_table.find { it.id == "user1" }!!,
                        comments_num = 0,
                        likes_num = 0,
                        dislikes_num = 0,
                        posted_to_board_id = boardId,
                        posted_to_board =  MockDatabase.instance.board_table.find{ it.id == boardId }
                    )

                MockDatabase.instance.posts_table.add(newPost!!)
                MockDatabase.instance.users_table.find { it.id == "user1" }!!.published_posts?.add(newPost)

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }

    }
}