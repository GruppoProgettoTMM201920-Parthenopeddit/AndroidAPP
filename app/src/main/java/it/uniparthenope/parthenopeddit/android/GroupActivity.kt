package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import kotlinx.android.synthetic.main.activity_course.*
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

        val extras = intent.extras
        var id_group:Int = extras?.getInt("id_group")?:0

        val num_members_textview = findViewById<TextView>(R.id.num_members)

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(null)
        group_recyclerview.adapter = postAdapter
        group_recyclerview.layoutManager = LinearLayoutManager(this)
        group_recyclerview.setHasFixedSize(true)


        MockApiData().getGroupInfo( Auth().token, id_group) { name, num_members, created, members, error ->
            if(error != null){
                //toast
            } else {
                group_name_textview.text = name!!
                if(num_members!=1){ num_members_textview.text = "${num_members} membri" } else { num_members_textview.text = "${num_members} membri" }
            }



        }


        MockApiData().getGroupPost( Auth().token, id_group) { postItemList, error ->
            if( error != null ) {
                Toast.makeText(this,"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                postItemList!!

                postAdapter.aggiungiPost( postItemList )
            }
        }

        configureBackdrop()

    }



    private var mBottomSheetBehavior: BottomSheetBehavior<View?>? = null

    private fun configureBackdrop() {
        // Get the fragment reference
        val fragment = supportFragmentManager.findFragmentById(R.id.filter_fragment)

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