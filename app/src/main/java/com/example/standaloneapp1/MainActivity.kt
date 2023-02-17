package com.example.standaloneapp1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var firstName: String? = null
    private var middleName: String? = null
    private var lastName: String? = null

    private var buttonCamera: Button? = null
    private var buttonSubmit: Button? = null
    private var editTextFirstName: EditText? = null
    private var editTextMiddleName: EditText? = null
    private var editTextLastName: EditText? = null
    private var imageViewPicture: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonCamera = findViewById(R.id.button_takePicture)
        buttonCamera!!.setOnClickListener(this)
        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_submit -> {
                // Get data from Edit Texts
                editTextFirstName = findViewById<EditText>(R.id.editTextTextPersonFirstName)
                firstName = editTextFirstName!!.text.toString()
                editTextMiddleName = findViewById<EditText>(R.id.editTextTextPersonMiddleName)
                middleName = editTextMiddleName!!.text.toString()
                editTextLastName = findViewById<EditText>(R.id.editTextTextPersonLastName)
                lastName = editTextLastName!!.text.toString()
                if (firstName.isNullOrBlank() || middleName.isNullOrBlank() || lastName.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.toast_name),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    // Start an activity
                    val messageIntent = Intent(this, DashboardActivity::class.java)
                    messageIntent.putExtra("EditText_FirstName", firstName)
                    messageIntent.putExtra("EditText_MiddleName", middleName)
                    messageIntent.putExtra("EditText_LastName", lastName)
                    startActivity(messageIntent)
                }
            }
            R.id.button_takePicture -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try{
                    cameraActivity.launch(cameraIntent)
                }catch(ex:ActivityNotFoundException){

                }
            }
        }
    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            imageViewPicture = findViewById<View>(R.id.imageView) as ImageView
            //val extras = result.data!!.extras
            //val thumbnailImage = extras!!["data"] as Bitmap?

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                imageViewPicture!!.setImageBitmap(thumbnailImage)
            } else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                imageViewPicture!!.setImageBitmap(thumbnailImage)
            }
        }
    }
}