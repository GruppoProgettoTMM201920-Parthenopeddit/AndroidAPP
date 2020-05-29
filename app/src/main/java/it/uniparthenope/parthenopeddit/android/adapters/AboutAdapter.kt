package it.uniparthenope.parthenopeddit.android.adapters

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import it.uniparthenope.parthenopeddit.R

class AboutAdapter(private val context: Activity, private val devs: ArrayList<String>, private val email: ArrayList<String>, private val image: ArrayList<Int>)
    : ArrayAdapter<String>(context, R.layout.item_email, devs) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.item_email, null, true)

        val dev_textview = rowView.findViewById(R.id.dev_name) as TextView
        val email_textview = rowView.findViewById(R.id.dev_email) as TextView
        val image_imageview = rowView.findViewById(R.id.dev_image) as ImageView

        dev_textview.text = devs[position]
        email_textview.text = email[position]
        image_imageview.setImageResource(image[position])

        return rowView
    }
}