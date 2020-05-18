package it.uniparthenope.parthenopeddit.android.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
                        mAlertDialog.dismiss()
                    } else{
                        error_textview.visibility = View.VISIBLE
                        Toast.makeText(requireContext(),"ao",Toast.LENGTH_SHORT)

                    }
                }
                mDialogView.dialogCancelBtn.setOnClickListener {
                    mAlertDialog.dismiss()
                }}

        })

        /*supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
        */
        return root
    }

    override fun onPreferenceStartFragment(
        caller: PreferenceFragmentCompat?,
        pref: Preference?
    ): Boolean {
        TODO("Not yet implemented")
    }

}
