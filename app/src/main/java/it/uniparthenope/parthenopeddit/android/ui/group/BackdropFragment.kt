package it.uniparthenope.parthenopeddit.android.ui.group

import android.graphics.Canvas
import it.uniparthenope.parthenopeddit.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableSwipeAdapter
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.util.SwipeItemTouchHelper
import it.uniparthenope.parthenopeddit.util.SwipeItemTouchListener
import kotlinx.android.synthetic.main.fragment_backdrop.*

class BackdropFragment(): SwipeItemTouchListener, Fragment() {
    private var id_group: Int = 0
    private var name_group: String? = null
    private var created_on_group: String? = null
    private var members_group: ArrayList<GroupMember>? = null
    private var members_num_group: Int? = null


    private val charList : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private lateinit var adapter: ExpandableSwipeAdapter
    private lateinit var expandable_recycler_view: RecyclerView

    companion object {
        private const val HEADER_ITEM_COUNT = 2     //Membri e amministratori
        private const val CONTENT_ITEM_COUNT = 5
        private const val RANDOM_STRING_LENGTH = 10
        private const val MAX_IMAGE_SIZE = 200
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_backdrop, container, false)
        expandable_recycler_view = root.findViewById<RecyclerView>(R.id.expandable_recycler_view)
        expandable_recycler_view.layoutManager = LinearLayoutManager(requireContext())

        // Swipe
        val swipeHelper = SwipeItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(expandable_recycler_view)

        // ExpandableSwipeAdapter
        adapter = ExpandableSwipeAdapter(requireContext(), Glide.with(this))

        return root
    }

        /**
         * get data example.
         * actually, this function should be modified to do some network requests like Retrofit 2.0.
         * after that, add Items to List using builder pattern.
         */
        private fun getData() : List<ExpandableSwipeAdapter.Item> {
            val ret = ArrayList<ExpandableSwipeAdapter.Item>()

            var admin_arraylist: ArrayList<GroupMember> = ArrayList()
            admin_arraylist = members_group?.filter { it.is_owner == true } as ArrayList<GroupMember>
            var user_arraylist: ArrayList<GroupMember> = ArrayList()
            user_arraylist = members_group?.filter { it.is_owner == false } as ArrayList<GroupMember>

            val admin_header = ExpandableSwipeAdapter.Item.Builder()
                .type(ExpandableSwipeAdapter.HEADER)
                .title("Amministratori")
                .num(admin_arraylist.size.toString())
                .build()

            val member_header = ExpandableSwipeAdapter.Item.Builder()
                .type(ExpandableSwipeAdapter.HEADER)
                .title("Membri")
                .num( if(user_arraylist.isNotEmpty()) user_arraylist.size.toString() else "0" )
                .build()

            ret.add(admin_header)
            for(j in 0..(admin_arraylist.size!!-1)) {
                val content = ExpandableSwipeAdapter.Item.Builder()
                    .type(ExpandableSwipeAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(admin_arraylist?.get(j)?.user?.display_name!!)
                    .joindate(admin_arraylist?.get(j)?.join_date!!)
                    .build()

                ret.add(content)
            }

            ret.add(member_header)
            if(user_arraylist.isNotEmpty()) {
                for (j in 0..(user_arraylist.size!! - 1)) {
                    val content = ExpandableSwipeAdapter.Item.Builder()
                        .type(ExpandableSwipeAdapter.CONTENT)
                        .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                        .username(user_arraylist?.get(j)?.user?.display_name!!)
                        .joindate(user_arraylist?.get(j)?.join_date!!)
                        .build()

                    ret.add(content)
                }
            }


            val footer = ExpandableSwipeAdapter.Item.Builder()
                .type(ExpandableSwipeAdapter.FOOTER)
                .title(resources.getString(R.string.footer_header))
                .build()

            ret.add(footer)




            return ret
        }

        private fun generateRandomString(length: Int) : String = (1..length)
            .map { kotlin.random.Random.nextInt(0, charList.size) }
            .map(charList::get)
            .joinToString("")

        private fun generateRandomImageUrl(max: Int) : String = resources.getString(R.string.random_image_url, (1..max).shuffled().first())

        // SwipeItemTouchListener override area

        override fun onSwiped(holder: RecyclerView.ViewHolder, direction: Int) {

            if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                val swipedIndex = holder.adapterPosition
                val removedItem = adapter.remove(swipedIndex)

                val snackBar = Snackbar.make(expandable_recycler_view, resources.getString(R.string.remove, removedItem.username), Snackbar.LENGTH_LONG)
                snackBar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                if(removedItem.type == ExpandableSwipeAdapter.CONTENT) {

                    snackBar.setAction("Undo") {
                        adapter.add(swipedIndex, removedItem)
                    }
                } else if(removedItem.type == ExpandableSwipeAdapter.HEADER) {
                    snackBar.setText(resources.getString(R.string.header, removedItem.title))
                    snackBar.setAction("XD") {
                        snackBar.dismiss()
                    }
                }

                snackBar.show()
            }
        }

        override fun onSelectedChanged(holder: RecyclerView.ViewHolder?, actionState: Int, uiUtil: ItemTouchUIUtil) {
            when(actionState) {
                ItemTouchHelper.ACTION_STATE_SWIPE -> {
                    if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                        uiUtil.onSelected(holder.container)
                    }
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            holder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean,
            uiUtil: ItemTouchUIUtil
        ) {
            when(actionState) {
                ItemTouchHelper.ACTION_STATE_SWIPE -> {
                    if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                        uiUtil.onDraw(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                    }
                }
            }
        }

        override fun onChildDrawOver(
            c: Canvas,
            recyclerView: RecyclerView,
            holder: RecyclerView.ViewHolder?,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean,
            uiUtil: ItemTouchUIUtil
        ) {
            when(actionState) {
                ItemTouchHelper.ACTION_STATE_SWIPE -> {
                    if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                        uiUtil.onDrawOver(c, recyclerView, holder.container, dX, dY, actionState, isCurrentlyActive)
                    }
                }
            }
        }

        override fun clearView(recyclerView: RecyclerView, holder: RecyclerView.ViewHolder, uiUtil: ItemTouchUIUtil) {
            if(holder is ExpandableSwipeAdapter.ContentViewHolder) {
                uiUtil.clearView(holder.container)
            }
        }

    fun updateData(id_group: Int, name_group: String?, created_on_group: String?, members_group: ArrayList<GroupMember>?, members_num_group: Int?){
        this.id_group = id_group
        this.name_group = name_group
        this.created_on_group = created_on_group
        this.members_group = members_group
        this.members_num_group = members_num_group

        backdrop_group_name_textview.text = name_group
        creation_date_textview.text = created_on_group

        expandable_recycler_view.adapter = adapter

        adapter.setData(getData())
    }
}