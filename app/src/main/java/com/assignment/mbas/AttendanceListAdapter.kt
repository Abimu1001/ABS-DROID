package com.assignment.mbas

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.RadioGroup
import android.widget.TextView

import java.util.ArrayList
import java.util.Arrays

import dbutils.DataContract
import model.Lecture
import model.Student


class AttendanceListAdapter(private val mContext: Context, internal var lectureID: Int) : BaseAdapter() {
    internal var students: MutableList<Student>

    //        List<Student> students = new ArrayList<>();
    //        for (Student student : students) {
    //            students.add(student);
    //        }
    val attendanceDetails: List<Student>
        get() = students

    init {
        val lecture = Lecture(mContext)
        val lectures = lecture.getLectures(mContext, DataContract._ID + " = " + lectureID)
        val mLecture = lectures!![0]
        students = ArrayList()
        val studentsWithCommaSeperate = mLecture.selectedStudents
        //getstudents here....
        val studentsList = studentsWithCommaSeperate?.split(",".toRegex())?.dropLastWhile({ it.isEmpty() })?.toTypedArray()?.let { mutableListOf<String>(*it) }

        val student = Student(mContext)
        if (studentsList != null) {
            for (i in studentsList.indices) {
                try {
                    val mStudent = student.getStudents(mContext, DataContract._ID + "=" + Integer.parseInt(studentsList[i].toString()))!![0]
                    students.add(mStudent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

    }

    override fun getCount(): Int {
        return students.size
    }

    override fun getItem(position: Int): Any {
        return students[position]
    }

    override fun getItemId(position: Int): Long {
        return students[position]._id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val layoutInflater: LayoutInflater
        val viewHolder: ViewHolder
        if (convertView == null) {
            layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_attendance_sheet, parent, false)
            viewHolder = ViewHolder(convertView!!)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.name.text = students[position].name
        viewHolder.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.present) {
                //some code
                students[position].absentPresent = 1
                students[position].lectureID = lectureID
            } else if (checkedId == R.id.absent) {
                //some code
                students[position].absentPresent = 0
                students[position].lectureID = lectureID
            }
        }


        return convertView
    }

    internal inner class ViewHolder(convertView: View) {
        var name: TextView
        var radioGroup: RadioGroup
        var save: Button? = null

        init {
            name = convertView.findViewById(R.id.name) as TextView
            radioGroup = convertView.findViewById(R.id.attendanceRG) as RadioGroup

        }
    }

}
