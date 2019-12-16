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

import java.util.ArrayList

import dbutils.DBUtils
import model.Lecture

class LectureListAdapter(internal var mContext: Activity) : BaseAdapter() {
    internal var lectures: List<Lecture>? = null


    init {
        getData()
    }

    private fun getData() {
        val dbUtils = DBUtils(mContext)
        dbUtils.open()
        val lecture = Lecture(mContext)
        lectures = ArrayList()
        lectures = lecture.getLectures(mContext, null)

        dbUtils.close()
    }

    override fun getCount(): Int {
        return lectures!!.size
    }

    override fun getItem(position: Int): Lecture {
        return lectures!![position]
    }

    override fun getItemId(position: Int): Long {
        return lectures!![position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView


        val viewHolder: ViewHolder
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null) {


            convertView = layoutInflater.inflate(R.layout.item_lecture, parent, false)
            viewHolder = ViewHolder(convertView!!)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.courseName.text = "Class:" + lectures!![position].courseName
        //        viewHolder.courseDetails.setText("Details" + lectures.get(position).getCourceDetails());

        viewHolder.takeAttendance.setOnClickListener {
            //navigate to attendance activity
            val intent = Intent(mContext, AttendanceDashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(LECTURE_ID, lectures!![position].id)
            mContext.startActivity(intent)
            mContext.finish()
        }
        return convertView
    }

    fun updateData() {
        val dbUtils = DBUtils(mContext)
        dbUtils.open()
        val lecture = Lecture(mContext)
        lectures = ArrayList()
        lectures = lecture.getLectures(mContext, null)
        dbUtils.close()
        notifyDataSetChanged()
    }

    internal inner class ViewHolder(v: View) {
        var courseName: TextView
        var courseDetails: TextView
        var takeAttendance: Button

        init {
            courseName = v.findViewById(R.id.courseName) as TextView
            courseDetails = v.findViewById(R.id.courseDetails) as TextView
            takeAttendance = v.findViewById(R.id.takeAttendance) as Button
        }

    }

    companion object {
        val LECTURE_ID = "lecture_id"
    }
}
