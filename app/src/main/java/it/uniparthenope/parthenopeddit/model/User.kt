package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class User (
    val id: String,
    val display_name: String? = null,
    val registered_on: String
) : JSONConvertable {

    /*
        TODO
    published_content
    liked_content
    disliked_content
    followed_courses
    groups
    sent_messages
    chats_with_users
    group_invites

     */
}