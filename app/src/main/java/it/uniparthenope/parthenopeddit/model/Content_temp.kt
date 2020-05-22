package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Content_temp (
    val id: Int,
    val body: String? = null,
    val timestamp: String,
    val author_id: String
) : JSONConvertable