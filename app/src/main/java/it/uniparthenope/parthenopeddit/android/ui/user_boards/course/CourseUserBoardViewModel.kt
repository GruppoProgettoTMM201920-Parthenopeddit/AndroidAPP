package it.uniparthenope.parthenopeddit.android.ui.user_boards.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CourseUserBoardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is course Fragment"
    }
    val text: LiveData<String> = _text
}