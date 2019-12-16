package model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList
import java.util.Date

import dbutils.DBUtils
import dbutils.DataContract


class Lecture {

    var id: Int = 0
    var courseName: String? = null
    var courceDetails: String? = null
    var classCode: String? = null

    var startDateTime: String? = null

    var endDateTime: String? = null

    var selectedStudents: String? = null

    constructor(courseName: String, courceDetails: String, classCode: String, startDateTime: String, endDateTime: String, selectedStudents: String) {
        this.courseName = courseName
        this.courceDetails = courceDetails
        this.classCode = classCode
        this.startDateTime = startDateTime
        this.endDateTime = endDateTime
        this.selectedStudents = selectedStudents
    }


    constructor(mContext: Context) {

    }

    fun addLectue(context: Context) {
        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cv = ContentValues()
        cv.put(DataContract.Lecture.COURSE_NAME, courseName)
        cv.put(DataContract.Lecture.COURSE_DETAILS, courceDetails)
        cv.put(DataContract.Lecture.CLASS_CODE, classCode)
        cv.put(DataContract.Lecture.START_DATE_TIME, startDateTime)
        cv.put(DataContract.Lecture.END_DATE_TIME, endDateTime)
        cv.put(DataContract.Lecture.SELECTED_STUDENTS, selectedStudents)

        sqLiteDatabase!!.insert(DataContract.Lecture.TABLE_NAME, null, cv)
        dbUtils.close()
    }


    fun getLectures(mContext: Context, where: String): List<Lecture>? {
        var lectures: MutableList<Lecture>? = null
        val dbUtils = DBUtils(mContext)
        val sqLiteDatabase = dbUtils.open()
        val cursor = sqLiteDatabase!!.query(DataContract.Lecture.TABLE_NAME, null, where, null, null, null, null)
        if (cursor != null)
            lectures = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {

                val lecture = Lecture(cursor.getString(cursor.getColumnIndex(DataContract.Lecture.COURSE_NAME)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Lecture.COURSE_DETAILS)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Lecture.CLASS_CODE)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Lecture.START_DATE_TIME)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Lecture.END_DATE_TIME)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Lecture.SELECTED_STUDENTS)))


                lecture.id = cursor.getInt(cursor.getColumnIndex(DataContract._ID))
                lectures!!.add(lecture)
            } while (cursor.moveToNext())
        }
        dbUtils.close()

        return lectures
    }
}
