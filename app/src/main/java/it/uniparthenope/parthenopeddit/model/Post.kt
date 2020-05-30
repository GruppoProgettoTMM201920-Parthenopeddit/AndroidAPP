package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable
import java.util.*

class Post(
    id: Int,
    body: String,
    timestamp: String,
    author_id: String,

    val title: String,
    val posted_to_board_id: Int? = null

) : Content(id, body, timestamp, author_id, "post"), JSONConvertable {

    /* relationships */
    var posted_to_board: Board? = null
}