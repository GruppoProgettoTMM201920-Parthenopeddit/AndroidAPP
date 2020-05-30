package it.uniparthenope.parthenopeddit.api.namespaces

import it.uniparthenope.parthenopeddit.model.Comment
import it.uniparthenope.parthenopeddit.model.Post

interface CommentsNamespace {
    fun publishNewComment(
        body:String,
        commented_content_id: Int,

        onSuccess: (comment: Comment) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun getComment(
        commentId:Int,

        onSuccess: (comment: Comment) -> Unit,
        onFail: (error: String) -> Unit
    )
    fun getCommentWithComments(
        commentId:Int,

        onSuccess: (comment: Comment) -> Unit,
        onFail: (error: String) -> Unit
    )

    fun likeComment(
        commentId:Int,

        onLikePlaced: () -> Unit,
        onLikeRemoved: () -> Unit,
        onDislikeRemovedAndLikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    )
    fun dislikeComment(
        commentId:Int,

        onDislikePlaced: () -> Unit,
        onDislikeRemoved: () -> Unit,
        onLikeRemovedAndDislikePlaced: () -> Unit,
        onFail: (error: String) -> Unit
    )
}