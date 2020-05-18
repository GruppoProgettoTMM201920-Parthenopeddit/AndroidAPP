package it.uniparthenope.parthenopeddit.android.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.appcompat.widget.AppCompatTextView
import it.uniparthenope.parthenopeddit.R
import kotlinx.android.synthetic.main.list_header.view.*

class ExpandableListAdapter(val context: Context, val listOfHeaderData: List<String>, val listOfChildData: HashMap<String,List<String>>) : BaseExpandableListAdapter() {
    override fun getGroup(position: Int): Any {
        return listOfHeaderData[position]
    }

    override fun isChildSelectable(headerPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val headerTitle = getGroup(groupPosition) as String

        val view: View = LayoutInflater.from(context).inflate(R.layout.list_header,parent,false)

        val listHeaderText = view.findViewById<AppCompatTextView>(R.id.list_header_text) as AppCompatTextView

        listHeaderText.setTypeface(null, Typeface.BOLD)
        listHeaderText.text = headerTitle

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return listOfChildData[listOfHeaderData[groupPosition]]!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return listOfChildData[listOfHeaderData[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val childText = getChild(groupPosition,childPosition) as String
        val view: View = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val listItemText = view.findViewById<AppCompatTextView>(R.id.list_item_text) as AppCompatTextView
        listItemText.text = childText

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return listOfHeaderData.size
    }
}