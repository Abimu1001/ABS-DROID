package model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import dbutils.DBUtils
import dbutils.DataContract


class User {
    var userLoginId: String? = null
    var userName: String? = null
    var userPhoneNumber: String? = null
    var userEmailId: String? = null
    var password: String? = null
    var securityQuestion: String? = null

    var answer: String? = null

    fun adduser(context: Context, user: User) {
        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cv = ContentValues()
        cv.put(DataContract.Users.USER_NAME, user.userName)
        cv.put(DataContract.Users.USER_LOGIN_ID, user.userLoginId)
        cv.put(DataContract.Users.USER_PHONE_NUMBER, user.userPhoneNumber)
        cv.put(DataContract.Users.PASSWORD, user.password)
        cv.put(DataContract.Users.USER_EMAIL_ID, user.userEmailId)
        cv.put(DataContract.Users.SECURITY_QUESTION, user.securityQuestion)
        cv.put(DataContract.Users.ANSWER, user.answer)
        sqLiteDatabase!!.insert(DataContract.Users.TABLE_NAME, null, cv)
        dbUtils.close()
    }


    fun loginAuthentication(context: Context, loginId: String, password: String): Boolean {

        val dbUtils = DBUtils(context)
        val sqLiteDatabase = dbUtils.open()
        val cursor = sqLiteDatabase!!.query(DataContract.Users.TABLE_NAME, null,
                DataContract.Users.USER_LOGIN_ID + "='" + loginId + "' and " + DataContract.Users.PASSWORD + "='" + password + "'", null, null, null, null)
        try {
            if (cursor != null)
                if (cursor.count > 0) {
                    return true
                }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            dbUtils.close()
        }

        return false
    }


}
