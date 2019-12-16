package model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import java.util.ArrayList

import dbutils.DBUtils
import dbutils.DataContract

class Student {

    var lectureID: Int = 0

    /**
     * 0=absent
     * 1= present
     */
    var absentPresent: Int = 0

    var _id: Int = 0
    var name: String? = null
    private var tNumber: String? = null
    var phoneNumber: String? = null
    var emailId: String? = null

    fun gettNumber(): String? {
        return tNumber
    }

    fun settNumber(tNumber: String) {
        this.tNumber = tNumber
    }

    constructor(name: String, tNumber: String, phoneNumber: String, emailId: String) {
        this.name = name
        this.tNumber = tNumber
        this.phoneNumber = phoneNumber
        this.emailId = emailId
    }

    constructor(context: Context) {

    }


    fun addStudent(context: Context) {
        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cv = ContentValues()
        cv.put(DataContract.Student.NAME, name)
        cv.put(DataContract.Student.EMAIL_ID, emailId)
        cv.put(DataContract.Student.PHONE_NUMBER, phoneNumber)
        cv.put(DataContract.Student.TNUMBER, tNumber)
        sqLiteDatabase!!.insert(DataContract.Student.TABLE_NAME, null, cv)
        dbUtils.close()
    }

    fun getStudents(context: Context, where: String): List<Student>? {
        var students: MutableList<Student>? = null
        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cursor = sqLiteDatabase!!.query(DataContract.Student.TABLE_NAME, null, where, null, null, null, null)
        if (cursor != null)
            students = ArrayList()
        if (cursor!!.moveToFirst()) {
            do {

                val student = Student(

                        cursor.getString(cursor.getColumnIndex(DataContract.Student.NAME)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Student.TNUMBER)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Student.PHONE_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(DataContract.Student.EMAIL_ID)))
                student._id = cursor.getInt(cursor.getColumnIndex(DataContract._ID))

                students!!.add(student)
            } while (cursor.moveToNext())
        }
        dbUtils.close()

        return students
    }
}

