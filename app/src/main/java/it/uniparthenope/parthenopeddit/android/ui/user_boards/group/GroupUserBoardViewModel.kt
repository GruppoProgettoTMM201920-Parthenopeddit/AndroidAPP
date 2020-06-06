package it.uniparthenope.parthenopeddit.android.ui.user_boards.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GroupUserBoardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is group Fragment"
    }
    val text: LiveData<String> = _text
}