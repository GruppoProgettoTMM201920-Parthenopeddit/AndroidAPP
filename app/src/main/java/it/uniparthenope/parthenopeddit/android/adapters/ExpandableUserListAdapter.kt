package it.uniparthenope.parthenopeddit.android.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.util.DateParser
import java.util.*


class ExpandableUserListAdapter(private val context: Context, private val glide: RequestManager, private var listener: UserClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemList: MutableList<Item> = Collections.emptyList()

    interface UserClickListener {
        fun onUserClicked(userId: String)
    }

    companion object {
        const val HEADER = 0
        const val CONTENT = 1
        const val FOOTER = 2
        const val GROUP_CONTENT = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var holder: RecyclerView.ViewHolder? = null
        when (viewType) {
            HEADER -> holder =
                HeaderViewHolder(
                    inflater.inflate(
                        R.layout.item_header,
                        parent,
                        false
                    )
                )
            CONTENT -> holder =
                ContentViewHolder(
                    inflater.inflate(
                        R.layout.item_content,
                        parent,
                        false
                    )
                )
            FOOTER -> holder =
                FooterViewHolder(
                    inflater.inflate(
                        R.layout.item_footer,
                        parent,
                        false
                    )
                )
            GROUP_CONTENT -> holder =
                ContentGroupViewHolder(
                    inflater.inflate(
                        R.layout.item_content,
                        parent,
                        false
                    )
                )
        }
        return holder ?: throw IllegalStateException("Item type unspecified.")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            val item = itemList[holder.adapterPosition]
            when (item.type) {
                HEADER -> {
                    bindHeader(holder as HeaderViewHolder, item)
                }
                CONTENT -> {
                    bindContent(holder as ContentViewHolder, item)
                }
                FOOTER -> {
                    bindFooter(holder as FooterViewHolder, item)
                }
                GROUP_CONTENT -> {
                    bindGroupContent(holder as ContentGroupViewHolder, item)
                }
            }
        }
    }

    fun getItem(position: Int) : Item{
        return itemList[position]
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemList[position].type
    }

    fun setData(itemList: List<Item>) {
        this.itemList = itemList.toMutableList()
        notifyDataSetChanged()
    }

    fun add(position: Int, item: Item) {
        itemList.add(position, item)
        notifyItemInserted(position)

        var i = position - 1
        while( itemList[i].type != HEADER ) {
            i--
        }

        itemList[i].num = (itemList[i].num!!.toInt() + 1).toString()
        notifyItemChanged(i)
    }

    fun remove(position: Int): Item {

        val removedItem = itemList.removeAt(position)
        notifyItemRemoved(position)

        var i = position - 1
        while( itemList[i].type != HEADER ) {
            i--
        }

        itemList[i].num = (itemList[i].num!!.toInt() - 1).toString()
        notifyItemChanged(i)

        return removedItem
    }

    private fun bindHeader(holder: HeaderViewHolder, item: Item) {

        if (item.isOpened) {
            holder.arrow.setImageResource(R.drawable.ic_downvote_red_24dp)
        } else {
            holder.arrow.setImageResource(R.drawable.ic_upvote_themecolor_24dp)
        }

        holder.title.text = item.title
        holder.num.text = item.num
        holder.container.setOnClickListener {
            it.isClickable = false
            if (item.isOpened) {
                shrinkContents(holder.adapterPosition + 1)
            } else {
                expandContents(holder.adapterPosition + 1)
            }
            rotateArrow(holder, item, it)
        }
    }

    private fun rotateArrow(holder: HeaderViewHolder, item: Item, container: View) {
        val fromRotation = 0f
        val toRotation = 180f
        val rotateAnim = RotateAnimation(
            fromRotation, toRotation,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f,
            RotateAnimation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnim.duration = 300
        rotateAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                if (item.isOpened) {
                    holder.arrow.clearAnimation()
                    holder.arrow.setImageResource(R.drawable.ic_upvote_themecolor_24dp)
                    item.isOpened = false
                } else {
                    holder.arrow.clearAnimation()
                    holder.arrow.setImageResource(R.drawable.ic_downvote_red_24dp)
                    item.isOpened = true
                }
                container.isClickable = true
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        rotateAnim.fillAfter = true // if false, the animation will reset
        holder.arrow.startAnimation(rotateAnim)
    }

    private fun expandContents(startPosition: Int) {
        var endPosition = startPosition
        while (itemList.size > endPosition && itemList[endPosition].type == CONTENT) {
            itemList[endPosition].isOpened = true
            endPosition++
        }

        notifyItemRangeChanged(startPosition, endPosition - 1)
    }

    private fun shrinkContents(startPosition: Int) {
        var endPosition = startPosition
        while (itemList.size > endPosition && itemList[endPosition].type == CONTENT) {
            itemList[endPosition].isOpened = false
            endPosition++
        }

        notifyItemRangeChanged(startPosition, endPosition - 1)
    }

    private fun bindContent(holder: ContentViewHolder, item: Item) {
        resizeContent(holder, item.isOpened)
        glide.load(item.thumbnailUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.thumbnail)
        holder.username.text = item.diplayed_name?:item.username
        holder.joindate.text = "${item.dateMessage} ${DateParser.prettyParse(item.date!!)}"
        holder.container.setOnClickListener {
            item.username?.let { userId -> listener.onUserClicked(userId) }
        }
    }

    private fun bindGroupContent(holder: ContentGroupViewHolder, item: Item) {
        resizeGroupContent(holder, item.isOpened)
        glide.load(item.thumbnailUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.thumbnail)
        holder.container.setOnClickListener{
        }
    }

    /**
     * this is the expandable trick.
     * resize each item to width 0, height 0, then
     * notifyItemRangeChanged do collapse / expand beautifully with their basic animation.
     */
    private fun resizeContent(holder: ContentViewHolder, isOpened: Boolean) {
        val container = holder.backgroundContainer
        if (isOpened) {
            container.visibility = View.VISIBLE
            container.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            container.visibility = View.GONE
            container.layoutParams = FrameLayout.LayoutParams(0, 0)
        }
    }

    private fun resizeGroupContent(holder: ContentGroupViewHolder, isOpened: Boolean) {
        val container = holder.backgroundContainer
        if (isOpened) {
            container.visibility = View.VISIBLE
            container.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )

        } else {
            container.visibility = View.GONE
            container.layoutParams = FrameLayout.LayoutParams(0, 0)
        }
    }

    private fun bindFooter(holder: FooterViewHolder, item: ExpandableUserListAdapter.Item) {

    }

    // ViewHolder area

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val container: RelativeLayout = itemView.findViewById(  R.id.header_container)
        val title: TextView = itemView.findViewById(R.id.header_title)
        val num: TextView = itemView.findViewById(R.id.header_child_num)
        val arrow: ImageView = itemView.findViewById(R.id.header_arrow)
    }

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val backgroundContainer: FrameLayout =
            itemView.findViewById(R.id.content_background_container)
        val container: ConstraintLayout = itemView.findViewById(R.id.content_container)
        val thumbnail: ImageView = itemView.findViewById(R.id.content_image)
        val username: TextView = itemView.findViewById(R.id.content_username_textview)
        val joindate: TextView = itemView.findViewById(R.id.content_joindate_textview)
    }

    class ContentGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val backgroundContainer: FrameLayout =
            itemView.findViewById(R.id.content_background_container)
        val container: ConstraintLayout = itemView.findViewById(R.id.content_container)
        val thumbnail: ImageView = itemView.findViewById(R.id.content_image)
        val groupname: TextView = itemView.findViewById(R.id.content_groupname_textview)
        val group_user_latest: TextView = itemView.findViewById(R.id.content_username_textview)
        val latest_message: TextView = itemView.findViewById(R.id.content_latestmessage_textview)
        val date: TextView = itemView.findViewById(R.id.content_date_textview)
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.footer_title)
        val content: TextView = itemView.findViewById(R.id.footer_content)
    }


    /**
     * Item object using builder pattern.
     */
    class Item(
        internal val type: Int,
        internal val title: String?,
        internal var num: String? = null,
        internal val thumbnailUrl: String?,
        internal val username: String?,
        internal val diplayed_name: String?,
        internal val dateMessage: String?,
        internal val date: String?,
        internal var isOpened: Boolean
    ) {

        data class Builder(
            private var type: Int = 0,
            private var title: String? = null,
            private var num: String? = null,
            private var thumbnailUrl: String? = null,
            private var username: String? = null,
            private var dateMessage: String? = null,
            private var date: String? = null,
            private var isOpened: Boolean = true,
            private var diplayed_name: String? = null
        ) {

            fun type(type: Int) = apply { this.type = type }
            fun title(title: String) = apply { this.title = title }
            fun num(num: String) = apply { this.num = num }
            fun thumbnailUrl(thumbnailUrl: String) = apply { this.thumbnailUrl = thumbnailUrl }
            fun username(username: String) = apply { this.username = username }
            fun dispayName(name: String) = apply { this.diplayed_name = name }
            fun dateMessage(dateMessage: String) = apply { this.dateMessage = dateMessage }
            fun date(date: String) = apply { this.date = date }
            fun isOpened(isOpened: Boolean) = apply { this.isOpened = isOpened }
            fun build() = Item(
                type,
                title,
                num,
                thumbnailUrl,
                username,
                diplayed_name,
                dateMessage,
                date,
                isOpened
            )
        }
    }
}