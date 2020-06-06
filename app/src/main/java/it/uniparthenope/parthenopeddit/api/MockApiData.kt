package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.*

class MockApiData {
    fun login(
        username: String,
        password: String,
        completion: (user: User?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getPost(
        token: String,
        postId: Int,
        completion: (post: Post?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getAllPost(
        token: String,
        completion: (postList: List<Post>?, error: String?) -> Unit
    ) {
        completion.invoke(MockDatabase.instance.posts_table, null)
    }

    fun publishNewPost(
        token: String,
        title: String,
        body: String?,
        completion: (post: Post?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getPostWithComments(
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

    fun getGroupPost(
        token: String,
        id_group: Int,
        completion: (postList: List<Post>?, error: String?) -> Unit
    ) {
        completion.invoke(
            MockDatabase.instance.posts_table.filter { it.posted_to_board?.id == id_group },
            null
        )
    }

    fun getGroupInfo(
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

    fun getComment(
        token: String,
        commentId: Int,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getUserComment(
        token: String,
        userId: String,
        completion: (commentList: List<Comment>?, error: String?) -> Unit
    ) {
        completion.invoke(
            MockDatabase.instance.comments_table.filter { it.author?.id == userId },
            null
        )
    }

    fun getCommentWithComments(
        token: String,
        commentId: Int,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun publishCommentToPost(
        token: String,
        postId: Int,
        body: String,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun publishCommentToComment(
        token: String,
        commentId: Int,
        body: String,
        completion: (comment: Comment?, error: String?) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getAllReview(
        token: String,
        completion: (reviewList: List<Review>?, error: String?) -> Unit
    ) {
        completion.invoke(MockDatabase.instance.reviews_table, null)
    }

    fun getCourseInfo(
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

    fun getCourseReviews(
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

    fun getChat(
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

    fun getChatMessages(
        token: String,
        user1Id: String,
        user2Id: String,
        completion: (chatLog: ChatLog?, error: String?) -> Unit
    ) {
        lateinit var chatUser1: UsersChat
        lateinit var otherUserChat : UsersChat
        for ( usersChat in MockDatabase.instance.chats_table ) {
            if(usersChat.of_user_id == user1Id && usersChat.other_user_chat!!.of_user_id == user2Id) {
                chatUser1 = usersChat
                otherUserChat = usersChat.other_user_chat!!
            }
        }

        val logMessaggi : ArrayList<MessageLog> = ArrayList()

        for( message in MockDatabase.instance.messages_table ) {
            if( message.sender_id == user1Id && message.receiver_id == otherUserChat.id ) {
                logMessaggi.add( MessageLog( message, true, true ) )
            } else if( message.sender_id == user2Id && message.receiver_id == chatUser1.id ) {
                logMessaggi.add( MessageLog( message, false, true ) )
            }
        }

        var chatLog = ChatLog( logMessaggi, otherUserChat.of_user!! )
        completion.invoke(chatLog, null)
        return
    }

    fun newMessage(
        sender: String,
        receiver: String,
        body: String,
        timestamp: String,
        completion: (messageLog: MessageLog, error: String?) -> Unit
    ) {
        lateinit var senderUser: User
        lateinit var receiverUser: User
        for ( user in MockDatabase.instance.users_table ) {
            if(user.id == sender) {
                senderUser = user
            } else if(user.id == receiver) {
                receiverUser = user
            }
        }

        lateinit var senderUserChat: UsersChat
        lateinit var receiverUserChat : UsersChat
        for ( usersChat in MockDatabase.instance.chats_table ) {
            if(usersChat.of_user_id == sender && usersChat.other_user_chat!!.of_user_id == receiver) {
                senderUserChat = usersChat
                receiverUserChat = usersChat.other_user_chat!!
            }
        }

        val newMessage = Message(
            id = MockDatabase.instance.messages_table.size +1,
            body = body,
            timestamp = timestamp,
            sender_id = sender,
            sender_user = senderUser,
            receiver_chat = receiverUserChat,
            receiver_id = receiverUserChat.id
        )

        MockDatabase.instance.messages_table.add(newMessage)

        completion.invoke(MessageLog(
            message = newMessage,
            inviato = true,
            letto = true
        ), null)
    }
}