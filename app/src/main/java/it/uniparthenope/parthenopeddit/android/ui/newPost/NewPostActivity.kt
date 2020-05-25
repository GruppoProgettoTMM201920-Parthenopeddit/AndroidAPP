package it.uniparthenope.parthenopeddit.android.ui.newPost

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.Post
import kotlinx.android.synthetic.main.activity_new_post.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)
        val actionBar = supportActionBar
        actionBar!!.title = "Nuovo post"

        val groupSpinner = findViewById<Spinner>(R.id.group_spinner)
        val groups: MutableList<Group> = ArrayList()
        val publish_button = findViewById<Button>(R.id.publish_button)

        //TODO: Add sections to spinner
        /*groups.add(Group(true, "Corsi di studio"))
        groups.add(Group(false, "Terminali mobili"))
        groups.add(Group(false, "Tecnologie Web"))

        groups.add(Group(true, "Gruppi"))
        groups.add(Group(false, "Progetto TMM"))
        groups.add(Group(false, "Programmatori Kotlin"))*/

        ArrayAdapter.createFromResource(this, R.array.groups, android.R.layout.simple_spinner_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                groupSpinner.adapter = adapter

            }

        publish_button.setOnClickListener {
            //TODO: Send post through API
            //var date = Date()
            //val formatter = SimpleDateFormat("dd MMM yyyy HH:mma")
            //var newPost: Post? = Post(
            //    MockDatabase.instance.reviews_table.maxBy { it -> it.id  }?.id!! + 1,
            //    title_edittext.text.toString(), user_post_edittext.text.toString(), formatter.format(date), MockDatabase.instance.users_table.find { it.id == "user1" }!!, null, MockDatabase.instance.course_table.find { it.id == 1 }, difficulty_rating)
            //newPost?.reviewed_course =  MockDatabase.instance.course_table.find { it.id == 1 }


            //MockDatabase.instance.reviews_table.add(newPost!!)
            //MockDatabase.instance.course_table.find { it.id == 1 }?.reviews?.add(newPost)
            //MockDatabase.instance.users_table.find{ it.id == "user1" }!!.reviews?.add(newPost)

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
}