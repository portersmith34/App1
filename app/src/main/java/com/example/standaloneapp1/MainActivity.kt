package com.example.standaloneapp1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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
    private var messageIntent: Intent? = null
    private var profileImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextFirstName = findViewById(R.id.editTextTextPersonFirstName)
        editTextMiddleName = findViewById(R.id.editTextTextPersonMiddleName)
        editTextLastName = findViewById(R.id.editTextTextPersonLastName)
        imageViewPicture = findViewById<View>(R.id.imageView) as ImageView


        buttonCamera = findViewById(R.id.button_takePicture)
        buttonCamera!!.setOnClickListener(this)
        buttonSubmit = findViewById(R.id.button_submit)
        buttonSubmit!!.setOnClickListener(this)
        messageIntent = Intent(this, DashboardActivity::class.java)

        var receivedIntent = intent
        if (receivedIntent != null) {
            editTextFirstName!!.setText(receivedIntent!!.getStringExtra("EditText_FirstName"))
            editTextMiddleName!!.setText(receivedIntent!!.getStringExtra("EditText_MiddleName"))
            editTextLastName!!.setText(receivedIntent!!.getStringExtra("EditText_LastName"))
            if (Build.VERSION.SDK_INT >= 33) {
                profileImage = receivedIntent!!.getParcelableExtra("Bitmap_ProfileImage", Bitmap::class.java)
            } else {
                val thumbnailImage = receivedIntent!!.getParcelableExtra<Bitmap>("data")
            }
            imageViewPicture!!.setImageBitmap(profileImage)
        }
    }
    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_submit -> {
                // Get data from Edit Texts
                firstName = editTextFirstName!!.text.toString()
                middleName = editTextMiddleName!!.text.toString()
                lastName = editTextLastName!!.text.toString()
                if (firstName.isNullOrBlank() || middleName.isNullOrBlank() || lastName.isNullOrBlank() || profileImage == null) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.toast_submit),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    // Start an activity
                    messageIntent!!.putExtra("Bitmap_ProfileImage", profileImage)
                    messageIntent!!.putExtra("EditText_FirstName", firstName)
                    messageIntent!!.putExtra("EditText_MiddleName", middleName)
                    messageIntent!!.putExtra("EditText_LastName", lastName)
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
            if (Build.VERSION.SDK_INT >= 33) {
                profileImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                imageViewPicture!!.setImageBitmap(profileImage)
            } else {
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                imageViewPicture!!.setImageBitmap(thumbnailImage)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        if (savedInstanceState != null) {
            editTextFirstName!!.setText(savedInstanceState.getString("FirstName_TEXT"))
            editTextMiddleName!!.setText(savedInstanceState.getString("MiddleName_TEXT"))
            editTextLastName!!.setText(savedInstanceState.getString("LastName_TEXT"))
            if (Build.VERSION.SDK_INT >= 33) {
            imageViewPicture!!.setImageBitmap(savedInstanceState.getParcelable("Bitmap_Image", Bitmap::class.java))
                } else{
                imageViewPicture!!.setImageBitmap(savedInstanceState.getParcelable("Bitmap_Image"))
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        firstName = editTextFirstName!!.text.toString()
        middleName = editTextMiddleName!!.text.toString()
        lastName = editTextLastName!!.text.toString()

        //Put them in the outgoing Bundle
        outState.putParcelable("Bitmap_ProfileImage",profileImage);
        outState.putString("FirstName_TEXT", firstName)
        outState.putString("MiddleName_TEXT", middleName)
        outState.putString("LastName_TEXT", lastName)
    }
}