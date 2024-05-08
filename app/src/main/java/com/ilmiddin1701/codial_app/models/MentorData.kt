package com.ilmiddin1701.codial_app.models

class MentorData{
    var id: Int? = null
    var firstName: String? = null
    var lastName: String? = null
    var phoneNumber: String? = null
    var courseId: CourseData? = null

    constructor(
        id: Int?,
        firstName: String?,
        lastName: String?,
        phoneNumber: String?,
        courseId: CourseData?
    ) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.phoneNumber = phoneNumber
        this.courseId = courseId
    }

    constructor(firstName: String?, lastName: String?, phoneNumber: String?, courseId: CourseData?) {
        this.firstName = firstName
        this.lastName = lastName
        this.phoneNumber = phoneNumber
        this.courseId = courseId
    }

    constructor(firstName: String?, lastName: String?, phoneNumber: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.phoneNumber = phoneNumber
    }

    override fun toString(): String {
        return "MentorData(id=$id, firstName=$firstName, lastName=$lastName, number=$phoneNumber, courseId=$courseId)"
    }
}