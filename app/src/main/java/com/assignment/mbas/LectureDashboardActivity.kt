package com.assignment.mbas

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.Display
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast

import com.google.android.gms.appindexing.Action
import com.google.android.gms.appindexing.AppIndex
import com.google.android.gms.common.api.GoogleApiClient

import java.util.ArrayList
import java.util.Calendar
import java.util.Date

import dbutils.DBUtils
import model.Lecture
import model.Student

class LectureDashboardActivity : BaseActivity() {
    internal lateinit var lectureListAdapter: LectureListAdapter
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private var client: GoogleApiClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecture_dashboard)


        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById(R.id.listview) as ListView
        lectureListAdapter = LectureListAdapter(this)
        listView.adapter = lectureListAdapter

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = GoogleApiClient.Builder(this).addApi(AppIndex.API).build()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lecture, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.action_add_lecture -> {
                //open add student fragment
                val fragment = AddLectureFragment()
                fragment.show(supportFragmentManager, "addLecture")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    public override fun onStart() {
        super.onStart()

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client!!.connect()
        val viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LectureDashboard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.assignment.mbas/http/host/path")
        )
        AppIndex.AppIndexApi.start(client, viewAction)
    }

    public override fun onStop() {
        super.onStop()

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        val viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "LectureDashboard Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.assignment.mbas/http/host/path")
        )
        AppIndex.AppIndexApi.end(client, viewAction)
        client!!.disconnect()
    }


    internal inner class AddLectureFragment : DialogFragment() {

        private var courseName: EditText? = null
        private var courseDetails: EditText? = null
        private var classCode: EditText? = null
        private var startTime: TextView? = null
        private var endTime: TextView? = null

        val mSelectedItems = arrayListOf()
        var students: List<Student>? = null

        private val isValidate: Boolean
            get() {
                if (TextUtils.isEmpty(courseName!!.text.toString()) ||
                        TextUtils.isEmpty(courseDetails!!.text.toString()) || TextUtils.isEmpty(startTime!!.text.toString()) ||
                        TextUtils.isEmpty(endTime!!.text.toString())) {

                    return false
                } else if (mSelectedItems.size == 0) {
                    Toast.makeText(applicationContext, "Please select the students", Toast.LENGTH_SHORT).show()
                    return false
                } else
                    return true
            }

        var myCalendar: Calendar? = null

        private var isEditeted: Boolean = false
        var count = 0
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        }


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.add_lecture, null)


            courseName = rootView.findViewById(R.id.courseName) as EditText
            courseDetails = rootView.findViewById(R.id.courseDetails) as EditText
            classCode = rootView.findViewById(R.id.classCode) as EditText
            startTime = rootView.findViewById(R.id.startTime) as TextView
            endTime = rootView.findViewById(R.id.endTime) as TextView

            val pickStartTime = rootView.findViewById(R.id.pickStartTime) as Button
            pickStartTime.setOnClickListener {
                count = 0
                val datePickerFragment = DatePickerFragment()
                val bundle = Bundle()
                bundle.putString("from", "start_time")
                datePickerFragment.arguments = bundle
                datePickerFragment.show(activity.supportFragmentManager, "DatePickerFragment")
            }

            val pickEndTime = rootView.findViewById(R.id.pickEndTime) as Button
            pickEndTime.setOnClickListener {
                count = 0
                val datePickerFragment = DatePickerFragment()
                val bundle = Bundle()
                bundle.putString("from", "end_time")
                datePickerFragment.arguments = bundle
                datePickerFragment.show(activity.supportFragmentManager, "DatePickerFragment")
            }
            val selectedStudents = StringBuilder()
            val selectStudents = rootView.findViewById(R.id.selectStudents) as Button
            val studentAdapter = StudentsListAdapter(applicationContext, "Dialog")
            selectStudents.setOnClickListener { loadStudents() }


            val save = rootView.findViewById(R.id.save) as Button

            save.setOnClickListener {
                if (isValidate) {
                    //save...
                    val stringBuilder = StringBuilder()
                    for (i in mSelectedItems.indices) {
                        stringBuilder.append(students!![i]._id.toString() + "")
                        if (mSelectedItems.size > 1 && i < mSelectedItems.size - 1)
                            stringBuilder.append(",")
                    }

                    val lecture = Lecture(courseName!!.text.toString(), courseDetails!!.text.toString(), classCode!!.text.toString(),
                            startTime!!.text.toString(), endTime!!.text.toString(), stringBuilder.toString())

                    lecture.addLectue(applicationContext)
                    lectureListAdapter.updateData()
                    dismiss()
                } else
                    Toast.makeText(activity, "Please fill the data", Toast.LENGTH_SHORT).show()
            }

            return rootView
        }

        fun loadStudents() {

            val dbUtils = DBUtils(applicationContext)
            dbUtils.open()
            val student = Student(applicationContext)

            students = student.getStudents(applicationContext, null)
            val items = arrayOfNulls<String>(students!!.size)
            for (i in students!!.indices) {
                items[i] = students!![i].name
            }


            val ab = AlertDialog.Builder(this@LectureDashboardActivity)
            ab.setTitle("Dialog Title")
                    .setMultiChoiceItems(items, null) { dialog, which, isChecked ->
                        if (isChecked) {
                            mSelectedItems.add(which)
                        } else if (mSelectedItems.contains(which)) {
                            mSelectedItems.remove(Integer.valueOf(which))
                        }
                    }.setPositiveButton("OK") { dialog, id ->
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                        //save goes here...
                    }.setNegativeButton("CANCEL") { dialog, id -> }

            ab.create()


            ab.show()
        }

        fun addLecture() {

        }

        fun viewLectures() {

        }

        override fun onResume() {
            super.onResume()
            val dialog = dialog
            dialog.setTitle("Add Class")
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

        inner class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {


            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


                if (myCalendar == null) {
                    myCalendar = Calendar.getInstance()
                }

                // Use the current time as the default values for the picker

                val hour = myCalendar!!.get(Calendar.HOUR_OF_DAY)
                val minute = myCalendar!!.get(Calendar.MINUTE)

                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(activity, this, hour, minute,
                        DateFormat.is24HourFormat(activity))
            }


            override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {


                var am_pm: String? = null
                if (hourOfDay > 12)
                    am_pm = "AM"
                else
                    am_pm = "PM"

                //            timeOrDays.setText(hourOfDay + " : " + minute + am_pm);


                myCalendar!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar!!.set(Calendar.MINUTE, minute)


                isEditeted = true
                updateLabel(arguments)
            }
        }

        inner class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                // Use the current date as the default date in the picker
                if (myCalendar == null) {
                    myCalendar = Calendar.getInstance()
                }

                val year = myCalendar!!.get(Calendar.YEAR)
                val month = myCalendar!!.get(Calendar.MONTH)
                val day = myCalendar!!.get(Calendar.DAY_OF_MONTH)

                // Create a new instance of DatePickerDialog and return it
                return DatePickerDialog(activity, this, year, month, day)
            }


            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
                //            timeOrDays.setText(monthOfYear + " : " + dayOfMonth + " : " + year);
                myCalendar!!.set(Calendar.YEAR, year)
                myCalendar!!.set(Calendar.MONTH, monthOfYear)
                myCalendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                isEditeted = true


                count++

                if (count == 1) {// TODO this is for HTC MOBILE......
                    val timePickerFragment = TimePickerFragment()
                    val bundle = Bundle()
                    bundle.putString("from", arguments.getString("from"))
                    timePickerFragment.show(activity.supportFragmentManager, "TimePickerFragment")
                }
                updateLabel(arguments)
            }


        }

        private fun updateLabel(bundle: Bundle?) {
            val newTime = myCalendar!!.time
            if (isEditeted) {
                if (bundle != null) {
                    if (bundle.getString("from") == "start_time")
                        startTime!!.text = MyDateUtils.convertMilliSecondsToDateFormat(newTime.time + 1000)
                    else
                        endTime!!.text = MyDateUtils.convertMilliSecondsToDateFormat(newTime.time + 1000)
                } else {
                    if (bundle != null)
                        if (arguments.getString("from") == "start_time")
                            startTime!!.text = MyDateUtils.convertMilliSecondsToDateFormat(newTime.time)
                        else
                            endTime!!.text = MyDateUtils.convertMilliSecondsToDateFormat(newTime.time)

                }
            }

        }
    }


}
