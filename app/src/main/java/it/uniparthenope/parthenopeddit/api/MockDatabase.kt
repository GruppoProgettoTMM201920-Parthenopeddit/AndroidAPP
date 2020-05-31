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
    val group_table = ArrayList<Group>()
    val reviews_table = ArrayList<Review>()
    val chats_table = ArrayList<UsersChat>()

    init {
        /* Popolamento tabelle fittizie. */

        val g1 = Group(
            id = 1,
            name = "Generale",
            created_on = "01/01/1970"
        )

        val g2 = Group(
            id = 2,
            name = "Studenti L-21",
            created_on = "01/01/1970"
        )

        val g3 = Group(
            id = 3,
            name = "CS Memes",
            created_on = "01/01/1970"
        )

        val C1 = Course(
            id = 4,
            name = "Terminali Mobili e Multimedialità",
            average_liking_score = 4.5,
            follower_number = 10,
            average_difficulty_score = 3.0,
            reviews_count = 2
        )

        val C2 = Course(
            id = 5,
            name = "Programmazione 3",
            average_liking_score = 3.4,
            follower_number = 50,
            average_difficulty_score = 4.0,
            reviews_count = 10
        )

        val C3 = Course(
            id = 6,
            name = "Tecnologie Web",
            average_liking_score = 4.2,
            follower_number = 8,
            average_difficulty_score = 3.5,
            reviews_count = 8
        )

        val u1 = User(
            id = "user1",
            display_name = "NoobMaster69",
            registered_on = "2014-08-18 21:11:35.537000"
        )
        val u2 = User(
            id = "user2",
            display_name = "gaussjr",
            registered_on = "2014-08-18 21:11:35.537000"
        )

        val p1 = Post(
            id=1,
            title = "Hello World!",
            body = "This is a test post",
            posted_to_board_id = 1,
            timestamp = "10:22",
            author_id = u1.id,
            author = u1,
            comments_num = 2,
            likes_num = 10,
            dislikes_num = 2,
            posted_to_board = g1
        )
        val p2 = Post(
            id=2,
            title = "My title is awesome",
            body = "And my body too",
            posted_to_board_id = 2,
            timestamp = "9:50",
            author_id = u2.id,
            author = u2,
            posted_to_board = g2
        )
        val p3 = Post(
            id=3,
            title = "User1 second post",
            body = "body post body post body post body post body post body post ",
            posted_to_board_id = 3,
            timestamp = "4:37",
            author_id = u1.id,
            author = u1,
            posted_to_board = g3
        )

        val p4 = Post(
            id=4,
            title = "User1 third post",
            body = "test test",
            posted_to_board_id = 6,
            timestamp = "5/5/2055",
            author_id = u1.id,
            author = u1,
            posted_to_board = C3
        )

        val p5 = Post(
            id=5,
            title = "My title is awesome",
            body = "And my body too",
            posted_to_board_id = 4,
            timestamp = "1/1/9999",
            author_id = u2.id,
            author = u2,
            posted_to_board = C1

        )

        val p6 = Post(
            id=6,
            title = "My title is awesome",
            body = "And my body too",
            posted_to_board_id = 5,
            timestamp = "1/1/9999",
            author_id = u2.id,
            author = u2,
            posted_to_board = C2
        )

        val c1 = Comment(
            id = 7,
            body = "First!1!1!1",
            timestamp = "2014-08-18 21:11:35.537000",
            commented_content_id = 1,
            root_content_id = 1,
            author_id = u2.id,
            author = u2,
            commented_content = p1,
            root_content = p1
        )
        val c2 = Comment(
            id = 8,
            body = "Second comment",
            timestamp = "2014-08-18 21:11:35.537000",
            commented_content_id = 7,
            author_id = u1.id,
            author = u1,
            root_content_id = 1,
            commented_content = c1,
            root_content = p1
        )

        val r1 = Review(
            id = 9,
            body = "Ricetta della pasta e patate: 1/2kg di pasta mista, 1/2kg di patate, pomodorini qb, sale extra vergine d'oliva, sale",
            timestamp = "22/05/2020",
            author = u1,
            author_id = u1.id,
            score_liking = 5,
            score_difficulty = 2,
            reviewed_course_id = C1.id,
            reviewed_course = C1
        )

        val r2 = Review(
            id = 10,
            body = "Non puoi criticare chi mangia la pizza sull'ananas se poi mangi quella merda di prosciutto e melone",
            timestamp = "22/05/2020",
            author = u2,
            author_id = u2.id,
            score_liking = 2,
            score_difficulty = 5,
            reviewed_course_id = C1.id,
            reviewed_course = C1
        )

        val r3 = Review(
            id = 11,
            body = "I Ryzen sono migliori degli Intel",
            timestamp = "23/05/2020",
            author = u1,
            author_id = u1.id,
            score_liking = 5,
            score_difficulty = 2,
            reviewed_course_id = C2.id,
            reviewed_course = C2
        )

        val r4 = Review(
            id = 12,
            body = "E' stato Mattarella",
            timestamp = "23/05/2020",
            author = u2,
            author_id = u2.id,
            score_liking = 3,
            score_difficulty = 5,
            reviewed_course_id = C2.id,
            reviewed_course =C2
        )

        val r5 = Review(
            id = 13,
            body = "Il prof non si è mai presentato",
            timestamp = "23/05/2020",
            author = u1,
            author_id = u1.id,
            score_liking = 1,
            score_difficulty = 1,
            reviewed_course_id = C3.id,
            reviewed_course = C3
        )

        val us_1_2 = UsersChat(
            id = 1,
            of_user_id = u1.id,
            latest_message = "Ciao bello, hai ganja?",
            last_opened_on = "2019/90/89-89:00:00Z",
            other_user_chat_id = 2,
            other_user = u2,
            of_user = u1
        )

        val us_2_1 = UsersChat(
            id = 2,
            of_user_id = u2.id,
            latest_message = "Ciao bello, hai ganja?",
            last_opened_on = "2019/90/89-89:00:00Z",
            other_user_chat_id = 1,
            other_user = u1,
            of_user = u2
        )

        us_1_2.other_user_chat = us_2_1
        us_2_1.other_user_chat = us_1_2

        u1.published_posts = ArrayList<Post>(listOf(p1,p3,p4))
        u1.published_comments = ArrayList<Comment>(listOf(c2))
        u1.published_reviews = ArrayList<Review>(listOf(r1,r3,r5))

        u2.published_posts = ArrayList<Post>(listOf(p2,p5,p6))
        u2.published_comments = ArrayList<Comment>(listOf(c1))
        u2.published_reviews = ArrayList<Review>(listOf(r2,r4))

        p1.comments = ArrayList<Comment>(listOf(c1))
        c1.comments = ArrayList<Comment>(listOf(c2))
        c1.comments_num = c1.comments?.size
        c2.comments_num = 0

        C1.reviews = ArrayList<Review>(listOf(r1,r1,r1,r1,r1,r1,r1,r1,r1,r1,r2))
        C2.reviews = ArrayList<Review>(listOf(r3,r4))
        C3.reviews = ArrayList<Review>(listOf(r5))

        users_table.addAll(listOf(u1,u2))
        posts_table.addAll(listOf(p1,p2,p3,p4,p5,p6))
        comments_table.addAll(listOf(c1,c2))
        course_table.addAll(listOf(C1,C2,C3))
        group_table.addAll(listOf(g1,g2,g3))
        reviews_table.addAll(listOf(r1,r2,r3,r4,r5))
        chats_table.addAll(listOf(us_1_2,us_2_1))


    }
}