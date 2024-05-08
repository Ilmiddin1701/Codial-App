package com.ilmiddin1701.codial_app.models

class CourseData {
    var id: Int? = null
    var name: String? = null
    var about: String? = null

    constructor(id: Int?, name: String?, about: String?) {
        this.id = id
        this.name = name
        this.about = about
    }

    constructor(name: String?, about: String?) {
        this.name = name
        this.about = about
    }

    override fun toString(): String {
        return "CourseData(id=$id, name=$name, information=$about)"
    }
}