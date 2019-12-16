package com.assignment.mbas

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View

import com.assignment.mbas.R

class ModuleDashboardActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_dashboard)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    fun lecture(view: View) {
        startActivity(Intent(this, LectureDashboardActivity::class.java))
    }

    fun student(view: View) {
        startActivity(Intent(this, StudentDashboardActivity::class.java))
    }

    fun attendance(view: View) {
        startActivity(Intent(this, AttendanceDashboardActivity::class.java))
    }

}
