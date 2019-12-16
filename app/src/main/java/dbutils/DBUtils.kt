package dbutils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import model.User


class DBUtils(context: Context) {

    private val dbHelper: DBHelper
    private var sqLiteDatabase: SQLiteDatabase? = null
    private val DB_VERSION = 2


    init {

        dbHelper = DBHelper(context, DB_NAME, null, DB_VERSION)

    }


    fun open(): SQLiteDatabase? {
        sqLiteDatabase = dbHelper.writableDatabase
        return sqLiteDatabase
    }

    fun close() {
        sqLiteDatabase!!.close()
    }

    inner class DBHelper(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Users.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Users.USER_LOGIN_ID +
                    " TEXT," + DataContract.Users.USER_NAME + " TEXT," + DataContract.Users.USER_PHONE_NUMBER + " TEXT," + DataContract.Users.USER_EMAIL_ID
                    + " TEXT, " + DataContract.Users.PASSWORD + " TEXT," + DataContract.Users.SECURITY_QUESTION + " text,"
                    + DataContract.Users.ANSWER + " text);")

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Student.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Student.NAME +
                    " TEXT," + DataContract.Student.TNUMBER + " TEXT," + DataContract.Student.PHONE_NUMBER + " TEXT,"
                    + DataContract.Student.EMAIL_ID
                    + " TEXT);")

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Lecture.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Lecture.COURSE_NAME +
                    " TEXT," + DataContract.Lecture.COURSE_DETAILS + " TEXT," + DataContract.Lecture.CLASS_CODE
                    + " text," + DataContract.Lecture.START_DATE_TIME + " text," + DataContract.Lecture.END_DATE_TIME + " text,"
                    + DataContract.Lecture.SELECTED_STUDENTS + " text);")

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DataContract.Attendance.TABLE_NAME + "(" + DataContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DataContract.Attendance.LECTURE_ID +
                    " INTEGER," + DataContract.Attendance.MARK_ATTENDANCE + " INTEGER," + DataContract.Attendance.STUDENT_ID
                    + " INTEGER);")


        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Users.TABLE_NAME + ";")
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Student.TABLE_NAME + ";")
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Lecture.TABLE_NAME + ";")
            db.execSQL("DROP TABLE IF EXISTS " + DataContract.Attendance.TABLE_NAME + ";")

            onCreate(db)

        }
    }

    fun containUsers(): Boolean {

        val database = dbHelper.readableDatabase
        val cursor = database.query(DataContract.Users.TABLE_NAME, null, null, null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                if (cursor.count > 0)
                    return true
            }
        }
        return false
    }

    fun getUser(userId: String, password: String): User? {
        var user: User? = null
        val database = dbHelper.readableDatabase
        val cursor = database.query(DataContract.Users.TABLE_NAME, null, "userLoginId=$userId and password=$password", null, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    user = User()
                } while (cursor.moveToNext())
            }
        }
        return user
    }

    companion object {
        private val DB_NAME = "mbas"
    }


}
