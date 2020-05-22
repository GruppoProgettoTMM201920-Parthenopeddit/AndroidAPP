package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.*
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
    val reviews_table = ArrayList<Review>()
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
            nome_visualizzato = "gaussjr",
            registrato_il = "2014-08-18 21:11:35.537000"
        )

        val p1 = Post(
            id=1,
            title = "Hello World!",
            group = "Generale",
            group_type = 0,
            timestamp = "10:22",
            author_id = u1.id,
            author = u1
        )
        val p2 = Post(
            id=2,
            title = "My title is awesome",
            group = "Terminali Mobili 2020",
            group_type = 1,
            body = "And my body too",
            timestamp = "9:50",
            author_id = u2.id,
            author = u2
        )
        val p3 = Post(
            id=3,
            title = "User1 second post",
            group = "Studenti L-21",
            group_type = 2,
            body = "body post body post body post body post body post body post ",
            timestamp = "4:37",
            author_id = u1.id,
            author = u1
        )

        val p4 = Post(
            id=4,
            title = "User1 third post",
            group = "Generale",
            group_type = 0,
            body = "test test",
            timestamp = "5/5/2055",
            author_id = u1.id,
            author = u1
        )

        val p5 = Post(
            id=5,
            title = "My title is awesome",
            group = "CS Memes",
            group_type = 2,
            body = "And my body too",
            timestamp = "1/1/9999",
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

        val r1 = Review(
            id = 1,
            title = "Ottimo corso",
            body = "Ricetta della pasta e patate: 1/2kg di pasta mista, 1/2kg di patate, pomodorini qb, sale extra vergine d'oliva, sale",
            timestamp = "22/05/2020",
            author_id = "user1",
            score_liking = 5,
            score_difficulty = 2,
            reviewed_course_id = 1
        )

        val r2 = Review(
            id = 2,
            title = "Troppe dimostrazioni",
            body = "Non puoi criticare chi mangia la pizza sull'ananas se poi mangi quella merda di prosciutto e melone",
            timestamp = "22/05/2020",
            author_id = "user1",
            score_liking = 2,
            score_difficulty = 5,
            reviewed_course_id = 1
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
        reviews_table.addAll(listOf(r1,r2))

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