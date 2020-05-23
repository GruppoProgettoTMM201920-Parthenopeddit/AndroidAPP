package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList


class Review(
    id: Int,
    title: String,
    body: String? = null,
    timestamp: String,
    author_id: String,

    @SerializedName("reviewed_course_id")
    val reviewed_course_id: Int,

    @SerializedName("score_liking")
    val score_liking: Int,

    @SerializedName("score_difficulty")
    val score_difficulty: Int

) : Content_temp(id, title, body, timestamp, author_id), JSONConvertable {

    /* relationships */

    @SerializedName("reviewed_course")
    var reviewed_course: Course? = null
}