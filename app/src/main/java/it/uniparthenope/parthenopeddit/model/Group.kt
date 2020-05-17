package it.uniparthenope.parthenopeddit.model

class Group(var isHeaderArg: Boolean, var nameArg: String) {
        private var isHeader: Boolean
        private var name: String

    init {
        isHeader = isHeaderArg
        name = nameArg
    }

    fun isHeader(): Boolean {
        return isHeader
    }

    fun setHeader(header: Boolean){
        isHeader = header
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }


}