package com.example.standaloneapp1

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity: AppCompatActivity(), View.OnClickListener {

    private var textViewLoggedIn: TextView? = null
    private var firstName: String? = null
    private var middleName: String? = null
    private var lastName: String? = null
    private var buttonPrev: Button? = null
    private var messageIntent: Intent? = null
    private var profileImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        messageIntent = Intent(this, MainActivity::class.java)
        var receivedIntent = intent
        firstName = receivedIntent!!.getStringExtra("EditText_FirstName")
        middleName = receivedIntent!!.getStringExtra("EditText_MiddleName")
        lastName = receivedIntent!!.getStringExtra("EditText_LastName")
        textViewLoggedIn = findViewById(R.id.textView_LoggedIn)
        "$firstName $lastName is logged in!".also { textViewLoggedIn!!.text = it }

        if (Build.VERSION.SDK_INT >= 33) {
            profileImage = receivedIntent!!.getParcelableExtra("Bitmap_ProfileImage", Bitmap::class.java)
        } else {
            val thumbnailImage = receivedIntent!!.getParcelableExtra<Bitmap>("data")
        }

        buttonPrev = findViewById(R.id.button_previous)
        buttonPrev!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_previous -> {
                messageIntent!!.putExtra("Bitmap_ProfileImage", profileImage)
                messageIntent!!.putExtra("EditText_FirstName", firstName)
                messageIntent!!.putExtra("EditText_MiddleName", middleName)
                messageIntent!!.putExtra("EditText_LastName", lastName)
                startActivity(messageIntent)
            }
        }
    }
}