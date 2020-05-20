package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Chat
import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.User
import java.util.*
import kotlin.collections.ArrayList

/**
 * Contaner class for MOCK data
 */
class MockDatabase private constructor() {

    /*Singleton*/
    private object HOLDER {
        val INSTANCE = MockDatabase()
    }
    companion object {
        val instance: MockDatabase by lazy { HOLDER.INSTANCE }
    }

    val users_table = ArrayList<User>()
    val posts_table = ArrayList<Post>()
    val comments_table = ArrayList<Comment>()
    val chat_table = ArrayList<Chat>()

    init {
        /* Popolamento tabelle fittizie. */

        val u1 = User(
            id = "user1",
            nome_visualizzato = "NoobMaster69",
            registrato_il = "2014-08-18 21:11:35.537000"
        )
        val u2 = User(
            id = "user2",
            registrato_il = "2014-08-18 21:11:35.537000"
        )

        val p1 = Post(
            id=1,
            title = "Hello World!",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u1.id,
            author = u1
        )
        val p2 = Post(
            id=2,
            title = "My title is awesome",
            body = "And my body too",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u2.id,
            author = u2
        )
        val p3 = Post(
            id=3,
            title = "User1 second post",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u1.id,
            author = u1
        )

        val p4 = Post(
            id=4,
            title = "User1 second post",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u1.id,
            author = u1
        )

        val p5 = Post(
            id=5,
            title = "My title is awesome",
            body = "And my body too",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u2.id,
            author = u2
        )

        u1.posts = ArrayList<Post>(listOf(p1,p3,p4))
        u1.posts_num = u1.posts?.size
        u2.posts = ArrayList<Post>(listOf(p2,p5))
        u2.posts_num = u2.posts?.size

        val c1 = Comment(
            id = 1,
            body = "First!1!1!1",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u2.id,
            commented_post_id = 1,
            author = u2,
            commented_post = p1
        )
        val c2 = Comment(
            id = 2,
            body = "Second comment",
            timestamp = "2014-08-18 21:11:35.537000",
            author_id = u1.id,
            commented_comment_id = 1,
            author = u1,
            commented_comment = c1
        )

        u2.comments = ArrayList<Comment>(listOf(c1))
        u1.comments = ArrayList<Comment>(listOf(c2))

        p1.comments = ArrayList<Comment>(listOf(c1))
        c1.comments = ArrayList<Comment>(listOf(c2))
        c1.comments_num = c1.comments?.size
        c2.comments_num = 0

        users_table.addAll(listOf(u1,u2))
        posts_table.addAll(listOf(p1,p2,p3,p4,p5))
        comments_table.addAll(listOf(c1,c2))

        val chat1 = Chat(
            user_id = 1,
            username = "Pippo",
            last_message = "Ciao bello, hai ganja?",
            date = "2019/90/89-89:00:00Z",
            author = u1
        )
        chat_table.add(chat1)
    }
}