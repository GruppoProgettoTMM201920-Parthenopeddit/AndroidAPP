package it.uniparthenope.parthenopeddit.model

import it.uniparthenope.parthenopeddit.util.JSONConvertable

class Course(
    id: Int,
    name: String
) : Board(id, name), JSONConvertable