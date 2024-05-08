package com.ilmiddin1701.codial_app.db

import com.ilmiddin1701.codial_app.models.CourseData
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.MentorData
import com.ilmiddin1701.codial_app.models.StudentData

interface DbInterface {
    fun addCourse(courseData: CourseData)
    fun showCourses(): List<CourseData>

    fun addGroup(groupData: GroupData)
    fun showGroups(): List<GroupData>
    fun editGroup(groupData: GroupData)
    fun deleteGroup(groupData: GroupData)

    fun addMentor(mentorData: MentorData)
    fun showMentors(): List<MentorData>
    fun editMentor(mentorData: MentorData)
    fun deleteMentor(mentorData: MentorData)

    fun addStudent(studentData: StudentData)
    fun showStudents(): List<StudentData>
    fun editStudent(studentData: StudentData)
    fun deleteStudent(studentData: StudentData)

    fun getCourseById(id: Int): CourseData
    fun getMentorById(id: Int): MentorData
    fun getGroupById(id: Int): GroupData
}