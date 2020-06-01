package it.uniparthenope.parthenopeddit.android.ui.group

import it.uniparthenope.parthenopeddit.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupMember
import kotlinx.android.synthetic.main.fragment_backdrop.*

class BackdropFragment(): Fragment() {
    private var id_group: Int = 0
    private var name_group: String? = null
    private var created_on_group: String? = null
    private var members_group: ArrayList<GroupMember>? = null
    private var members_num_group: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_backdrop, container, false)
        return root
    }

    fun updateData(id_group: Int, name_group: String?, created_on_group: String?, members_group: ArrayList<GroupMember>?, members_num_group: Int?){
        this.id_group = id_group
        this.name_group = name_group
        this.created_on_group = created_on_group
        this.members_group = members_group
        this.members_num_group = members_num_group

        backdrop_group_name_textview.text = name_group
        creation_date_textview.text = created_on_group
    }
}