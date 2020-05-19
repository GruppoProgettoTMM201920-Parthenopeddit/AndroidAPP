package it.uniparthenope.parthenopeddit.android.ui.profile

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import it.uniparthenope.parthenopeddit.R
import kotlinx.android.synthetic.main.change_username_dialog.*
import kotlinx.android.synthetic.main.change_username_dialog.view.*
import kotlinx.android.synthetic.main.change_username_dialog.view.error_textview
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        profileViewModel.text.observe(viewLifecycleOwner, Observer {

            fab_new_username.setOnClickListener {val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.change_username_dialog, null)
                //AlertDialogBuilder
                val mBuilder = AlertDialog.Builder(requireContext())
                    .setView(mDialogView)
                    .setTitle("Nuovo username")
                val  mAlertDialog = mBuilder.show()
                mDialogView.dialogDoneBtn.setOnClickListener {
                    val username = mDialogView.dialogUsernameEt.text.toString()
                    val error_textview = mDialogView.error_textview
                    if(username.isNotBlank()){
                        username_textview.setText(username)
                        //TODO: send username to database through API
                        mAlertDialog.dismiss()
                    } else{
                        error_textview.visibility = View.VISIBLE
                        Toast.makeText(requireContext(),"ao",Toast.LENGTH_SHORT)

                    }
                }
                mDialogView.dialogCancelBtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }}

            fab_user_image.setOnClickListener{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            //PERMISSION DENIED
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                        } else{
                        //PERMISSION ALREADY GRANTED
                        pickImageFromGallery()
                    }
                } else{
                    //OS > 6.0
                    pickImageFromGallery()
                }
            }

        })

        /*supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
        */
        return root
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        TODO("Not yet implemented")
    }

    @Keep companion object{
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode){
            PERMISSION_CODE ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission from popup grandetd
                    pickImageFromGallery()
                } else{
                    //permission from popup denied
                    Toast.makeText(requireContext(),"Permesso negato", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            user_image.setImageURI(data?.data)
        }
    }

}
