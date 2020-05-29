package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.AboutAdapter
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var devs = ArrayList<String>(listOf("Francesco Bottino", "Marco Sautto"))
        var email = ArrayList<String>(listOf("francesco.bottino001@studenti.uniparthenope.it", "marco.sautto@gmail.com"))
        var image = ArrayList<Int>(listOf(R.drawable.default_user_image, R.drawable.default_user_image))


        val aboutAdapter = AboutAdapter(this,devs,email,image)
        listView.adapter = aboutAdapter
        val header = TextView(this)
        header.text = "Sviluppatori"
        header.setPadding(50,0,0,0)
        header.setTypeface(header.getTypeface(), Typeface.BOLD);
        listView.addHeaderView(header);

        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()

            if(itemIdAtPos>1){
                intent.putExtra(Intent.EXTRA_EMAIL, email[1])
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Parthenopeddit")
            } else {
                intent.putExtra(Intent.EXTRA_EMAIL, email[0])
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Parthenopeddit")
            }
            startActivity(intent)

        }

    }
}