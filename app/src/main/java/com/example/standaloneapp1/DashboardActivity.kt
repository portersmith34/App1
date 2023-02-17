package com.example.standaloneapp1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity: AppCompatActivity(), View.OnClickListener {

    private var textViewLoggedIn: TextView? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var buttonPrev: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val receivedIntent = intent
        firstName = receivedIntent.getStringExtra("EditText_FirstName")
        lastName = receivedIntent.getStringExtra("EditText_LastName")
        textViewLoggedIn = findViewById(R.id.textView_LoggedIn)
        "$firstName $lastName is logged in!".also { textViewLoggedIn!!.text = it }

        buttonPrev = findViewById(R.id.button_previous)
        buttonPrev!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_previous -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}