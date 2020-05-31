package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.*

class MockApiData : AuthNamespace, PostNamespace, CommentsNamespace, ReviewNamespace, ChatNamespace, GroupNamespace {
    override fun login(
        username: String,
        password: String,
        completion: (user: User?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getPost(
        token: String,
        postId: Int,
        completion: (post: Post?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllPost(
        token: String,
        completion: (postList: List<Post>?, error: String?) -> Unit
    ) {
        completion.invoke(MockDatabase.instance.posts_table, null)
    }

    override fun publishNewPost(
        token: String,
        title: String,
        body: String?,
        completion: (post: Post?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getPostWithComments(
        token: String,
        postId: Int,
        completion: (post: Post?, error: String?) -> Unit
    ) {
        for (post in MockDatabase.instance.posts_table) {
            if (post.id == postId) {
                completion.invoke(post, null)
                return
            }
        }
        completion.invoke(null, "no post with id $postId")
    }

    override fun getGroupPost(
        token: String,
        id_group: Int,
        completion: (postList: List<Post>?, error: String?) -> Unit
    ) {
        completion.invoke(
            MockDatabase.instance.posts_table.filter { it.posted_to_board?.id == id_group },
            null
        )
    }

    override fun getGroupInfo(
        token: String,
        id_group: Int,
        completion: (name: String?, num_members: Int?, created: String?, members: ArrayList<GroupMember>?, error: String?) -> Unit
    ) {
        var group : Group = MockDatabase.instance.group_table.filter { it.id == id_group }.single()
        if(group != null) {
            completion.invoke(
                group.name,
                group.members_num,
                group.created_on,
                group.members,
                null
            )
        } else{
            completion.invoke(null,null,null,null,"Gruppo non trovato")
        }
        return
    }

    override fun getComment(
        token: String,
        commentId: Int,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getUserComment(
        token: String,
        userId: String,
        completion: (commentList: List<Comment>?, error: String?) -> Unit
    ) {
        completion.invoke(
            MockDatabase.instance.comments_table.filter { it.author?.id == userId },
            null
        )
    }

    override fun getCommentWithComments(
        token: String,
        commentId: Int,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun publishCommentToPost(
        token: String,
        postId: Int,
        body: String,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun publishCommentToComment(
        token: String,
        commentId: Int,
        body: String,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getAllReview(
        token: String,
        completion: (reviewList: List<Review>?, error: String?) -> Unit
    ) {
        completion.invoke(MockDatabase.instance.reviews_table, null)
    }

    override fun getCourseInfo(
        token: String,
        courseId: Int,
        completion: (courseRating: Float, courseDifficulty: Float, numReviews: Int, courseName: String?, error: String?) -> Unit
    ) {
        for (course in MockDatabase.instance.course_table) {
            if (course.id == courseId) {
                completion.invoke(course.average_liking_score!!.toFloat(), course.average_difficulty_score!!.toFloat(), course.reviews_count!!, course.name, null)
                return
            }
        }
        completion.invoke(0F, 0F, 0,"no course with id $courseId", "no course with id $courseId")
    }

    override fun getCourseReviews(
        token: String,
        courseId: Int,
        completion: (reviewList: List<Review>?, error: String?) -> Unit
    ) {
        for(course in MockDatabase.instance.course_table) {
            if (course.id == courseId) {
                completion.invoke(course.reviews, null)
                return
            }
        }

        completion.invoke(null, "course not found")
    }

    override fun getChat(
        token: String,
        userId: Int,
        completion: (chat: ArrayList<UsersChat>?, error: String?) -> Unit
    ) {
        var user_chats : ArrayList<UsersChat>? = ArrayList()
        for(chat in MockDatabase.instance.chats_table){
            if(chat.of_user_id == "user1"){
                user_chats?.add(chat)
            }
        }

        if(user_chats?.isEmpty()!!){
            completion.invoke(null, "L'utente non ha chat")
        } else {
            completion.invoke(user_chats, null)
        }
        return
    }

    override fun getChatMessages(
        token: String,
        user1Id: String,
        user2Id: String,
        completion: (chat: ArrayList<Message>?, error: String?) -> Unit
    ) {
        for(messagelog in MockDatabase.instance.messagelog_table){
            if (messagelog.user1_id == user1Id && messagelog.user2_id == user2Id) {
                completion.invoke(messagelog.messages, null)
                return
            }
        }
        completion.invoke(null, "Questo utente non ha chat con questo destinatario")
        return
    }

}