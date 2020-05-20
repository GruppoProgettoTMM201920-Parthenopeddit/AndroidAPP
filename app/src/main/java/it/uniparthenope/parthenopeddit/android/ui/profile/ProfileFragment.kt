package it.uniparthenope.parthenopeddit.android.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Keep
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import it.uniparthenope.parthenopeddit.R
import kotlinx.android.synthetic.main.change_username_dialog.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.ByteArrayOutputStream

private val sharedPrefFile = "kotlinsharedpreference"

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
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

        val sharedNameValue = sharedPreferences.getString("USERNAME","Username")

        profileViewModel.text.observe(viewLifecycleOwner, Observer {

            val myImage = decodeBase64(sharedPreferences.getString("imagePreferance","000"))
            username_shown_textview.text = sharedNameValue
            user_image.setImageBitmap(myImage)



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
                        username_shown_textview.setText(username)
                        //TODO: send username to database through API

                        //SAVE PREFERENCE
                        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("USERNAME",username)
                        editor.apply()
                        editor.commit()
                        mAlertDialog.dismiss()
                    } else{
                        error_textview.visibility = View.VISIBLE

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
                        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("namePreferance", "imagename");
                        editor.putString("imagePreferance", encodeTobase64(user_image.drawable.toBitmap()));
                        editor.apply()
                        editor.commit();
                    }
                } else{
                    //OS > 6.0
                    pickImageFromGallery()
                }
            }


        })
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

    fun encodeTobase64(image: Bitmap): String? {
        val immage: Bitmap = image
        val baos = ByteArrayOutputStream()
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val imageEncoded: String = Base64.encodeToString(b, Base64.DEFAULT)
        Log.d("Image Log:", imageEncoded)
        return imageEncoded
    }

    fun decodeBase64(input: String?): Bitmap? {
        val decodedByte = Base64.decode(input, 0)
        return BitmapFactory
            .decodeByteArray(decodedByte, 0, decodedByte.size)
    }

}
