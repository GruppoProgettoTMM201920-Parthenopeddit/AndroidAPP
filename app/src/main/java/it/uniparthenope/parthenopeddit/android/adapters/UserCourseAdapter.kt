package it.uniparthenope.parthenopeddit.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import it.uniparthenope.parthenopeddit.R
import it.uniparthenope.parthenopeddit.model.Course
import kotlinx.android.synthetic.main.cardview_course.view.*

class UserCourseAdapter : RecyclerView.Adapter<UserCourseAdapter.UserCourseViewHolder>() {

    private val courseList: ArrayList<Course> = ArrayList()
    private var listener:UserCourseItemClickListeners? = null

    fun setItemClickListener(listener: UserCourseItemClickListeners) {
        this.listener = listener
    }

    interface UserCourseItemClickListeners {
        fun onBoardClick(board_id: Int?)
    }

    fun addCourse(courseItemList: List<Course>) {
        this.courseList.addAll(courseItemList)
        notifyDataSetChanged()
    }

    fun setCourseList(courseItemList: List<Course>) {
        this.courseList.clear()
        this.courseList.addAll(courseItemList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCourseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cardview_course,
            parent, false)

        return UserCourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserCourseViewHolder, position: Int) {
        val currentItem = courseList[position]

        holder.course_name_textview.text = currentItem.name
        holder.course_enjoyment_rating.text = "%2.1f/5".format(currentItem.average_liking_score)
        holder.course_difficulty_rating.text = "%2.1f/5".format(currentItem.average_difficulty_score)
        holder.liking_rating_bar.rating = currentItem.average_liking_score!!.toFloat()
        holder.difficulty_rating_bar.rating = currentItem.average_difficulty_score!!.toFloat()
        
        if( listener != null ) {
            holder.cardLayout.setOnClickListener {
                listener!!.onBoardClick(currentItem.id)
            }
        }
    }

    override fun getItemCount() = courseList.size

    class UserCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {     //SINGOLO ELEMENTO DELLA LISTA
        val course_name_textview: TextView = itemView.course_name_textview
        val liking_rating_bar:RatingBar = itemView.liking_rating_bar
        val course_enjoyment_rating: TextView = itemView.course_enjoyment_rating
        val difficulty_rating_bar: RatingBar = itemView.difficulty_rating_bar
        val course_difficulty_rating: TextView = itemView.course_difficulty_rating

        val cardLayout: CardView = itemView.container
    }
}