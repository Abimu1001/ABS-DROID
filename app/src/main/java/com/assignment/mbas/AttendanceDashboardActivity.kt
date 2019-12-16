package com.assignment.mbas

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

import model.Attendance
import model.Student


class AttendanceDashboardActivity : BaseActivity() {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_dashboard)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        val attendanceListView = findViewById(R.id.attendanceListView) as ListView
        val totalHeader: TextView
        val attendancePercentHeader: TextView
        totalHeader = findViewById(R.id.totalHeader) as TextView
        attendancePercentHeader = findViewById(R.id.attendancePercentHeader) as TextView
        val save = findViewById(R.id.save) as Button
        var lectureID = 0
        if (intent.hasExtra(LectureListAdapter.LECTURE_ID))
            lectureID = intent.getIntExtra(LectureListAdapter.LECTURE_ID, 0)
        if (lectureID > 0) {


            totalHeader.visibility = View.GONE
            attendancePercentHeader.visibility = View.GONE

            //prepare attendance ui


            val attendanceListAdapter = AttendanceListAdapter(applicationContext, lectureID)
            attendanceListView.adapter = attendanceListAdapter


            save.setOnClickListener {
                // save to db and calculate attendance percentage,,,,,
                val students = attendanceListAdapter.attendanceDetails
                for (student in students) {
                    val attendance = Attendance(student.absentPresent, student.lectureID, student._id)
                    attendance.addAttendance(applicationContext)
                }


                finish()
            }
        } else {
            //          show attendance based on student  normal percentages..
            totalHeader.visibility = View.VISIBLE
            attendancePercentHeader.visibility = View.VISIBLE
            save.visibility = View.GONE
            attendanceListView.adapter = AttendanceSheetAdapter(this@AttendanceDashboardActivity)

        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
