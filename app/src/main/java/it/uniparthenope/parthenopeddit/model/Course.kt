package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

data class Course(
    val id: Int,
    val course_name: String? = null,
    var rating: Float,
    var difficulty: Float,
    var numReview: Int,
    var reviews: ArrayList<Review> = ArrayList()
) : Board(), JSONConvertable