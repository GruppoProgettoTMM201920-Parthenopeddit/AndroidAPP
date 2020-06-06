package it.uniparthenope.parthenopeddit.android.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import it.uniparthenope.parthenopeddit.R

class AboutTPAdapter(private val context: Activity, private val libName: ArrayList<String>, private val libAuthor: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.item_email, libName) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.item_email, null, true)

        val dev_textview = rowView.findViewById(R.id.dev_name) as TextView
        val email_textview = rowView.findViewById(R.id.dev_email) as TextView
        val image_imageview = rowView.findViewById(R.id.dev_image) as ImageView

        dev_textview.text = libName[position]
        email_textview.text = libAuthor[position]
        image_imageview.setImageResource(R.drawable.github_logo)

        return rowView
    }
}