package com.example.serviceu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.database.DatabaseHelper
import com.example.serviceu.database.User


class CreateAccount : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var homeAddress: EditText
    private lateinit var btCreate: Button
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var category: TextView
    private lateinit var categorySpinner: Spinner
    private lateinit var radioGroupRole: RadioGroup
    private lateinit var radioCustomer: RadioButton
    private lateinit var radioProvider: RadioButton
    private lateinit var backButton: ImageView
    private lateinit var loginButton: TextView

    //database
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        //database
        db = DatabaseHelper(this)


        fullName = findViewById(R.id.et_fullname)
        email = findViewById(R.id.et_EmailAddress)
        phoneNumber = findViewById(R.id.et_phoneNumber)
        homeAddress = findViewById(R.id.et_City)
        btCreate = findViewById(R.id.bt_createAccount)
        password = findViewById(R.id.et_password)
        confirmPassword = findViewById(R.id.et_confirmPassword)
        category = findViewById(R.id.tv_category)
        categorySpinner = findViewById(R.id.sp_category)
        radioGroupRole = findViewById(R.id.rb_role)
        radioCustomer = findViewById(R.id.rb_customer)
        radioProvider = findViewById(R.id.rb_serviceProvider)
        backButton = findViewById(R.id.back_button)
        loginButton = findViewById(R.id.tv_loginButton)


        // this is for the spinner or dropdown
        val categories = arrayOf(
            "Laundry", "Electrician", "Cleaning", "Pest Control", "Plumber",
            "Makeup Artist", "Hair Stylist", "Mechanic", "Manicure/Pedicure"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCategory = categories[position]
                category.text = "Category: $selectedCategory"
            }

            @SuppressLint("SetTextI18n")
            override fun onNothingSelected(parent: AdapterView<*>?) {
                category.text = "Category:"

            }

        }

        // this is for the radio buttons
        radioGroupRole.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.rb_customer) {
                categorySpinner.visibility = View.INVISIBLE
                category.visibility = View.INVISIBLE
            } else if (checkedId == R.id.rb_serviceProvider) {
                categorySpinner.visibility = View.VISIBLE
                category.visibility = View.VISIBLE
            }
        }


        // buttons functionalities
        backButton.setOnClickListener {
            val intent = Intent(this, LoginSignUp::class.java)
            startActivity(intent)
            finish()
        }


        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }


        btCreate.setOnClickListener {
            if (signUpValidation()){
                val selectedRole = findViewById<RadioButton>(radioGroupRole.checkedRadioButtonId)?.text.toString()

//                //database
                val user = User(
                    id = -1,
                    fullname = fullName.text.toString(),
                    email = email.text.toString(),
                    phone = phoneNumber.text.toString(),
                    address = homeAddress.text.toString(),
                    role = selectedRole,
                    category = category.text.toString(),
                    password = password.text.toString(),
                )
                db.createAccount(user)

                clearErrors()

                Toast.makeText(this, "Validation Completed", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Services::class.java)
                startActivity(intent)
                finish()
            }
        }

    }

    // all of the functions are here
    // |
    // v


    private fun clearErrors() {
        fullName.error = null
        email.error = null
        phoneNumber.error = null
        homeAddress.error = null
        password.error = null
        confirmPassword.error = null
        category.visibility = TextView.INVISIBLE
    }
    // function to show errors
    private fun showError(field: EditText, message: String) {
        field.error = message
        field.visibility = View.VISIBLE
    }

    // validation functions
    private fun signUpValidation(): Boolean {
        // Edit texts
        val fullname = fullName.text.toString().trim()
        val emailAddress = email.text.toString().trim()
        val phonePattern = "^9\\d{9}$".toRegex()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+".toRegex()
        val number = phoneNumber.text.toString().trim()
        val address = homeAddress.text.toString().trim()
        val pass = password.text.toString()
        val confirmPass = confirmPassword.text.toString()

        // RadioButtons
        val selectedRole =
            findViewById<RadioButton>(radioGroupRole.checkedRadioButtonId)?.text.toString()

        try {
            if (fullname.isEmpty()) {
                showError(fullName, "Required")
                return false
            }

            if (emailAddress.isEmpty() || !emailAddress.matches(emailPattern)) {
                showError(email, "Invalid Email Address")
                return false
            }

            if (number.isEmpty() || !number.matches(phonePattern)) {
                showError(phoneNumber, "Invalid Phone Number")
                return false
            }

            if (address.isEmpty()) {
                showError(homeAddress, "Required")
                return false
            }

            if (selectedRole.isEmpty()) {
                return false
            }

            if (pass.isEmpty() || confirmPass.isEmpty()) {
                showError(password, "Password Required")
                return false
            }

            if (pass != confirmPass) {
                showError(confirmPassword, "Password do not match")
                return false
            }
        }catch (e:Exception){
            Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        return true

    }
}

