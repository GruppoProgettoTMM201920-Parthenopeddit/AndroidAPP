package it.uniparthenope.parthenopeddit.android.ui.search.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchUsersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search users Fragment"
    }
    val text: LiveData<String> = _text
}