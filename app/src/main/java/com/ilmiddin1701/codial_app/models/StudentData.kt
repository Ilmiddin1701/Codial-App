package com.ilmiddin1701.codial_app.models

class StudentData {
    var id: Int? = null
    var firstName: String? = null
    var lastName: String? = null
    var number: String? = null
    var day: String? = null
    var groupId: GroupData? = null

    constructor(
        id: Int?,
        firstName: String?,
        lastName: String?,
        number: String?,
        day: String?,
        groupId: GroupData?
    ) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.number = number
        this.day = day
        this.groupId = groupId
    }

    constructor(firstName: String?, lastName: String?, number: String?, day: String, groupId: GroupData?) {
        this.firstName = firstName
        this.lastName = lastName
        this.number = number
        this.day = day
        this.groupId = groupId
    }

    override fun toString(): String {
        return "StudentData(id=$id, firstName=$firstName, lastName=$lastName, number=$number, day=$day, groupId=$groupId)"
    }
}