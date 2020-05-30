package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Course(
    id: Int,
    name: String
) : Board(id, name, "course"), JSONConvertable{
    var average_liking_score: Double? = null
    var follower_number: Int? = null
    var average_difficulty_score: Double? = null
    var reviews_count: Int? = null
    var reviews: ArrayList<Review>? = null
    var followers : ArrayList<User>? = null


}