package it.uniparthenope.parthenopeddit.api

import it.uniparthenope.parthenopeddit.model.Post

interface PostNamespace {
    /**
     * GET POST
     * Retrieves post of given ID
     */
    fun getPost(token: String, postId:Int, completion: (post: Post?, error: String?) -> Unit)

    /**
     * GET ALL POST
     * Retrieves data of all posts visible to the user
     */
    fun getAllPost(token:String, completion: (postList: List<Post>?, error: String?) -> Unit)

    /**
     * PUBLISH NEW POST
     * Publishes a new post with logged user as author.
     * Post needs title and may have a body
     * on completion returns created post
     */
    fun publishNewPost(token: String, title:String, body:String?, completion: (post: Post?, error: String?) -> Unit)

    /**
     * GET POST WITH COMMENTS
     * Retrieves post of given ID with a 'comments list' component
     */
    fun getPostWithComments(token: String, postId:Int, completion: (post: Post?, error: String?) -> Unit)
}