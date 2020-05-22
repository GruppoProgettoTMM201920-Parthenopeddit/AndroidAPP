package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList


class CourseReview(
    id: Int,
    name: String,
    rating: Float,
    num_reviews: Int

) : JSONConvertable {

    /* relationships */

    @SerializedName("reviewed_course")
    var reviewed_course: Course? = null
}