package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Course(
    id: Int,
    name: String,
    posts: ArrayList<Post>? = null,
    posts_num: Int? = null
) : Board(id, name,
    "course",
    posts, posts_num
), JSONConvertable