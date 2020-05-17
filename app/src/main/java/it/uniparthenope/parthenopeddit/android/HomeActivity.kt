package it.uniparthenope.parthenopeddit.android

import android.os.Bundle
import android.widget.ListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableListAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.HashMap

class HomeActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_dashboard,
            R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val listHeader = listOf("I tuoi corsi di studio", "I tuoi gruppi")

        val coursesList = listOf("Corso 1","Corso 2","Corso 3","Corso 4")
        val groupsList = listOf("Gruppo1","Gruppo 2","Gruppo 3","Gruppo 4")

        //val groupList = resources.getStringArray(R.array.groups)
        
        
        val listChild = HashMap<String, List<String>>()
        listChild.put(listHeader[0], coursesList)
        listChild.put(listHeader[1], groupsList)

        val expandableListAdapter : ExpandableListAdapter = ExpandableListAdapter(this, listHeader, listChild)

        expandable_list_view.setAdapter(expandableListAdapter)
    }
}
