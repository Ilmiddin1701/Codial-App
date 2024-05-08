package com.ilmiddin1701.codial_app.models

class GroupData {
    var id: Int? = null
    var name: String? = null
    var mentorId: MentorData? = null
    var day: String? = null
    var time: String? = null
    var isOpened: Int? = null

    constructor(
        id: Int?,
        name: String?,
        mentorId: MentorData?,
        day: String?,
        time: String?,
        isOpened: Int
    ) {
        this.id = id
        this.name = name
        this.mentorId = mentorId
        this.day = day
        this.time = time
        this.isOpened = isOpened
    }

    constructor(name: String?, mentorId: MentorData?, day: String?, time: String?,  isOpened: Int) {
        this.name = name
        this.mentorId = mentorId
        this.day = day
        this.time = time
        this.isOpened = isOpened
    }

    override fun toString(): String {
        return "GroupData(id=$id, name=$name, mentorId=$mentorId, day=$day, time=$time, isOpened=$isOpened)"
    }
}