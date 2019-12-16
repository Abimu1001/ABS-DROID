package com.assignment.mbas

import android.app.Dialog
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast

import com.assignment.mbas.R

import model.Student


class StudentDashboardActivity : BaseActivity() {
    private var studentsListAdapter: StudentsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_student_dashboard)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById(R.id.listview) as ListView
        studentsListAdapter = StudentsListAdapter(applicationContext)
        listView.adapter = studentsListAdapter

    }

    fun addStudent() {

    }

    fun viewStudents() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_student, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_add_student -> {
                //open add student fragment
                val fragment = AddStudentFragment()
                fragment.show(supportFragmentManager, "addStudent")
            }
        }
        return super.onOptionsItemSelected(item)
    }


    internal inner class AddStudentFragment : DialogFragment() {

        private var name: EditText? = null
        private var tNumber: EditText? = null
        private var phoneNumber: EditText? = null
        private var emailid: EditText? = null

        private val isValidate: Boolean
            get() = if (TextUtils.isEmpty(name!!.text.toString()) ||
                    TextUtils.isEmpty(tNumber!!.text.toString()) || TextUtils.isEmpty(phoneNumber!!.text.toString()) ||
                    TextUtils.isEmpty(emailid!!.text.toString())) {
                false
            } else
                true

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)


        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.add_student, null)


            name = rootView.findViewById(R.id.name) as EditText
            tNumber = rootView.findViewById(R.id.tNumber) as EditText
            phoneNumber = rootView.findViewById(R.id.phoneNumber) as EditText
            emailid = rootView.findViewById(R.id.emailId) as EditText
            val save = rootView.findViewById(R.id.save) as Button

            save.setOnClickListener {
                if (isValidate) {
                    val student = Student(name!!.text.toString(), tNumber!!.text.toString(),
                            phoneNumber!!.text.toString(), emailid!!.text.toString())

                    student.addStudent(applicationContext)
                    studentsListAdapter!!.updateData()
                    dismiss()
                } else
                    Toast.makeText(activity, "Please fill the data", Toast.LENGTH_SHORT).show()
            }

            return rootView
        }

        override fun onResume() {
            super.onResume()
            val dialog = dialog
            dialog.setTitle("Add Student")
            val window = dialog.window
            window!!.setGravity(Gravity.CENTER)
            val display = dialog.window!!.windowManager.defaultDisplay
            var height = 0f
            if (Build.VERSION.SDK_INT > 13) {
                val point = Point()
                display.getSize(point)
                height = point.y / 1.25f
            } else
                height = display.height / 1.25f

            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, height.toInt())

        }
    }


}
