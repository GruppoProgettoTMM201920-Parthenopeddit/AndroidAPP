package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class User (
    val id: String,
    val display_name: String? = null,
    val registered_on: String,

    //TODO REMOVE TRANSIENT - CHECK CHAT
    @Transient var published_content: ArrayList<Content>? = null,
    @Transient var published_posts: ArrayList<Post>? = null,
    @Transient var published_comments: ArrayList<Comment>? = null,
    @Transient var published_reviews: ArrayList<Review>? = null,
    @Transient var liked_content: ArrayList<Content>? = null,
    @Transient var disliked_content: ArrayList<Content>? = null,
    @Transient var followed_courses: ArrayList<Course>? = null,
    @Transient var groups: ArrayList<Group>? = null,
    @Transient var chats_with_users: ArrayList<UsersChat>? = null,
    @Transient var group_invites: ArrayList<GroupInvite>? = null
) : JSONConvertable {
    /* TODO
        sent_messages
    */
}