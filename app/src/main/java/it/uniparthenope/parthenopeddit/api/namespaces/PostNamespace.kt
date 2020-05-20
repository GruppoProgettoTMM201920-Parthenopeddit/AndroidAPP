package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.Post

interface PostNamespace {
    fun publishNewPost(token: String, title:String, body:String, board_id: Int, completion: (post: Post?, error: String?) -> Unit)

    fun getPost(token: String, postId:Int, completion: (post: Post?, error: String?) -> Unit)
    fun getPostWithComments(token: String, postId:Int, completion: (post: Post?, error: String?) -> Unit)

    fun likePost(token: String, postId:Int, completion: (success: Boolean) -> Unit)
    fun dislikePost(token: String, postId:Int, completion: (success: Boolean) -> Unit)
}