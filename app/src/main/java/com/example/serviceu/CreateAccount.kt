package com.example.serviceu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty



class CreateAccount : AppCompatActivity() {

    private lateinit var firstName:EditText
    private lateinit var lastName:EditText
    private lateinit var email:EditText
    private lateinit var phoneNumber:EditText
    private lateinit var homeAddress :EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioGroupOne: RadioGroup
    private lateinit var btCreate : Button
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var category: TextView
    private lateinit var categorySpinner: Spinner
    private lateinit var radioCustomer: RadioButton
    private lateinit var radioProvider: RadioButton
    private lateinit var backButton : Button
    private lateinit var loginButton : Button
    private lateinit var createAccountButton : Button


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        firstName = findViewById(R.id.et_firstname)
        lastName = findViewById(R.id.et_lastname)
        email = findViewById(R.id.et_EmailAddress)
        phoneNumber = findViewById(R.id.et_phoneNumber)
        homeAddress = findViewById(R.id.et_City)
        btCreate = findViewById(R.id.bt_createAccount)
        radioGroup = findViewById(R.id.rb_Gender)
        radioGroupOne = findViewById(R.id.rb_Rule)
        password = findViewById(R.id.et_password)
        confirmPassword = findViewById(R.id.et_confirmPassword)
        category = findViewById(R.id.tv_category)
        categorySpinner = findViewById(R.id.sp_category)
        radioCustomer = findViewById(R.id.rb_customer)
        radioProvider = findViewById(R.id.rb_serviceProvider)
        backButton = findViewById(R.id.back_button)
        loginButton = findViewById(R.id.tv_loginButton)
        createAccountButton = findViewById(R.id.bt_createAccount)



        backButton.setOnClickListener {
            val intent = Intent(this, LoginSignUp::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        createAccountButton.setOnClickListener {
            val intent = Intent(this, Services::class.java)
            startActivity(intent)
        }


        btCreate.setOnClickListener {

            val firstname = firstName.text.toString().trim()
            val lastname = lastName.text.toString().trim()
            val emailAddress = email.text.toString().trim()
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+")
            val phonePattern = "^9\\d{9}$".toRegex()
            val number = phoneNumber.text.toString().trim()
            val address = homeAddress.text.toString().trim()
            val selectedRbGender = radioGroup.checkedRadioButtonId
            val selectedRb = radioGroup.checkedRadioButtonId
            var selectedGender = ""
            var selected = ""
            val pass = password.text.toString()
            val confirmPass = confirmPassword.text.toString()


            if (selectedRbGender != -1) {
                val selectedRdOne = findViewById<RadioGroup>(selectedRbGender)
                selectedGender = selectedRbGender.toString()
            }

            if (selectedRb != -1) {
                val selectedRbTwo = findViewById<RadioGroup>(selectedRb)
                selected = selected.toString()
            }

            if (firstname.isEmpty()) {
                firstName.error = "Required"
                firstName.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (lastname.isEmpty()) {
                lastName.error = "Required"
                lastName.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (emailAddress.isEmpty() || !emailAddress.matches(emailPattern)) {
                email.error = "Invalid Email Address"
                email.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (number.isEmpty() || !number.matches(phonePattern)) {
                phoneNumber.error = "Invalid Phone Number"
                phoneNumber.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (address.isEmpty()) {
                homeAddress.error = "Required"
                homeAddress.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (selectedGender.isEmpty()) {
                return@setOnClickListener

            }

            if (selected.isEmpty()) {
                return@setOnClickListener

            }

            if (pass.isEmpty() || confirmPass.isEmpty()) {
                password.error = "Password Required"
                password.visibility = View.VISIBLE
                return@setOnClickListener

            } else if (pass != confirmPass) {
                confirmPassword.error = "Password do not match"
                confirmPassword.visibility = View.VISIBLE
                return@setOnClickListener

            }else {
                firstName.error = null
                lastName.error = null
                email.error = null
                phoneNumber.error = null
                homeAddress.error = null
                password.error = null
                confirmPassword.error = null
                category.visibility = TextView.INVISIBLE

                Toast.makeText(this, "Validation Completed", Toast.LENGTH_SHORT).show()

            }

        }
        val categories = arrayOf(
            "Laundry", "Electrician", "Cleaning", "Pest Control", "Plumber",
            "Makeup Artist", "Hair Stylist", "Mechanic", "Manicure/Pedicure"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        categorySpinner.adapter = adapter

        radioGroupOne.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.rb_customer) {
                categorySpinner.visibility = View.GONE
            } else if (checkedId == R.id.rb_serviceProvider) {
                categorySpinner.visibility = View.VISIBLE
            }
        }

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                category.text = "Category: $selectedCategory"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                category.text = "Category:"

            }
        }

    }

}
