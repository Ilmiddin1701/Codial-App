package com.ilmiddin1701.codial_app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ilmiddin1701.codial_app.models.CourseData
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.MentorData
import com.ilmiddin1701.codial_app.models.StudentData

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, "db_name", null, 1), DbInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        val courseQuery =
            "create table course_table(id integer not null primary key autoincrement unique, name text not null, about text not null)"
        val mentorQuery =
            "create table mentor_table(id integer not null primary key autoincrement unique, first_name text not null, last_name text not null, phoneNumber text not null, course_id not null, foreign key (course_id) references course_table (id))"
        val groupQuery =
            "create table group_table(id integer not null primary key autoincrement unique, name text not null, mentor_id integer not null, day text not null, time text not null, isOpened integer not null, foreign key (mentor_id) references mentor_table (id))"
        val studentQuery =
            "create table student_table(id integer not null primary key autoincrement unique, first_name text not null, last_name text not null, number text not null, day text not null, group_id integer not null, foreign key (group_id) references group_table (id))"
        db?.execSQL(courseQuery)
        db?.execSQL(mentorQuery)
        db?.execSQL(groupQuery)
        db?.execSQL(studentQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addCourse(courseData: CourseData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", courseData.name)
        contentValues.put("about", courseData.about)
        database.insert("course_table", null, contentValues)
        database.close()
    }

    override fun showCourses(): List<CourseData> {
        val list = ArrayList<CourseData>()
        val database = readableDatabase
        val query = "select * from course_table"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    CourseData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun addGroup(groupData: GroupData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", groupData.name)
        contentValues.put("mentor_id", groupData.mentorId!!.id)
        contentValues.put("day", groupData.day)
        contentValues.put("time", groupData.time)
        contentValues.put("isOpened", groupData.isOpened)
        database.insert("group_table", null, contentValues)
        database.close()
    }

    override fun showGroups(): List<GroupData> {
        val list = ArrayList<GroupData>()
        val database = readableDatabase
        val query = "select * from group_table"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    GroupData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        getMentorById(cursor.getInt(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun editGroup(groupData: GroupData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", groupData.name)
        contentValues.put("mentor_id", groupData.mentorId!!.id)
        contentValues.put("day", groupData.day)
        contentValues.put("time", groupData.time)
        contentValues.put("isOpened", groupData.isOpened)
        database.update("group_table", contentValues, "id = ?", arrayOf(groupData.id.toString()))
    }

    override fun deleteGroup(groupData: GroupData) {
        val database = this.writableDatabase
        database.delete("group_table", "id = ?", arrayOf(groupData.id.toString()))
    }

    override fun addMentor(mentorData: MentorData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("first_name", mentorData.firstName)
        contentValues.put("last_name", mentorData.lastName)
        contentValues.put("phoneNumber", mentorData.phoneNumber)
        contentValues.put("course_id", mentorData.courseId!!.id)
        database.insert("mentor_table", null, contentValues)
        database.close()
    }

    override fun showMentors(): List<MentorData> {
        val list = ArrayList<MentorData>()
        val database = this.readableDatabase
        val query = "select * from mentor_table"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    MentorData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        getCourseById(cursor.getInt(4))
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun editMentor(mentorData: MentorData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("first_name", mentorData.firstName)
        contentValues.put("last_name", mentorData.lastName)
        contentValues.put("phoneNumber", mentorData.phoneNumber)
        database.update("mentor_table", contentValues, "id = ?", arrayOf(mentorData.id.toString()))
    }

    override fun deleteMentor(mentorData: MentorData) {
        val database = this.writableDatabase
        database.delete("mentor_table", "id = ?", arrayOf(mentorData.id.toString()))
    }

    override fun addStudent(studentData: StudentData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("first_name", studentData.firstName)
        contentValues.put("last_name", studentData.lastName)
        contentValues.put("number", studentData.number)
        contentValues.put("day", studentData.day)
        contentValues.put("group_id", studentData.groupId!!.id)
        database.insert("student_table", null, contentValues)
        database.close()
    }

    override fun showStudents(): List<StudentData> {
        val list = ArrayList<StudentData>()
        val database = this.readableDatabase
        val query = "select * from student_table"
        val cursor = database.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    StudentData(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        getGroupById(cursor.getInt(5))
                    )
                )
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun editStudent(studentData: StudentData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("first_name", studentData.firstName)
        contentValues.put("last_name", studentData.lastName)
        contentValues.put("number", studentData.number)
        contentValues.put("day", studentData.day)
        contentValues.put("group_id", studentData.groupId!!.id)
        database.update("student_table", contentValues, "id = ?", arrayOf(studentData.id.toString()))
    }

    override fun deleteStudent(studentData: StudentData) {
        val database = this.writableDatabase
        database.delete("student_table", "id = ?", arrayOf(studentData.id.toString()))
    }

    override fun getCourseById(id: Int): CourseData {
        val database = this.readableDatabase
        val cursor = database.query(
            "course_table",
            arrayOf("id", "name", "about"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val courseData = CourseData(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2)
        )
        return courseData
    }

    override fun getMentorById(id: Int): MentorData {
        val database = this.readableDatabase
        val cursor = database.query(
            "mentor_table",
            arrayOf("id", "first_name", "last_name", "phoneNumber", "course_id"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val mentorData = MentorData(
            cursor.getInt(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            getCourseById(cursor.getInt(4))
        )
        return mentorData
    }

    override fun getGroupById(id: Int): GroupData {
        val database = this.readableDatabase
        val cursor = database.query(
            "group_table",
            arrayOf("id", "name", "mentor_id", "day", "time", "isOpened"),
            "id = ?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val groupData = GroupData(
            cursor.getInt(0),
            cursor.getString(1),
            getMentorById(cursor.getInt(2)),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getInt(5)
        )
        return groupData
    }
}