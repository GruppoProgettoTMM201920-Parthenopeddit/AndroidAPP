package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList


class Review(
    id: Int,
    body: String? = null,
    timestamp: Date,
    author_id: String,

    @SerializedName("reviewed_course_id")
    val reviewed_course_id: Int,

    @SerializedName("score_liking")
    val score_liking: Int,

    @SerializedName("score_difficulty")
    val score_difficulty: Int

) : Content(id, body, timestamp, author_id), JSONConvertable {

    /* relationships */

    @SerializedName("reviewed_course")
    var reviewed_course: Course? = null
}