package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import java.io.File

class Add : AppCompatActivity() {
    val IMAGE_REQUEST_CODE = 1_000;

    //Our variables
    // private var mImageView: ImageView? = null
    lateinit var img:String
    private var mUri: Uri? = null
    var categoryy:String=""
    val REQUEST_IMAGE_CAPTURE = 1
    //Our widgets
    private lateinit var btnCapture: Button
    private lateinit var btnChoose: Button
    private lateinit var name:EditText
    private lateinit var duration:EditText
    private lateinit var serving:EditText
    private lateinit var ingredients:EditText
    private lateinit var procedure:EditText
    private lateinit var spinner:Spinner
    //Our constants
    private val OPERATION_CAPTURE_PHOTO = 1
    private val OPERATION_CHOOSE_PHOTO = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        btnCapture = findViewById(R.id.take)

        val categ = resources.getStringArray(R.array.category)
        spinner = findViewById(R.id.category)

        if (spinner != null) {
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, categ)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {


                    categoryy = (categ[position]).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        btnCapture.setOnClickListener { pickImageFromGallery() }
       findViewById<Button>(R.id.upload).setOnClickListener {
            var title = findViewById<EditText>(R.id.name).text.toString()
            var people =findViewById<EditText>(R.id.people).text
            var ingredients = findViewById<EditText>(R.id.ingridients).text.toString()
            var time = findViewById<EditText>(R.id.time).text
            var procedure = findViewById<EditText>(R.id.procedure).text.toString()
        //    Toast.makeText(requireContext(), people,Toast.LENGTH_LONG ).show()
            val Recipe = Recipe(title,categoryy , time.toString(),
                people.toString(), ingredients, procedure, img)
            val database = Firebase.database
            val myRef = database.getReference("Recipe")
            myRef.child(title).setValue(Recipe)
        }

    }
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            // binding.imageView.setImageURI(data?.data)
            //  Toast.makeText(this,""+data?.data,Toast.LENGTH_LONG).show()
            img=data?.data.toString()
            // val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
            // val path = saveImage(bitmap)
           // Toast.makeText(requireContext(), "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }
        
   /* private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            //binding.imgViewer.setImageBitmap(imageBitmap)
          //  img=imageBitmap.toString()
          //  img=data?.extras?.get("data")
           // Toast.makeText(this, img, Toast.LENGTH_LONG).show()
        }
    }*/


}