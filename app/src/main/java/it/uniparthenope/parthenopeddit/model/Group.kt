package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Group(
    id: Int,
    name: String,
    posts: ArrayList<Post>? = null,
    posts_num: Int? = null,

    val created_on: String,

    var members: ArrayList<GroupMember>? = null,
    var chat: GroupChat? = null,
    var invites: ArrayList<GroupInvite>? = null,
    var members_num: Int? = null
) : Board(id, name,
    "group",
    posts, posts_num
), JSONConvertable