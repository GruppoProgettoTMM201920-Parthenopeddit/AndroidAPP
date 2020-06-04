package it.uniparthenope.parthenopeddit.api

import android.util.Log
import it.uniparthenope.parthenopeddit.model.*
import kotlinx.android.synthetic.main.chat_fragment.*
import java.sql.Timestamp
import java.text.SimpleDateFormat

class MockApiData : AuthNamespace, PostNamespace, CommentsNamespace, ReviewNamespace, GroupNamespace {
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
        body: String,
        boardId: Int,
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
        completion: (courseRating: Float, courseDifficulty: Float, numReviews: Int, courseName: String?, isFollowed: Boolean, error: String?) -> Unit
    ) {
        var isFollowed: Boolean = false
        for (course in MockDatabase.instance.course_table) {
            if (course.id == courseId) {

                if( MockDatabase.instance.users_table.filter{ it.id == "user1"}.single().followed_courses?.filter { it.id == courseId } != null ){ isFollowed=true }

                completion.invoke(course.average_liking_score!!.toFloat(), course.average_difficulty_score!!.toFloat(), course.reviews_count!!, course.name, isFollowed,null)
                return
            }
        }
        completion.invoke(0F, 0F, 0,"no course with id $courseId", false,"no course with id $courseId")
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

    fun getGroupChat(
        token: String,
        userId: Int,
        completion: (chat: ArrayList<GroupChat>?, error: String?) -> Unit
    ) {
        var group_chats : ArrayList<GroupChat>? = ArrayList()
        var user = MockDatabase.instance.users_table.filter { it.id=="user1" }.single()
        for(chat in MockDatabase.instance.group_chats_table){
            if(user.groups!!.any { it.id == chat.of_group_id }){
                group_chats?.add(chat)
            }
        }

        if(group_chats?.isEmpty()!!){
            completion.invoke(null, "L'utente non ha chat di gruppo")
        } else {
            completion.invoke(group_chats, null)
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

    override fun getUserGroups(
        onSuccess: (groupMemberships: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun createGroup(
        group_name: String,
        invitedUsersIds: ArrayList<String>,
        completion: (groupId: Int, error: String?) -> Unit
    ) {
        var group_id: Int = MockDatabase.instance.board_table.maxBy{ it.id }!!.id + 1
        Log.d("DEBUG", "new id is ${group_id}")
        var group_users: ArrayList<GroupMember> = ArrayList<GroupMember>()

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.toString()

        var newGroup: Group = Group(group_id, group_name, currentDate, ArrayList<GroupMember>(), null, ArrayList<GroupInvite>(), 0)

        for(username in invitedUsersIds){
            var member: User = MockDatabase.instance.users_table.filter { it.id == username }.single()
            member.groups?.add(newGroup)
            if(username == "user1")
                group_users.add(GroupMember(member.id, group_id, currentDate, null, true, member, newGroup))
            else
                group_users.add(GroupMember(member.id, group_id, currentDate, null, false, member, newGroup))

        }

        newGroup.members = group_users
        newGroup.members_num = newGroup.members!!.size
        //TODO: add group chat

        MockDatabase.instance.group_table.add(newGroup)
        MockDatabase.instance.board_table.add(newGroup)
        completion.invoke(group_id,null)
    }

    override fun getUserInvitesToGroup(
        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getGroup(
        group_id: Int,
        onSuccess: (group: Group) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun inviteUsersToGroup(
        group_id: Int,
        invitedUsersIds: List<String>,
        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getGroupInvites(
        group_id: Int,
        onSuccess: (invites: ArrayList<GroupInvite>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun answerGroupInvite(
        group_id: Int,
        accept: Boolean,
        onDecline: () -> Unit,
        onAccept: (membership: GroupMember) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun leaveGroup(
        group_id: Int,
        onSuccess: () -> Unit,
        onNewOwnerPromoted: () -> Unit,
        onGroupDisbanded: () -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getGroupMembers(
        group_id: Int,
        onSuccess: (newOwners: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getUserBoard(
        token: String,
        userId: String,
        completion: (boards: ArrayList<Board>?, error: String?) -> Unit
    ) {
        var courses : ArrayList<Course>? = MockDatabase.instance.users_table.filter{it.id == userId}.single().followed_courses
        var groups : ArrayList<Group>? = MockDatabase.instance.users_table.filter{it.id == userId}.single().groups
        var boards : ArrayList<Board>? = ArrayList<Board>()
        if (courses != null) {
            boards?.addAll(courses)
        }
        if (groups != null) {
            boards?.addAll(groups)
        }
        if(boards?.isNotEmpty()!!){
            completion.invoke(boards, null)
        } else{
            completion.invoke(null, "L'utente non segue corsi o gruppi")
        }
    }

    override fun makeMembersOwners(
        group_id: Int,
        invitedUsersIds: List<String>,
        onSuccess: (new_owners: ArrayList<GroupMember>) -> Unit,
        onFail: (error: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    fun getUserCourse(
        token: String,
        userId: String,
        completion: (course: ArrayList<Course>?, error: String?) -> Unit
    ) {
        var courses : ArrayList<Course>? = MockDatabase.instance.users_table.filter{it.id == userId}.single().followed_courses
        if (courses != null) {
            completion.invoke(courses, null)
        } else{
            completion.invoke(null, "L'utente non segue corsi")
        }
    }

    fun getUserGroup(
        token: String,
        userId: String,
        completion: (course: ArrayList<Group>?, error: String?) -> Unit
    ) {
        var group : ArrayList<Group>? = MockDatabase.instance.users_table.filter{it.id == userId}.single().groups
        if (group != null) {
            completion.invoke(group, null)
        } else{
            completion.invoke(null, "L'utente non è iscritto a gruppi")
        }
    }

}