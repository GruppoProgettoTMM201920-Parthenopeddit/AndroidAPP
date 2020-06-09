package it.uniparthenope.parthenopeddit.android.ui.group

import android.content.Intent
import android.graphics.Canvas
import it.uniparthenope.parthenopeddit.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchUIUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import it.uniparthenope.parthenopeddit.BasicActivity
import it.uniparthenope.parthenopeddit.android.AddMemberActivity
import it.uniparthenope.parthenopeddit.android.adapters.ExpandableSwipeAdapter
import it.uniparthenope.parthenopeddit.api.MockDatabase
import it.uniparthenope.parthenopeddit.auth.AuthManager
import it.uniparthenope.parthenopeddit.model.Group
import it.uniparthenope.parthenopeddit.model.GroupMember
import it.uniparthenope.parthenopeddit.util.SwipeItemTouchHelper
import it.uniparthenope.parthenopeddit.util.SwipeItemTouchListener
import kotlinx.android.synthetic.main.fragment_backdrop.*

class BackdropFragment(): SwipeItemTouchListener, Fragment() {
    private lateinit var group: Group

    private var admin_arraylist: ArrayList<GroupMember> = ArrayList()
    private var user_arraylist: ArrayList<GroupMember> = ArrayList()

    private lateinit var auth: AuthManager

    private lateinit var adapter: ExpandableSwipeAdapter
    private lateinit var expandable_recycler_view: RecyclerView
    private lateinit var add_member_button: Button

    companion object {
        private const val MAX_IMAGE_SIZE = 200
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_backdrop, container, false)

        expandable_recycler_view = root.findViewById(R.id.expandable_recycler_view)
        expandable_recycler_view.layoutManager = LinearLayoutManager(requireContext())

        val swipeHelper = SwipeItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(swipeHelper).attachToRecyclerView(expandable_recycler_view)

        adapter = ExpandableSwipeAdapter(requireContext(), Glide.with(this))

        auth = (activity as BasicActivity).app.auth

        add_member_button = root.findViewById(R.id.add_member_button)
        add_member_button.setOnClickListener {
            if( admin_arraylist.filter{ it.user_id == auth.username!! }.singleOrNull()?.is_owner!! ){
                val intent = Intent(requireContext(), AddMemberActivity::class.java)
                intent.putExtra("id_group", group.id)
                intent.putExtra("name_group",group.name)
                startActivity(intent)
            } else{
                Toast.makeText(requireContext(), "Solo gli amministratori possono aggiungere membri", Toast.LENGTH_LONG).show()
            }
        }
        return root
    }

        /**
         * get data example.
         * actually, this function should be modified to do some network requests like Retrofit 2.0.
         * after that, add Items to List using builder pattern.
         */
        private fun getData() : List<ExpandableSwipeAdapter.Item> {
            val ret = ArrayList<ExpandableSwipeAdapter.Item>()

            admin_arraylist = group.members?.filter { it.is_owner } as ArrayList<GroupMember>
            user_arraylist = group.members?.filter { !it.is_owner } as ArrayList<GroupMember>

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
            for(j in 0..(admin_arraylist.size-1)) {
                val content = ExpandableSwipeAdapter.Item.Builder()
                    .type(ExpandableSwipeAdapter.CONTENT)
                    .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                    .username(admin_arraylist.get(j).user?.display_name!!)
                    .joindate(admin_arraylist.get(j).join_date)
                    .build()

                ret.add(content)
            }

            ret.add(member_header)
            if(user_arraylist.isNotEmpty()) {
                for (j in 0..(user_arraylist.size - 1)) {
                    val content = ExpandableSwipeAdapter.Item.Builder()
                        .type(ExpandableSwipeAdapter.CONTENT)
                        .thumbnailUrl(generateRandomImageUrl(MAX_IMAGE_SIZE))
                        .username(user_arraylist.get(j).user?.display_name!!)
                        .joindate(user_arraylist.get(j).join_date)
                        .build()

                    ret.add(content)
                }
            }

            return ret
        }

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

    fun updateData(group: Group){
        this.group = group

        backdrop_group_name_textview.text = group.name
        creation_date_textview.text = group.created_on

        expandable_recycler_view.adapter = adapter

        adapter.setData(getData())
    }
}