package it.uniparthenope.parthenopeddit.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mancj.materialsearchbar.MaterialSearchBar
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.android.CommentActivity
import it.uniparthenope.parthenopeddit.android.CourseActivity
import it.uniparthenope.parthenopeddit.android.HomeActivity
import it.uniparthenope.parthenopeddit.android.adapters.PostAdapter
import it.uniparthenope.parthenopeddit.android.ui.newGroup.NewGroupActivity
import it.uniparthenope.parthenopeddit.android.ui.newPost.NewPostActivity
import it.uniparthenope.parthenopeddit.android.ui.newReview.NewReviewActivity
import it.uniparthenope.parthenopeddit.api.MockApiData
import it.uniparthenope.parthenopeddit.auth.Auth
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.Review
import kotlinx.android.synthetic.main.cardview_post.*


class HomeFragment : Fragment(), PostAdapter.PostItemClickListeners {

    private lateinit var recycler_view: RecyclerView
    private lateinit var homeViewModel: HomeViewModel
    var isOpen = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val fab = root.findViewById(R.id.fab) as FloatingActionButton
        val fab_new_post = root.findViewById(R.id.fab_new_post) as FloatingActionButton
        val fab_new_group = root.findViewById(R.id.fab_new_group) as FloatingActionButton
        val fab_new_review = root.findViewById(R.id.fab_new_review) as FloatingActionButton
        val fab_new_post_textview = root.findViewById(R.id.fab_new_post_textview) as TextView
        val fab_new_group_textview = root.findViewById(R.id.fab_new_group_textview) as TextView
        val fab_new_review_textview = root.findViewById(R.id.fab_new_review_textview) as TextView

        /*
        val listHeader = listOf("I tuoi corsi di studio", "I tuoi gruppi")

        val coursesList = listOf("Corso 1","Corso 2","Corso 3","Corso 4")
        val groupsList = listOf("Gruppo1","Gruppo 2","Gruppo 3","Gruppo 4")

        //val groupList = resources.getStringArray(R.array.groups)


        val listChild = HashMap<String, List<String>>()
        listChild.put(listHeader[0], coursesList)
        listChild.put(listHeader[1], groupsList)

        val expandableListAdapter : ExpandableListAdapter = ExpandableListAdapter(requireContext(), listHeader, listChild)
        //root.expandable_list_view.setAdapter(expandableListAdapter)
        expandableListAdapter.notifyDataSetChanged()
        //root.expandable_list_view

         */

        recycler_view = root.findViewById(R.id.recycler_view) as RecyclerView

        val postAdapter = PostAdapter()
        postAdapter.setItemClickListener(this)
        recycler_view.adapter = postAdapter
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.setHasFixedSize(true)

        MockApiData().getAllPost( Auth().token ) { postItemList, error ->
            if( error != null ) {
                Toast.makeText(requireContext(),"Errore : $error", Toast.LENGTH_LONG).show()
            } else {
                postItemList!!

                postAdapter.aggiungiPost( postItemList )
            }
        }

        val rotateClockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clockwise)
        val rotateAnticlockwise = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anticlockwise)

        //MATERIALSEARCHBAR
        val lv = root.findViewById(R.id.search_listview) as ListView
        val searchBar = root.findViewById(R.id.searchBar) as MaterialSearchBar
        searchBar.setHint("Ricerca...")
        searchBar.setSpeechMode(false)

        var galaxies = arrayOf("Sombrero", "Cartwheel", "Pinwheel", "StarBust", "Whirlpool", "Ring Nebular", "Own Nebular", "Centaurus A", "Virgo Stellar Stream", "Canis Majos Overdensity", "Mayall's Object", "Leo", "Milky Way", "IC 1011", "Messier 81", "Andromeda", "Messier 87")

        //ADAPTER
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, galaxies)
        lv.setAdapter(adapter)



        searchBar.setCardViewElevation(10)
        searchBar.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {

            }

        })
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {

            }

            override fun onSearchConfirmed(text: CharSequence) {

            }

            override fun onButtonClicked(buttonCode: Int) {
                when (buttonCode) {
                    MaterialSearchBar.BUTTON_NAVIGATION -> {
                    }
                    MaterialSearchBar.BUTTON_SPEECH -> {
                    }
                    MaterialSearchBar.BUTTON_BACK -> searchBar.closeSearch()
                }
            }
        })


        fab.setOnClickListener{
            if(isOpen){
                fab.startAnimation(rotateClockwise)

                fab_new_post.animate().translationY(200F)
                fab_new_group.animate().translationY(400F)
                fab_new_review.animate().translationY(600F)
                fab_new_post_textview.animate().translationY(200F)
                fab_new_group_textview.animate().translationY(400F)
                fab_new_review_textview.animate().translationY(600F)
                fab_new_post_textview.animate().alpha(0F)
                fab_new_group_textview.animate().alpha(0F)
                fab_new_review_textview.animate().alpha(0F)
                fab_new_post_textview.visibility = View.GONE
                fab_new_group_textview.visibility = View.GONE
                fab_new_review_textview.visibility = View.GONE



                isOpen = false
            } else{
                fab.startAnimation(rotateAnticlockwise)

                fab_new_post.animate().translationY(-200F)
                fab_new_group.animate().translationY(-400F)
                fab_new_review.animate().translationY(-600F)

                fab_new_post_textview.animate().translationY(-200F)
                fab_new_group_textview.animate().translationY(-400F)
                fab_new_review_textview.animate().translationY(-600F)

                fab_new_post_textview.visibility = View.VISIBLE
                fab_new_group_textview.visibility = View.VISIBLE
                fab_new_review_textview.visibility = View.VISIBLE

                fab_new_post_textview.animate().alpha(1F)
                fab_new_group_textview.animate().alpha(1F)
                fab_new_review_textview.animate().alpha(1F)
                isOpen = true
            }
        }

        fab_new_post.setOnClickListener{ onClickNewPost() }
        fab_new_post_textview.setOnClickListener{ onClickNewPost() }
        fab_new_group.setOnClickListener{ onClickNewGroup() }
        fab_new_group_textview.setOnClickListener{ onClickNewGroup() }
        fab_new_review.setOnClickListener{ onClickNewReview() }
        fab_new_review_textview.setOnClickListener{ onClickNewReview() }

        return root
    }

    override fun onClickLike(id_post: Int) {
        Toast.makeText(requireContext(),"you liked post id[$id_post]", Toast.LENGTH_LONG).show()
        //TODO Send upvote through API
    }

    override fun onClickDislike(id_post: Int) {
        downvote_textview.text = (downvote_textview.text.toString().toInt() + 1).toString()
        //TODO: Send downvote through API
    }

    override fun onClickComments(id_post: Int) {
        val intent = Intent(requireContext(), CommentActivity::class.java)
        intent.putExtra("idPost", id_post)
        startActivity(intent)
    }

    override fun onGroupClick(group_type: Int, id_group: Int) {
        when (group_type){
            0 -> { val intent = Intent(requireContext(), HomeActivity::class.java)        //HOME
                startActivity(intent) }
            1 -> { val intent = Intent(requireContext(), CourseActivity::class.java)        //CORSO
                intent.putExtra("id_group", id_group)
                startActivity(intent) }
            2 -> { Toast.makeText(requireContext(), "Pagina dei gruppi ancora da implementare", Toast.LENGTH_LONG) }          //GRUPPO
            else -> {  }

        }
    }

    fun onClickNewPost(){
        //crea dialogo
            //passi fuonzione da effettuare onSuccess
        //uploiad to api
        //notifidatasetchanged()
        val intent = Intent(requireContext(), NewPostActivity::class.java)
        startActivity(intent)
    }

    fun onClickNewGroup(){
        val intent = Intent(requireContext(), NewGroupActivity::class.java)
        startActivity(intent)
    }

    fun onClickNewReview(){
        val intent = Intent(requireContext(), NewReviewActivity::class.java)
        startActivity(intent)
    }
}
