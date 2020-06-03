package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.group.BackdropFragment
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.GroupMember
import kotlinx.android.synthetic.main.activity_course.*
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    var isOpen = false
    var isFollowed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val extras = intent.extras
        var id_group:Int = extras?.getInt("id_group")?:0
        var name_group: String = ""
        var created_on_group: String?
        var members_group : ArrayList<GroupMember>?
        var members_num_group : Int?

        val num_members_textview = findViewById<TextView>(R.id.num_members)

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(null)
        group_recyclerview.adapter = postAdapter
        group_recyclerview.layoutManager = LinearLayoutManager(this)
        group_recyclerview.setHasFixedSize(true)


        val fab = findViewById(R.id.fab) as FloatingActionButton
        val fab_new_post__group = findViewById(R.id.fab_new_post_group) as FloatingActionButton
        val fab_new_post_textview__group = findViewById(R.id.fab_new_post_textview_group) as TextView
        val follow_button = findViewById(R.id.follow_button) as Button
        val rotateClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)


        MockApiData().getGroupInfo( Auth().token, id_group) { name, num_members, created, members, error ->

            if(error != null){
                //toast
            } else {
                group_name_textview.text = name!!
                name_group = group_name_textview.text.toString()
                created_on_group = created
                members_group = members
                members_num_group = members?.size
                Log.d("DEBUG", "there are ${members_num_group} members")
                if(num_members!=1){ num_members_textview.text = "${num_members} membri" } else { num_members_textview.text = "${num_members} membro" }

                Log.d("DEBUG", "before cf backdrop")
                configureBackdrop(id_group, name_group, created_on_group, members_group, members_num_group)
            }

        }

        name_group = group_name_textview.text.toString()

        MockApiData().getGroupPost( Auth().token, id_group) { postItemList, error ->
            if( error != null ) {
                Toast.makeText(this,"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                postItemList!!

                postAdapter.aggiungiPost( postItemList )
            }
        }

        fab.setOnClickListener{
            if(isOpen){
                fab.startAnimation(rotateClockwise)


                fab_new_post_group.animate().translationY(200F)
                fab_new_post_textview_group.animate().translationY(200F)
                fab_new_post_textview_group.animate().alpha(0F)
                fab_new_post_textview_group.visibility = View.GONE
                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)

                fab_new_post_group.animate().translationY(-200F)

                fab_new_post_textview_group.animate().translationY(-200F)

                fab_new_post_textview_group.visibility = View.VISIBLE

                fab_new_post_textview_group.animate().alpha(1F)
                isOpen = true
            }
        }

        fab_new_post_group.setOnClickListener{ onClickNewPost(id_group, name_group!!) }
        fab_new_post_textview_group.setOnClickListener{ onClickNewPost(id_group, name_group!!) }
        follow_button.setOnClickListener {
            if(isFollowed){
                //TODO: unfollow group
                Toast.makeText(this, "Hai smesso di seguire ${group_name_textview.text}",Toast.LENGTH_LONG).show()
                follow_button.text = "Entra"
                val imgResource: Int = R.drawable.ic_follow_themecolor_24dp
                follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0)
                isFollowed = false
            } else {
                //TODO: follow group
                Toast.makeText(this, "Hai seguito ${group_name_textview.text}",Toast.LENGTH_LONG).show()
                follow_button.text = "Lascia"
                val imgResource: Int = R.drawable.ic_unfollow_themecolor_24dp
                follow_button.setCompoundDrawablesWithIntrinsicBounds(imgResource, 0, 0, 0)
                isFollowed = true
            }
        }


    }

    fun onClickNewPost(id_group: Int, name_group: String){
        //crea dialogo
        //passi fuonzione da effettuare onSuccess
        //uploiad to api
        //notifidatasetchanged()
        val intent = Intent(this, NewPostActivity::class.java)
        intent.putExtra("id_group", id_group)
        intent.putExtra("name_group", name_group)
        startActivity(intent)
    }

    private var mBottomSheetBehavior: BottomSheetBehavior<View?>? = null

    private fun configureBackdrop(id_group: Int, name_group: String?, created_on_group: String?, members_group: ArrayList<GroupMember>?, members_num_group: Int?) {
        // Get the fragment reference
        Log.d("DEBUG", "before findfragment")
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment) as BackdropFragment
        fragment.updateData(id_group,name_group,created_on_group, members_group, members_num_group)


        fragment?.let {
            // Get the BottomSheetBehavior from the fragment view
            BottomSheetBehavior.from(it.requireView())?.let { bsb ->
                // Set the initial state of the BottomSheetBehavior to HIDDEN
                bsb.state = BottomSheetBehavior.STATE_HIDDEN

                // Set the trigger that will expand your view
                group_info_toolbar.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_HALF_EXPANDED }
                group_image.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_HALF_EXPANDED }
                group_name_textview.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_HALF_EXPANDED }
                num_members.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_HALF_EXPANDED }

                // Set the reference into class attribute (will be used latter)
                mBottomSheetBehavior = bsb
            }
        }
    }

    override fun onBackPressed() {
        // With the reference of the BottomSheetBehavior stored
        mBottomSheetBehavior?.let {
            if (it.state == BottomSheetBehavior.STATE_EXPANDED || it.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                it.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                super.onBackPressed()
            }
        } ?: super.onBackPressed()
    }

}