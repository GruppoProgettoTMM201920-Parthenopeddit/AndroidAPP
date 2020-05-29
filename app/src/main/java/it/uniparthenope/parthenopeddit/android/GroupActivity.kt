package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import it.uniparthenope.parthenopeddit.R
import kotlinx.android.synthetic.main.activity_group.*

class GroupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group)

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
                num_participants.setOnClickListener { bsb.state = BottomSheetBehavior.STATE_HALF_EXPANDED }

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