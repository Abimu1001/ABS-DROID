package dbutils

import java.util.Date


object DataContract {

    var _ID = "_id"

    object Users {
        var TABLE_NAME = "users"
        var USER_LOGIN_ID = "userLoginId"
        var USER_PHONE_NUMBER = "userPhoneNumber"
        var USER_EMAIL_ID = "userEmailId"
        var PASSWORD = "password"
        var SECURITY_QUESTION = "securityQuestion"
        var ANSWER = "answer"
        var USER_NAME = "userName"

    }

    object Student {
        var TABLE_NAME = "students"
        //        String name, String tNumber, String phoneNumber, String emailId
        var NAME = "name"
        var TNUMBER = "tNumber"
        var PHONE_NUMBER = "phoneNumber"
        var EMAIL_ID = "emailId"

    }

    object Lecture {

        //        private String courseName, courceDetails;
        //        private Date date

        var TABLE_NAME = "lectures"
        var COURSE_NAME = "course_name"
        var COURSE_DETAILS = "course_details"
        var CLASS_CODE = "class_code"
        var START_DATE_TIME = "start_date_time"
        var END_DATE_TIME = "end_date_time"
        val SELECTED_STUDENTS = "selected_students"


    }

    object Attendance {
        var TABLE_NAME = "attendance"
        var MARK_ATTENDANCE = "mark_attendance"
        var LECTURE_ID = "lecture_id"
        var STUDENT_ID = "student_id"
    }
}
