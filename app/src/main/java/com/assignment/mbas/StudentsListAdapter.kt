package com.assignment.mbas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListAdapter
import android.widget.TextView

import java.util.ArrayList

import dbutils.DBUtils
import model.Student


class StudentsListAdapter : BaseAdapter {
    internal var students: List<Student>? = null
    internal var mContext: Context
    internal var from: String? = null

    constructor(context: Context) {
        mContext = context
        getData()

    }

    constructor(context: Context, dialog: String) {
        mContext = context
        getData()
        from = dialog
    }

    internal fun getData() {
        val dbUtils = DBUtils(mContext)
        dbUtils.open()
        val student = Student(mContext)
        students = ArrayList()
        students = student.getStudents(mContext, null)

        dbUtils.close()
    }

    override fun getCount(): Int {
        return students!!.size
    }

    override fun getItem(position: Int): Student {
        return students!![position]
    }

    override fun getItemId(position: Int): Long {
        return students!![position]._id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView


        val viewHolder: ViewHolder
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_student, parent, false)
            viewHolder = ViewHolder(convertView!!)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        if (from == null) {
            viewHolder.name.text = "Name:" + students!![position].name
            viewHolder.tNumber.text = "NIS number:" + students!![position].gettNumber()
            viewHolder.nameCB.visibility = View.GONE
        } else {
            viewHolder.nameCB.visibility = View.VISIBLE
            viewHolder.nameCB.text = students!![position].name
            viewHolder.name.visibility = View.GONE
            viewHolder.tNumber.visibility = View.GONE
        }
        return convertView
    }

    fun updateData() {
        getData()
        notifyDataSetChanged()
    }

    internal inner class ViewHolder(v: View) {
        var name: TextView
        var tNumber: TextView
        var nameCB: CheckBox

        init {
            name = v.findViewById(R.id.name) as TextView
            tNumber = v.findViewById(R.id.tNumber) as TextView
            nameCB = v.findViewById(R.id.nameCB) as CheckBox
        }

    }
}
