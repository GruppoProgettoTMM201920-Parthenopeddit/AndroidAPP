package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Comment

interface CommentsNamespace {
    /**
     * GET COMMENT
     * Retrieves comment of given ID
     */
    fun getComment(token: String, commentId:Int, completion: (comment: Comment?, error: String?) -> Unit)

    /**
     * GET USER COMMENT
     * Retrieves all comments of an user given ID
     */
    fun getUserComment(token: String, userId: String, completion: (commentList: List<Comment>?, error: String?) -> Unit)

    /**
     * GET COMMENT WITH COMMENTS
     * Retrieves comment of given ID with a 'comments list' component
     */
    fun getCommentWithComments(token: String, commentId:Int, completion: (comment: Comment?, error: String?) -> Unit)

    /**
     * PUBLISH NEW COMMENT TO POST
     * Publishes a new comment to a user post with logged user as author.
     * Cooment needs commented post ID and a body
     * on completion returns created comment
     */
    fun publishCommentToPost(token: String, postId:Int, body:String, completion: (comment: Comment?, error: String?) -> Unit)

    /**
     * PUBLISH NEW COMMENT TO COMMENT
     * Publishes a new comment to a user comment with logged user as author.
     * Cooment needs commented comment ID and a body
     * on completion returns created comment
     */
    fun publishCommentToComment(token: String, commentId:Int, body:String, completion: (comment: Comment?, error: String?) -> Unit)
}