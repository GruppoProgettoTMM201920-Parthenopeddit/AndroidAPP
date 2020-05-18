package it.uniparthenope.parthenopeddit.android.ui.settings


import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import it.uniparthenope.parthenopeddit.R


class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
        }
}
