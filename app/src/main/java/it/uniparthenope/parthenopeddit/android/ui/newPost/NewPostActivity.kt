package it.uniparthenope.parthenopeddit.android.ui.newPost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.api.requests.PostsRequests
import kotlinx.android.synthetic.main.activity_new_post.*

class NewPostActivity : LoginRequiredActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        val actionBar = supportActionBar
        actionBar!!.title = "Nuovo post"
        val boardId : Int = intent.getIntExtra("id_group",0)
        val board_name : String = intent.extras!!.getString("name_group", "")
        val board_name_textview : TextView = findViewById<TextView>(R.id.board_name_textview)
        board_name_textview.text = board_name

        val publish_button = findViewById<Button>(R.id.publish_button)

        publish_button.setOnClickListener {
            if(title_edittext.text.isEmpty()){
                empty_title_textview.visibility = View.VISIBLE
                return@setOnClickListener
            } else {
                empty_title_textview.visibility = View.GONE
            }

            if(user_post_edittext.text.isEmpty()){
                empty_body_textview.visibility = View.VISIBLE
                return@setOnClickListener
            } else {
                empty_body_textview.visibility = View.GONE
            }

            PostsRequests(this, app.auth).publishNewPost(
                title_edittext.text.toString(),
                user_post_edittext.text.toString(),
                boardId,
                {
                    Toast.makeText(this, "Post pubblicato", Toast.LENGTH_SHORT).show()
                },{
                    Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
                }
            )
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }
}