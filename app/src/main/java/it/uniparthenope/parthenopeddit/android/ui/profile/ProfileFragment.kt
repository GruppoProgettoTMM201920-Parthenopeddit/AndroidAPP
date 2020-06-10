package it.uniparthenope.parthenopeddit.android.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User
import kotlinx.android.synthetic.main.change_username_dialog.view.*
import kotlinx.android.synthetic.main.fragment_profile.*

private val sharedPrefFile = "kotlinsharedpreference"

class ProfileFragment : Fragment(), PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    private lateinit var auth : AuthManager
    private lateinit var profileViewModel: ProfileViewModel

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private val max_username_length = 20

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = (activity as BasicActivity).app.auth

        sharedPreferences = requireContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences.getString("USERNAME","Username")
        val sharedImageValue = (sharedPreferences.getString("user_image_uri", "0"))
        val username_textview = root.findViewById<TextView>(R.id.username_textview)
        username_textview.text = auth.username

        val imageUri = Uri.parse(sharedImageValue?:"")

        profileViewModel.text.observe(viewLifecycleOwner, Observer {

            username_shown_textview.text = sharedNameValue
            if(sharedImageValue == "0"){ user_image.setImageDrawable(resources.getDrawable(R.drawable.default_user_image)) } else {
                user_image.setImageURI(imageUri)
            }


            fab_new_username.setOnClickListener {
                setUsername()
            }

            fab_user_image.setOnClickListener{
                if(checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //PERMISSION DENIED
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                    } else{
                    //PERMISSION ALREADY GRANTED
                    pickImageFromGallery()
                }
            }


        })
        return root
    }

    private fun setUsername(){

        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.change_username_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setTitle("Nuovo username")
        val  mAlertDialog = mBuilder.show()
        mDialogView.dialogDoneBtn.setOnClickListener {
            val username = mDialogView.dialogUsernameEt.text.toString()
            val error_textview = mDialogView.error_textview
            if(username.isBlank()) {
                error_textview.visibility = View.VISIBLE
                return@setOnClickListener
            }
            if(username.length > max_username_length){
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Username troppo lungo")
                builder.setMessage("Il nome utente non può superare i ${max_username_length} caratteri.")
                builder.setPositiveButton("OK", null)
                val dialog = builder.create()
                dialog.show()
                return@setOnClickListener
            }


            username_shown_textview.setText(username)

            val auth = (activity as BasicActivity).app.auth
            UserRequests(requireContext(), auth).setDisplayName(
                username, {it: User ->
                    Toast.makeText(requireContext(), "Hai correttamente cambiato nome utente", Toast.LENGTH_SHORT).show()
                }, { it: String ->
                    Toast.makeText(requireContext(),"Errore ${it}", Toast.LENGTH_LONG).show()
                }
            )

            //SAVE PREFERENCE
            editor = sharedPreferences.edit()
            editor.putString("USERNAME",username)
            editor.apply()
            editor.commit()
            mAlertDialog.dismiss()
        }

        mDialogView.dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
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
            sharedPreferences = requireContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

            var imageUri = data?.data
            var imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri);
            //user_image.setImageURI(data?.data)
            editor =  sharedPreferences.edit()
            //editor.putString("user_image_uri", data?.data.toString())
            editor.apply()
            editor.commit();


            /*
            sharedPreferences = requireContext().getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)

            user_image.setImageURI(data?.data)

            var imageUri = data?.data
            var imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)

            var outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            var byteArray: ByteArray = outputStream.toByteArray()
            var encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT)

            editor =  sharedPreferences.edit()
            //editor.putString("user_image_uri", data?.data.toString())
            editor.putString("user_image", encodedString)
            editor.apply()
            editor.commit();
             */



        }
    }

}
