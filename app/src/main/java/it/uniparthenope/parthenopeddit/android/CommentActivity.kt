package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.CommentAdapter
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.model.Post
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : BasicActivity(), CommentAdapter.CommentItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        val extras = intent.extras
        var id_post:Int = extras?.getInt("idPost")?:0

        if( id_post == 0 ) {
            Log.d("DEBUG","id post was 0")
            id_post = 1
        }

        for (post in MockDatabase.instance.posts_table) {
            Log.d("DEBUG","setting layout post texts")

            val username_textview: TextView = findViewById(R.id.username_textview)
            val titolo: TextView = findViewById(R.id.group_textview)
            val body: TextView = findViewById(R.id.posttext_textview)

            username_textview.text = post.author_id
            titolo.text = post.title
            body.text = post.body?:""

            Log.d("DEBUG","done")


            val commenti = post.comments
            if(commenti == null) {
                listaCommenti.visibility = View.GONE
            } else {
                listaCommenti.visibility = View.VISIBLE

                Log.d("DEBUG","initializing comments layout")

                val listaCommenti:RecyclerView = findViewById(R.id.listaCommenti)
                val commentAdapter = CommentAdapter(this, commenti, this)
                listaCommenti.adapter = commentAdapter
                listaCommenti.layoutManager = LinearLayoutManager(this)
                listaCommenti.setHasFixedSize(true)

                Log.d("DEBUG","done")
            }
        }

        var message_edittext = findViewById<EditText>(R.id.message_edittext)
        val send_btn = findViewById<Button>(R.id.send_btn)
        var message: String

        send_btn.setOnClickListener {
            message = message_edittext.text.toString()
            if(message.isNotEmpty()){
                //TODO: Send message through API
            } else{
                Toast.makeText(this,"Non hai scritto alcun commento.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onClickLike(id_Commento: Int) {
    }

    override fun onClickDislike(id_Commento: Int) {
    }

    override fun onClickComments(id_Commento: Int) {
    }
}
