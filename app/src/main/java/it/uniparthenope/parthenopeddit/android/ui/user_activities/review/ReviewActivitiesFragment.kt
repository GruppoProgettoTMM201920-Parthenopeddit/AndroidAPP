package it.uniparthenope.parthenopeddit.android.ui.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.ChatListAdapter
import it.uniparthenope.parthenopeddit.api.MockDatabase
import kotlinx.android.synthetic.main.fragment_messages.*

class ReviewActivitiesFragment : Fragment() {

    private lateinit var recycler_view: RecyclerView
    private lateinit var reviewactivitiesViewModel: ReviewActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reviewactivitiesViewModel =
            ViewModelProviders.of(this).get(ReviewActivitiesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_review_activities, container, false)
        return root
    }
}
