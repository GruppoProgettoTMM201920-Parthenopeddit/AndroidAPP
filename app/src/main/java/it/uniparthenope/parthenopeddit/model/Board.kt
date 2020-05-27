package it.uniparthenope.parthenopeddit.model

import com.google.gson.annotations.SerializedName
import it.uniparthenope.parthenopeddit.util.JSONConvertable

open class Board(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String
) : JSONConvertable {

}