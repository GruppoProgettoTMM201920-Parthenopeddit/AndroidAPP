package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.Post
import it.uniparthenope.parthenopeddit.model.User

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
        u1.posts = ArrayList<Post>(listOf(p1,p3))
        u1.posts_num = u1.posts?.size
        u2.posts = ArrayList<Post>(listOf(p2))
        u2.posts_num = u2.posts?.size

        users_table.addAll(listOf(u1,u2))
        posts_table.addAll(listOf(p1,p2,p3))
    }
}