package it.uniparthenope.parthenopeddit.android.ui.search.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchPostsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search posts Fragment"
    }
    val text: LiveData<String> = _text
}