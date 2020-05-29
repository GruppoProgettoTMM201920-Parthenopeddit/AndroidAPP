package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.Post

interface UserNamespace {
    fun getUserFeed(
        page: Int,
        perPage: Int,

        onSuccess: (postList: ArrayList<Post>) -> Unit,
        onFail: (error: String) -> Unit
    )
}