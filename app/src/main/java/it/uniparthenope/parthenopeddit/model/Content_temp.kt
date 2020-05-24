package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Content_temp (
    var id: Int,
    var title: String,
    var body: String? = null,
    var timestamp: String,
    var author: User
) : JSONConvertable