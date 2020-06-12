package it.uniparthenope.parthenopeddit.android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.group.BackdropFragment
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupInvite
import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.util.toObject
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : LoginRequiredActivity() {

    private var isOpen = false
    private lateinit var group: Group
    private lateinit var members: ArrayList<GroupMember>
    private lateinit var invites: ArrayList<GroupInvite>

    private lateinit var fragment: BackdropFragment
    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<View?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)
        fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment) as BackdropFragment

        val extras = intent.extras
        var id_group:Int = extras?.getInt("id_group")?:0

        if(id_group == 0) finish()

        var deserializedGroup:Group? = null

        try {
            deserializedGroup = (extras?.getString("group")?:"").toObject()
        } catch(e:Exception) {}

        if( deserializedGroup != null ) {
            setGroup(deserializedGroup)
            fragment.updateGroupData(deserializedGroup)
        }

        fragment.let {
            // Get the BottomSheetBehavior from the fragment view
            BottomSheetBehavior.from(it.requireView()).let { bsb ->
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

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(null)
        group_recyclerview.adapter = postAdapter
        group_recyclerview.layoutManager = LinearLayoutManager(this)
        group_recyclerview.setHasFixedSize(true)


        val fab = findViewById(R.id.fab) as FloatingActionButton
        val fab_group_chat = findViewById(R.id.fab_group_chat) as FloatingActionButton
        val fab_group_chat_textview = findViewById(R.id.fab_group_chat_textview) as TextView
        val follow_button = findViewById(R.id.follow_button) as Button
        val rotateClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)

        //BUON DIVERTIMENTO CON LE API, FRANCESCO
        GroupsRequests(this, app.auth).getGroup(id_group,{it: Group ->
            setGroup(it)
            fragment.updateGroupData(it)
        },{it: String ->
            Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
        })

        GroupsRequests(this, app.auth).getGroupMembers(id_group,{
            members = it
            fragment.updateMembersData(it)
        },{it: String ->
            Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
        })

        GroupsRequests(this, app.auth).getGroupInvites(id_group,{
            invites = it
            fragment.updateInvitesData(it)
        },{it: String ->
            Toast.makeText(this, "Errore ${it}", Toast.LENGTH_LONG).show()
        })

        GroupsRequests(this, app.auth).getGroupPosts(id_group, 20, 1,{it: ArrayList<Post> ->
            postAdapter.aggiungiPost(it)
        },{it: String ->
            Toast.makeText(this,"Errore : ${it}", Toast.LENGTH_LONG).show()
        })

        fab.setOnClickListener{
            if(isOpen){
                fab.startAnimation(rotateClockwise)

                fab_new_post_group.animate().translationY(200F)
                fab_group_chat.animate().translationY(400F)
                fab_new_post_group_textview.animate().translationY(200F)
                fab_group_chat_textview.animate().translationY(400F)
                fab_new_post_group_textview.animate().alpha(0F)
                fab_group_chat_textview.animate().alpha(0F)
                fab_new_post_group_textview.visibility = View.GONE
                fab_group_chat_textview.visibility = View.GONE

                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)

                fab_new_post_group.animate().translationY(-200F)
                fab_group_chat.animate().translationY(-400F)

                fab_new_post_group_textview.animate().translationY(-200F)
                fab_group_chat_textview.animate().translationY(-400F)

                fab_new_post_group_textview.visibility = View.VISIBLE
                fab_group_chat_textview.visibility = View.VISIBLE

                fab_new_post_group_textview.animate().alpha(1F)
                fab_group_chat_textview.animate().alpha(1F)
                isOpen = true
            }
        }

        fab_new_post_group.setOnClickListener {
            onClickNewPost(group.id, group.name)
        }

        fab_new_post_group_textview.setOnClickListener{
            onClickNewPost(group.id, group.name)
        }

        follow_button.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Sei sicuro di voler uscire?")
                    .setPositiveButton("Esci",
                        DialogInterface.OnClickListener { dialog, id ->

                            GroupsRequests(this, app.auth).leaveGroup(
                                id_group, {
                                    Toast.makeText(
                                        this,
                                        "Hai lasciato il gruppo ${group.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }, {
                                    Toast.makeText(
                                        this,
                                        "Eri l'ultimo admin. Hai abbandonato la nave, sei peggio di Schettino",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }, {
                                    Toast.makeText(
                                        this,
                                        "Eri l'ultimo utente. Il gruppo ${group.name} è stato eliminato",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }, {
                                    Toast.makeText(this, "Errore : ${it}", Toast.LENGTH_LONG).show()
                                }
                            )
                        })
                    .setNegativeButton("Annulla",
                        DialogInterface.OnClickListener { dialog, id ->
                        })

                // Create the AlertDialog object and return it
                builder.create()
                builder.show()

            }
        }


    private fun setGroup(newGroup: Group) {
        group = newGroup
        group_name_textview.text = group.name
        num_members.text = group.members_num.toString()
    }

    private fun onClickNewPost(id_group: Int, name_group: String){
        val intent = Intent(this, NewPostActivity::class.java)
        intent.putExtra("id_group", id_group)
        intent.putExtra("name_group", name_group)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // With the reference of the BottomSheetBehavior stored
        mBottomSheetBehavior.let {
            if (it.state == BottomSheetBehavior.STATE_EXPANDED || it.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                it.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                super.onBackPressed()
            }
        }
    }
}