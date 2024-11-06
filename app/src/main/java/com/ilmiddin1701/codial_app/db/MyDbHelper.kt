package com.ilmiddin1701.codial_app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ilmiddin1701.codial_app.models.CourseData
import com.ilmiddin1701.codial_app.models.GroupData
import com.ilmiddin1701.codial_app.models.MentorData
import com.ilmiddin1701.codial_app.models.StudentData

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION), DbInterface {

    companion object {
        const val DB_NAME = "db_name"
        const val VERSION = 1
        const val ID = "id"
        const val NAME = "name"
        const val FIRST_NAME = "first_name"
        const val LAST_NAME = "last_name"

        const val COURSE_TABLE = "course_table"

        const val MENTOR_TABLE = "mentor_table"

        const val GROUP_TABLE = "group_table"

        const val STUDENT_TABLE = "student_table"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val courseQuery =
            "create table $COURSE_TABLE(" +
                    "$ID integer not null primary key autoincrement unique, " +
                    "$NAME text not null, about text not null)"
        val mentorQuery =
            "create table $MENTOR_TABLE(" +
                    "$ID integer not null primary key autoincrement unique, " +
                    "$FIRST_NAME text not null, $LAST_NAME text not null, " +
                    "phoneNumber text not null, course_id not null, " +
                    "foreign key (course_id) references $COURSE_TABLE (id))"
        val groupQuery =
            "create table $GROUP_TABLE(" +
                    "$ID integer not null primary key autoincrement unique, " +
                    "$NAME text not null, mentor_id integer not null, day text not null, " +
                    "time text not null, isOpened integer not null," +
                    " foreign key (mentor_id) references $MENTOR_TABLE (id))"
        val studentQuery =
            "create table $STUDENT_TABLE(" +
                    "$ID integer not null primary key autoincrement unique," +
                    " $FIRST_NAME text not null, $LAST_NAME text not null, " +
                    "number text not null, day text not null, group_id integer not null, " +
                    "foreign key (group_id) references $GROUP_TABLE (id))"
        db?.execSQL(courseQuery)
        db?.execSQL(mentorQuery)
        db?.execSQL(groupQuery)
        db?.execSQL(studentQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    override fun addCourse(courseData: CourseData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, courseData.name)
        contentValues.put("about", courseData.about)
        database.insert(COURSE_TABLE, null, contentValues)
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
        contentValues.put(NAME, groupData.name)
        contentValues.put("mentor_id", groupData.mentorId!!.id)
        contentValues.put("day", groupData.day)
        contentValues.put("time", groupData.time)
        contentValues.put("isOpened", groupData.isOpened)
        database.insert(GROUP_TABLE, null, contentValues)
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
        contentValues.put(NAME, groupData.name)
        contentValues.put("mentor_id", groupData.mentorId!!.id)
        contentValues.put("day", groupData.day)
        contentValues.put("time", groupData.time)
        contentValues.put("isOpened", groupData.isOpened)
        database.update(GROUP_TABLE, contentValues, "$ID = ?", arrayOf(groupData.id.toString()))
    }

    override fun deleteGroup(groupData: GroupData) {
        val database = this.writableDatabase
        database.delete(GROUP_TABLE, "$ID = ?", arrayOf(groupData.id.toString()))
    }

    override fun addMentor(mentorData: MentorData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(FIRST_NAME, mentorData.firstName)
        contentValues.put(LAST_NAME, mentorData.lastName)
        contentValues.put("phoneNumber", mentorData.phoneNumber)
        contentValues.put("course_id", mentorData.courseId!!.id)
        database.insert(MENTOR_TABLE, null, contentValues)
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
        contentValues.put(FIRST_NAME, mentorData.firstName)
        contentValues.put(LAST_NAME, mentorData.lastName)
        contentValues.put("phoneNumber", mentorData.phoneNumber)
        database.update(MENTOR_TABLE, contentValues, "$ID = ?", arrayOf(mentorData.id.toString()))
    }

    override fun deleteMentor(mentorData: MentorData) {
        val database = this.writableDatabase
        database.delete(MENTOR_TABLE, "$ID = ?", arrayOf(mentorData.id.toString()))
    }

    override fun addStudent(studentData: StudentData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(FIRST_NAME, studentData.firstName)
        contentValues.put(LAST_NAME, studentData.lastName)
        contentValues.put("number", studentData.number)
        contentValues.put("day", studentData.day)
        contentValues.put("group_id", studentData.groupId!!.id)
        database.insert(STUDENT_TABLE, null, contentValues)
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
        contentValues.put(FIRST_NAME, studentData.firstName)
        contentValues.put(LAST_NAME, studentData.lastName)
        contentValues.put("number", studentData.number)
        contentValues.put("day", studentData.day)
        contentValues.put("group_id", studentData.groupId!!.id)
        database.update(STUDENT_TABLE, contentValues, "$ID = ?", arrayOf(studentData.id.toString()))
    }

    override fun deleteStudent(studentData: StudentData) {
        val database = this.writableDatabase
        database.delete(STUDENT_TABLE, "$ID = ?", arrayOf(studentData.id.toString()))
    }

    override fun getCourseById(id: Int): CourseData {
        val database = this.readableDatabase
        val cursor = database.query(
            COURSE_TABLE,
            arrayOf(ID, NAME, "about"),
            "$ID = ?",
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
            arrayOf(ID, FIRST_NAME, LAST_NAME, "phoneNumber", "course_id"),
            "$ID = ?",
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
            GROUP_TABLE,
            arrayOf(ID, NAME, "mentor_id", "day", "time", "isOpened"),
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