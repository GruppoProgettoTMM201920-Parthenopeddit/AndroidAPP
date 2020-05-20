package it.uniparthenope.parthenopeddit.android.ui.user_activities.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostActivitiesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is messages Fragment"
    }
    val text: LiveData<String> = _text
}