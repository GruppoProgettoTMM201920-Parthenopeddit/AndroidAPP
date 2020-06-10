package it.uniparthenope.parthenopeddit.android.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.*
import it.uniparthenope.parthenopeddit.api.requests.UserRequests
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.User
import it.uniparthenope.parthenopeddit.util.toGson
import kotlinx.android.synthetic.main.change_username_dialog.view.*


class ProfileFragment : Fragment() {

    private lateinit var auth : AuthManager

    private lateinit var user_id: String
    private lateinit var user: User

    private val max_username_length = 20

    private lateinit var username_textview: TextView
    private lateinit var username_shown_textview: TextView
    private lateinit var user_image: ImageView
    private lateinit var user_activities_layout: LinearLayout
    private lateinit var user_boards_layout: LinearLayout
    private lateinit var user_invites_layout: LinearLayout
    private lateinit var user_chat_layout: LinearLayout

    private lateinit var fab_new_username: FloatingActionButton
    private lateinit var fab_user_image: FloatingActionButton

    private lateinit var logout_label: TextView
    private lateinit var logout_button: ImageButton
    private lateinit var settings_button: ImageButton
    private lateinit var info_label: TextView
    private lateinit var info_button: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        auth = (activity as BasicActivity).app.auth

        val extras = (activity as BasicActivity).intent.extras
        user_id = extras?.getString("id_user") ?: auth.username!!

        username_textview = root.findViewById(R.id.username_textview)
        username_textview.text = user_id

        username_shown_textview = root.findViewById(R.id.username_shown_textview)
        username_shown_textview.text = user_id

        UserRequests(requireContext(), auth).getUserByID(
            user_id,
            {
                user = it
                username_shown_textview.text = user.display_name?:user.id
            }, {
                throw RuntimeException(it)
            }
        )

        user_image = root.findViewById(R.id.user_image)
        // TODO Set user image

        user_activities_layout = root.findViewById(R.id.user_activities_layout)
        user_activities_layout.setOnClickListener {
            val intent = Intent(requireContext(), UserContentActivity::class.java)
            intent.putExtra("user_id", user_id)
            startActivity(intent)
        }

        user_boards_layout = root.findViewById(R.id.user_boards_layout)
        user_invites_layout = root.findViewById(R.id.user_invites_layout)
        user_chat_layout = root.findViewById(R.id.user_chat_layout)

        fab_new_username = root.findViewById(R.id.fab_new_username)
        fab_user_image = root.findViewById(R.id.fab_user_image)

        logout_label = root.findViewById(R.id.logout_label)
        logout_button = root.findViewById(R.id.logout_button)
        settings_button = root.findViewById(R.id.settings_button)
        info_label = root.findViewById(R.id.info_label)
        info_button = root.findViewById(R.id.info_button)

        if (user_id == auth.username!!) {
            user_boards_layout.visibility = View.VISIBLE
            user_invites_layout.visibility = View.VISIBLE
            user_chat_layout.visibility = View.GONE

            user_boards_layout.setOnClickListener {
                (activity as BasicActivity).goToActivity(UserBoardsActivity::class.java)
            }
            user_invites_layout.setOnClickListener {
                (activity as BasicActivity).goToActivity(UserGroupInviteActivity::class.java)
            }

            fab_new_username.visibility = View.VISIBLE
            fab_user_image.visibility = View.VISIBLE

            fab_new_username.setOnClickListener {
                setUsername()
            }
            fab_user_image.setOnClickListener {
                //TODO
            }

            logout_label.visibility = View.VISIBLE
            logout_button.visibility = View.VISIBLE
            settings_button.visibility = View.VISIBLE
            info_label.visibility = View.VISIBLE
            info_button.visibility = View.VISIBLE

            logout_label.setOnClickListener {
                auth.logout()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            logout_button.setOnClickListener {
                auth.logout()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            settings_button.setOnClickListener {
                //TODO
            }
            info_label.setOnClickListener {
                (activity as BasicActivity).goToActivity(AboutActivity::class.java)
            }
            info_button.setOnClickListener {
                (activity as BasicActivity).goToActivity(AboutActivity::class.java)
            }
        } else {
            user_boards_layout.visibility = View.GONE
            user_invites_layout.visibility = View.GONE
            user_chat_layout.visibility = View.VISIBLE

            user_chat_layout.setOnClickListener {
                val intent = Intent(requireContext(), ChatActivity::class.java)
                intent.putExtra("user", user.toGson())
                startActivity(intent)
            }

            fab_new_username.visibility = View.GONE
            fab_user_image.visibility = View.GONE

            logout_label.visibility = View.GONE
            logout_button.visibility = View.GONE
            settings_button.visibility = View.GONE
            info_label.visibility = View.GONE
            info_button.visibility = View.GONE
        }
        return root
    }

    private fun setUsername() {
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
            if(username.length > max_username_length) {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Username troppo lungo")
                builder.setMessage("Il nome utente non può superare i ${max_username_length} caratteri.")
                builder.setPositiveButton("OK", null)
                val dialog = builder.create()
                dialog.show()
                return@setOnClickListener
            }

            username_shown_textview.text = username

            val auth = (activity as BasicActivity).app.auth
            UserRequests(requireContext(), auth).setDisplayName(
                username,
                {}, {
                    username_shown_textview.text = user.display_name?:user_id
                }
            )
            mAlertDialog.dismiss()
        }

        mDialogView.dialogCancelBtn.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }
    /***
    /* TODO USE THIS */
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

    **/
     */
}
