package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class GroupInvite(
    val inviter_id: String,
    val invited_id: String,
    val group_id: Int,
    val timestamp: String,

    var inviter: User? = null,
    var invited: User? = null,
    var group: Group? = null
) : JSONConvertable