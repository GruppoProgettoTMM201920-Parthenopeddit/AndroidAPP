package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Board(
    val id: Int,
    val name: String,
    val type: String,

    var posts: ArrayList<Post>? = null,
    var posts_num: Int? = null
) : JSONConvertable