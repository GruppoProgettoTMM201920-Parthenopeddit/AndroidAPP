package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class User (
    val id: String,
    val display_name: String? = null,
    val registered_on: String,

    var published_content: ArrayList<Content>? = null,
    var published_posts: ArrayList<Post>? = null,
    var published_comments: ArrayList<Comment>? = null,
    var published_reviews: ArrayList<Review>? = null,
    var liked_content: ArrayList<Content>? = null,
    var disliked_content: ArrayList<Content>? = null,
    var followed_courses: ArrayList<Course>? = null,
    var groups: ArrayList<Group>? = null,
    var chats_with_users: ArrayList<UsersChat>? = null,
    var group_invites: ArrayList<GroupInvite>? = null
) : JSONConvertable {

    /*
        TODO
        sent_messages
     */
}