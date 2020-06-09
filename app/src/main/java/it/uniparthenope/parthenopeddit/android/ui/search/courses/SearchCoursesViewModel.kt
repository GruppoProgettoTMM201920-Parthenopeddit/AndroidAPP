package it.uniparthenope.parthenopeddit.android.ui.search.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchCoursesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search courses Fragment"
    }
    val text: LiveData<String> = _text
}