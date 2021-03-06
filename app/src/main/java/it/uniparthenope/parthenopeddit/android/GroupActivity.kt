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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.uniparthenope.parthenopeddit.LoginRequiredActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.InfiniteScroller
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.adapters.ReviewAdapter
import it.uniparthenope.parthenopeddit.android.ui.group.BackdropFragment
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.api.requests.CoursesRequests
import it.uniparthenope.parthenopeddit.api.requests.GroupsRequests
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
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

    private lateinit var recycler_view: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var infiniteScroller: InfiniteScroller
    private lateinit var updater: InfiniteScroller.Updater

    private lateinit var transactionStartDateTime: String

    private val per_page = 20

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

        recycler_view = findViewById(R.id.group_recyclerview) as RecyclerView

        postAdapter = PostAdapter()
        postAdapter.setItemClickListener(null) //TODO add listener
        recycler_view.adapter = postAdapter

        layoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager

        updater = object : InfiniteScroller.Updater {
            override fun updateData(pageToLoad: Int, pageSize: Int) {
                GroupsRequests(this@GroupActivity, app.auth).getGroupPosts(
                    page = pageToLoad,
                    per_page = pageSize,
                    group_id = id_group,
                    transactionStartDateTime = transactionStartDateTime,
                    onSuccess = {
                        postAdapter.aggiungiPost(it)
                    },
                    onEndOfContent = {
                        infiniteScroller.theresMore = false
                    },
                    onFail = {
                        Toast.makeText(this@GroupActivity, "ERROR IN FETCHING NEW POSTS", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
        infiniteScroller = InfiniteScroller(
            layoutManager, updater, per_page
        )

        GroupsRequests(this, app.auth).getGroupPosts(
            group_id = id_group,
            per_page = per_page,
            page = 1,
            onSuccess = {
                if(it.isNotEmpty()) {
                    postAdapter.aggiungiPost( it )
                    transactionStartDateTime = it[0].timestamp
                    recycler_view.addOnScrollListener(infiniteScroller)
                }
            },
            onEndOfContent = {
                //nothing
            },
            onFail = {
                Toast.makeText(this,"Errore : ${it}", Toast.LENGTH_LONG).show()
            }
        )

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

        val itemsswipetorefresh = findViewById(R.id.itemsswipetorefresh) as SwipeRefreshLayout

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.white))
        itemsswipetorefresh.setOnRefreshListener {
            itemsswipetorefresh.isRefreshing = true

            GroupsRequests(this, app.auth).getGroupPosts(
                group_id = id_group,
                per_page = per_page,
                page = 1,
                onSuccess = {
                    if(it.isNotEmpty()) {
                        postAdapter.setPostList( it )
                        transactionStartDateTime = it[0].timestamp

                        infiniteScroller = InfiniteScroller(
                            layoutManager, updater, per_page
                        )

                        recycler_view.addOnScrollListener(infiniteScroller)
                    }
                    itemsswipetorefresh.isRefreshing = false
                },
                onEndOfContent = {
                    //nothing
                    itemsswipetorefresh.isRefreshing = false
                },
                onFail = {
                    itemsswipetorefresh.isRefreshing = false
                    Toast.makeText(this,"Errore : ${it}", Toast.LENGTH_LONG).show()
                }
            )
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