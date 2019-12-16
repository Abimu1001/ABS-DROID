package model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

import dbutils.DBUtils
import dbutils.DataContract


class Attendance {

    var _id: Int = 0


    var markAttendance: Int = 0
    var lectureID: Int = 0


    var studentID: Int = 0

    constructor() {


    }


    constructor(markAttendance: Int, lectureID: Int, studentID: Int) {
        this.markAttendance = markAttendance
        this.lectureID = lectureID
        this.studentID = studentID
    }

    fun addAttendance(context: Context) {
        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cv = ContentValues()
        cv.put(DataContract.Attendance.LECTURE_ID, lectureID)
        cv.put(DataContract.Attendance.MARK_ATTENDANCE, markAttendance)
        cv.put(DataContract.Attendance.STUDENT_ID, studentID)
        sqLiteDatabase!!.insert(DataContract.Attendance.TABLE_NAME, null, cv)
        dbUtils.close()
    }

    fun getAttendance(context: Context, where: String): List<Attendance>? {
        var attendances: MutableList<Attendance>? = null
        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cursor = sqLiteDatabase!!.query(DataContract.Attendance.TABLE_NAME, null, where, null, null, null, null)
        if (cursor != null)
            attendances = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {

                val attendance = Attendance(
                        cursor.getInt(cursor.getColumnIndex(DataContract.Attendance.MARK_ATTENDANCE)),
                        cursor.getInt(cursor.getColumnIndex(DataContract.Attendance.LECTURE_ID)),
                        cursor.getInt(cursor.getColumnIndex(DataContract.Attendance.STUDENT_ID)))
                attendance._id = cursor.getInt(cursor.getColumnIndex(DataContract._ID))

                attendances!!.add(attendance)
            } while (cursor.moveToNext())
        }
        dbUtils.close()

        return attendances
    }


}
