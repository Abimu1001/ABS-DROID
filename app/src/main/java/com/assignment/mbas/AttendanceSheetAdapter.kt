package com.assignment.mbas

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

import dbutils.DataContract
import model.Attendance
import model.Student


class AttendanceSheetAdapter(internal var context: Activity) : BaseAdapter() {

    internal var students: List<Student>? = null

    init {
        val student = Student(context)
        students = student.getStudents(context, null)
    }

    override fun getCount(): Int {

        return students!!.size
    }

    override fun getItem(position: Int): Any {

        return students!![position]
    }

    override fun getItemId(position: Int): Long {

        return students!![position]._id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            convertView = layoutInflater.inflate(R.layout.item_attendance_caluculate_sheet, parent, false)
            viewHolder = ViewHolder(convertView!!)
            convertView.tag = viewHolder
        } else
            viewHolder = convertView.tag as ViewHolder

        val mStudent = students!![position]
        viewHolder.name.text = mStudent.name
        val attendance = Attendance()
        val mAttendances = attendance.getAttendance(context, DataContract.Attendance.STUDENT_ID + "=" + mStudent._id)
        var absent = 0
        var present = 0
        val total: Float
        val percentage: Float

        for (mAttendance in mAttendances!!) {
            if (mAttendance.markAttendance == 0)
                absent++
            present += mAttendance.markAttendance
        }

        total = mAttendances.size.toFloat()
        percentage = present / total * 100f

        viewHolder.present.text = present.toString() + ""
        viewHolder.absent.text = absent.toString() + ""
        viewHolder.total.text = total.toString() + ""
        viewHolder.attendancePercentage.text = (Math.round(percentage * 100.0) / 100.0).toString() + "%"
        viewHolder.send.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(Intent.EXTRA_EMAIL, students!![position].emailId)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Attendance")
            intent.putExtra(Intent.EXTRA_TEXT, "Hi " + students!![position].name + ", your attendance percentage is " + Math.round(percentage * 100.0) / 100.0 + "%")

            context.startActivity(Intent.createChooser(intent, "Send Email"))
        }
        return convertView
    }

    internal inner class ViewHolder(convertView: View) {
        var present: TextView
        var absent: TextView
        var total: TextView
        var attendancePercentage: TextView
        var name: TextView
        var send: Button

        init {
            name = convertView.findViewById(R.id.name) as TextView
            present = convertView.findViewById(R.id.present) as TextView
            absent = convertView.findViewById(R.id.absent) as TextView
            total = convertView.findViewById(R.id.totalHeader) as TextView
            attendancePercentage = convertView.findViewById(R.id.attendancePercentHeader) as TextView
            send = convertView.findViewById(R.id.send) as Button
        }
    }
}
