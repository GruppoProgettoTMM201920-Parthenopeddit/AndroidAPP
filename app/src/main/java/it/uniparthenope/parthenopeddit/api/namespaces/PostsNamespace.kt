package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.LikeDislikeScore
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

        onLikePlaced: (score: LikeDislikeScore) -> Unit,
        onLikeRemoved: (score: LikeDislikeScore) -> Unit,
        onDislikeRemovedAndLikePlaced: (score: LikeDislikeScore) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun dislikePost(
        postId:Int,

        onDislikePlaced: (score: LikeDislikeScore) -> Unit,
        onDislikeRemoved: (score: LikeDislikeScore) -> Unit,
        onLikeRemovedAndDislikePlaced: (score: LikeDislikeScore) -> Unit,
        onFail: (error: String) -> Unit
    )
}