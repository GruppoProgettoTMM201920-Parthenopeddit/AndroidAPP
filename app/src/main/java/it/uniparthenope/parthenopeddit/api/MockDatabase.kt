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
    val course_table = ArrayList<Course>()
    val reviews_table = ArrayList<Review>()
    val chat_table = ArrayList<Chat>()

    init {
        /* Popolamento tabelle fittizie. */

        val g1 = Group(
            idArg = 0,
            isHeaderArg = false,
            nameArg = "Generale",
            typeArg = 0
        )

        val g2 = Group(
            idArg = 1,
            isHeaderArg = false,
            nameArg = "Terminali Mobili e Multimedialità",
            typeArg = 1
        )

        val g3 = Group(
            idArg = 2,
            isHeaderArg = false,
            nameArg = "Programmazione 3",
            typeArg = 1
        )

        val g4 = Group(
            idArg = 3,
            isHeaderArg = false,
            nameArg = "Tecnologie Web",
            typeArg = 1
        )

        val g5 = Group(
            idArg = 4,
            isHeaderArg = false,
            nameArg = "Studenti L-21",
            typeArg = 2
        )

        val g6 = Group(
            idArg = 5,
            isHeaderArg = false,
            nameArg = "CS Memes",
            typeArg = 2
        )

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
            group = g1,
            timestamp = "10:22",
            author_id = u1.id,
            author = u1
        )
        val p2 = Post(
            id=2,
            title = "My title is awesome",
            group = g2,
            body = "And my body too",
            timestamp = "9:50",
            author_id = u2.id,
            author = u2
        )
        val p3 = Post(
            id=3,
            title = "User1 second post",
            group = g3,
            body = "body post body post body post body post body post body post ",
            timestamp = "4:37",
            author_id = u1.id,
            author = u1
        )

        val p4 = Post(
            id=4,
            title = "User1 third post",
            group = g6,
            body = "test test",
            timestamp = "5/5/2055",
            author_id = u1.id,
            author = u1
        )

        val p5 = Post(
            id=5,
            title = "My title is awesome",
            group = g4,
            body = "And my body too",
            timestamp = "1/1/9999",
            author_id = u2.id,
            author = u2
        )

        val p6 = Post(
            id=5,
            title = "My title is awesome",
            group = g5,
            body = "And my body too",
            timestamp = "1/1/9999",
            author_id = u2.id,
            author = u2
        )

        u1.posts = ArrayList<Post>(listOf(p1,p3,p4))
        u1.posts_num = u1.posts?.size
        u2.posts = ArrayList<Post>(listOf(p2,p5,p6))
        u2.posts_num = u2.posts?.size

        val c1 = Comment(
            id = 1,
            body = "First!1!1!1",
            timestamp = "2014-08-18 21:11:35.537000",
            commented_post_id = 1,
            author = u2,
            commented_post = p1
        )
        val c2 = Comment(
            id = 2,
            body = "Second comment",
            timestamp = "2014-08-18 21:11:35.537000",
            commented_comment_id = 1,
            author = u1,
            commented_comment = c1
        )

        val C1 = Course(
            id = 1,
            course_name = "Terminali Mobili e Multimedialità",
            rating = 4.5F,
            difficulty = 3.0F,
            numReview = 2
        )

        val C2 = Course(
            id = 2,
            course_name = "Programmazione 3",
            rating = 3.4F,
            difficulty = 4.0F,
            numReview = 10
        )

        val C3 = Course(
            id = 3,
            course_name = "Tecnologie Web",
            rating = 4.2F,
            difficulty = 3.5F,
            numReview = 8
        )

        val r1 = Review(
            id = 1,
            title = "Ottimo corso",
            body = "Ricetta della pasta e patate: 1/2kg di pasta mista, 1/2kg di patate, pomodorini qb, sale extra vergine d'oliva, sale",
            timestamp = "22/05/2020",
            author = u1,
            score_liking = 5,
            score_difficulty = 2,
            reviewed_course_id = C1.id
        )

        val r2 = Review(
            id = 2,
            title = "Troppe dimostrazioni",
            body = "Non puoi criticare chi mangia la pizza sull'ananas se poi mangi quella merda di prosciutto e melone",
            timestamp = "22/05/2020",
            author = u2,
            score_liking = 2,
            score_difficulty = 5,
            reviewed_course_id = C1.id
        )

        val r3 = Review(
            id = 3,
            title = "Ottime spiegazioni del professore",
            body = "I Ryzen sono migliori degli Intel",
            timestamp = "23/05/2020",
            author = u1,
            score_liking = 5,
            score_difficulty = 2,
            reviewed_course_id = C2.id
        )

        val r4 = Review(
            id = 4,
            title = "Nobono",
            body = "E' stato Mattarella",
            timestamp = "23/05/2020",
            author = u2,
            score_liking = 3,
            score_difficulty = 5,
            reviewed_course_id = C2.id
        )

        val r5 = Review(
            id = 5,
            title = "Noioso",
            body = "Il prof non si è mai presentato",
            timestamp = "23/05/2020",
            author = u1,
            score_liking = 1,
            score_difficulty = 1,
            reviewed_course_id = C3.id
        )



        u2.comments = ArrayList<Comment>(listOf(c1))
        u1.comments = ArrayList<Comment>(listOf(c2))

        u1.reviews = ArrayList<Review>(listOf(r1,r3,r5))
        u2.reviews = ArrayList<Review>(listOf(r2,r4))

        p1.comments = ArrayList<Comment>(listOf(c1))
        c1.comments = ArrayList<Comment>(listOf(c2))
        c1.comments_num = c1.comments?.size
        c2.comments_num = 0

        C1.reviews = ArrayList<Review>(listOf(r1,r2))
        C2.reviews = ArrayList<Review>(listOf(r3,r4))
        C3.reviews = ArrayList<Review>(listOf(r5))

        users_table.addAll(listOf(u1,u2))
        posts_table.addAll(listOf(p1,p2,p3,p4,p5,p6))
        comments_table.addAll(listOf(c1,c2))
        course_table.addAll(listOf(C1,C2,C3))
        reviews_table.addAll(listOf(r1,r2,r3,r4,r5))

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