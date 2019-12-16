package com.assignment.mbas

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

import dbutils.DBUtils
import model.User


class LoginActivity : AppCompatActivity() {
    private var dbUtils: DBUtils? = null
    internal lateinit var regLayout: LinearLayout
    internal lateinit var loginLayout: LinearLayout

    internal lateinit var regUserName: EditText
    internal lateinit var regEmailId: EditText
    internal lateinit var regPhoneNumber: EditText
    internal lateinit var regUserId: EditText
    internal lateinit var regPassword: EditText
    internal lateinit var regSecurityQuestion: EditText
    internal lateinit var regSecurityAnswer: EditText

    private val isValidate: Boolean
        get() {

            if (TextUtils.isEmpty(regUserName.text.toString()) || TextUtils.isEmpty(regEmailId.text.toString()) || TextUtils.isEmpty(regPhoneNumber.text.toString()) ||
                    TextUtils.isEmpty(regUserId.text.toString()) || TextUtils.isEmpty(regPassword.text.toString()) || TextUtils.isEmpty(regSecurityQuestion.text.toString()) || TextUtils.isEmpty(regSecurityAnswer.text.toString())) {
                Toast.makeText(applicationContext, "Please fill the data in all fields", Toast.LENGTH_SHORT).show()
                return false
            } else
                return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        regLayout = findViewById(R.id.regLayout) as LinearLayout
        loginLayout = findViewById(R.id.loginLayout) as LinearLayout

        dbUtils = DBUtils(applicationContext)
        dbUtils!!.open()
        if (dbUtils!!.containUsers()) {
            regLayout.visibility = View.GONE
            loginLayout.visibility = View.VISIBLE
        } else {
            regLayout.visibility = View.VISIBLE
            loginLayout.visibility = View.GONE
            registerUser()
        }
        dbUtils!!.close()

    }


    private fun registerUser() {

        regUserName = findViewById(R.id.regUserName) as EditText
        regEmailId = findViewById(R.id.regEmailId) as EditText
        regPhoneNumber = findViewById(R.id.regPhoneNumber) as EditText
        regUserId = findViewById(R.id.regUserId) as EditText
        regPassword = findViewById(R.id.regPassword) as EditText

        regSecurityQuestion = findViewById(R.id.regSecurityQuestion) as EditText
        regSecurityAnswer = findViewById(R.id.regSecurityAnswer) as EditText
        val btnRegister = findViewById(R.id.register) as Button


        btnRegister.setOnClickListener {
            if (isValidate) {
                //reg goes here...
                val user = User()
                user.userName = regUserName.text.toString()
                user.userEmailId = regEmailId.text.toString()
                user.userLoginId = regUserId.text.toString()
                user.userPhoneNumber = regPhoneNumber.text.toString()
                user.password = regPassword.text.toString()
                user.adduser(applicationContext, user)

                //ds
                if (dbUtils!!.containUsers()) {
                    regLayout.visibility = View.GONE
                    loginLayout.visibility = View.VISIBLE
                } else {
                    regLayout.visibility = View.VISIBLE
                    loginLayout.visibility = View.GONE
                    registerUser()
                }

            }
        }


    }

    fun login(v: View) {

        val loginId: EditText
        val password: EditText
        loginId = findViewById(R.id.loginId) as EditText
        password = findViewById(R.id.password) as EditText

        val user = User()
        if (user.loginAuthentication(applicationContext, loginId.text.toString(), password.text.toString())) {
            startActivity(Intent(this@LoginActivity, ModuleDashboardActivity::class.java))
            finish()

        } else {
            Toast.makeText(applicationContext, "Please check the login id and password", Toast.LENGTH_SHORT).show()
        }

    }

}
