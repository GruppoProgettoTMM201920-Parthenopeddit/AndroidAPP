package it.uniparthenope.parthenopeddit.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import it.uniparthenope.parthenopeddit.R
import kotlinx.android.synthetic.main.fragment_profile.*


//TO-DO: implementare PreferenceFragmentCompat
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

            fab_new_username.setOnClickListener {
                
            }

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
