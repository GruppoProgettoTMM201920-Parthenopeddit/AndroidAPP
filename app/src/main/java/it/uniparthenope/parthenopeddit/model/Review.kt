package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*
import kotlin.collections.ArrayList


class Review(
    id: Int,
    body: String,
    timestamp: String,
    author_id: String,
    author: User,

    val reviewed_course_id: Int,
    val score_liking: Int,
    val score_difficulty: Int

) : Content(id, body, timestamp, author_id, "review"), JSONConvertable {

    /* relationships */
    var reviewed_course: Course? = null
}