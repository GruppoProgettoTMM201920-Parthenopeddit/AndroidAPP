package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.Post

interface PostsNamespace {
    fun publishNewPost(
        title:String,
        body:String,
        board_id: Int,

        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
        )

    fun getPost(
        postId:Int,

        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun getPostWithComments(
        postId:Int,

        onSuccess: (post: Post) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun likePost(
        postId:Int,

        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    )
    fun dislikePost(
        postId:Int,

        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    )
}