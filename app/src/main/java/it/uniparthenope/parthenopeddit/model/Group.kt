package it.uniparthenope.parthenopeddit.model

class Group(var isHeaderArg: Boolean, var nameArg: String, var typeArg: Int) {
        private var isHeader: Boolean
        private var name: String
        private var type: Int       //0 = GENERALE  1 = CORSO   2 = GRUPPO

    init {
        isHeader = isHeaderArg
        name = nameArg
        type = typeArg
    }

    fun isHeader(): Boolean {
        return isHeader
    }

    fun setHeader(header: Boolean){
        isHeader = header
    }

    fun setType(group: Int){
        type = group
    }

    fun getType(): Int{
        return type
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }


}