package it.uniparthenope.parthenopeddit.android

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.AboutDevsAdapter
import it.uniparthenope.parthenopeddit.android.adapters.AboutTPAdapter
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.title = "Informazioni"

        //DEVS ADAPTER
        var devs = ArrayList<String>(listOf("Francesco Bottino", "Marco Sautto"))
        var email = ArrayList<String>(listOf("francesco.bottino001@studenti.uniparthenope.it", "marco.sautto@gmail.com"))
        var image = ArrayList<Int>(listOf(R.drawable.default_user_image, R.drawable.default_user_image))

        val aboutDevsAdapter = AboutDevsAdapter(this,devs,email,image)
        listView.adapter = aboutDevsAdapter
        val header_listview = TextView(this)
        header_listview.text = "Sviluppatori"
        header_listview.setPadding(50,5,0,5)
        header_listview.setTypeface(header_listview.getTypeface(), Typeface.BOLD)
        listView.addHeaderView(header_listview)

        //THIRD-PARTY LIBS ADAPTER
        var libName = ArrayList<String>(listOf("Material Searchbar"))
        var libAuthor = ArrayList<String>(listOf("Mancj"))

        val aboutTPAdapter = AboutTPAdapter(this,libName,libAuthor)
        thirdparty_listView.adapter = aboutTPAdapter
        val header_thirdparty_listView = TextView(this)
        header_thirdparty_listView.text = "Librerie di terze parti"
        header_thirdparty_listView.setPadding(50,5,0,5)
        header_thirdparty_listView.setTypeface(header_thirdparty_listView.getTypeface(), Typeface.BOLD)
        thirdparty_listView.addHeaderView(header_thirdparty_listView)



        listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:") // only email apps should handle this
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_LONG).show()

            if(itemIdAtPos==1L){
                intent.putExtra(Intent.EXTRA_EMAIL, email[1])
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Parthenopeddit")
            } else {
                intent.putExtra(Intent.EXTRA_EMAIL, email[0])
                intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Parthenopeddit")
            }
            startActivity(intent)

        }

        thirdparty_listView.setOnItemClickListener(){adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val intent = Intent(Intent.ACTION_VIEW)

            startActivity(intent)

            if(itemIdAtPos==0L){
                intent.data = Uri.parse("https://github.com/mancj/MaterialSearchBar")
            } else {
                //Other libs
            }
            startActivity(intent)

        }

    }
}