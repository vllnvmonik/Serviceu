package com.example.serviceu

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceu.classes.SharedPreferenceClass
import com.vishnusivadas.advanced_httpurlconnection.PutData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class Book : AppCompatActivity(),DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    private lateinit var btn_datetimePicker: Button
    private lateinit var tv_dateDisplay: TextView
    private lateinit var tv_timeDisplay: TextView
    private lateinit var name: TextView
    private lateinit var service: TextView
    private lateinit var backButton: ImageView
    private lateinit var proceedButton: Button
    private var selectedDateTime: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        btn_datetimePicker = findViewById(R.id.btn_datetimePicker)
        tv_dateDisplay = findViewById(R.id.tv_dateDisplay)
        tv_timeDisplay = findViewById(R.id.tv_timeDisplay)
        service = findViewById(R.id.service)

        backButton = findViewById(R.id.back_button)
        proceedButton = findViewById(R.id.btn_proceed)

        val sharedPreferenceHelper = SharedPreferenceClass(this)

        backButton.setOnClickListener {
            val intent = Intent(this, Services::class.java)
            startActivity(intent)
            finish()
        }

        proceedButton.setOnClickListener {
            if (dateTimeValidation()){
                CoroutineScope(Dispatchers.IO).launch {

                    val userId = sharedPreferenceHelper.getUserId()
                    Log.d("USERID", userId.toString())
                    val field = arrayOf("userId", "providerName", "providerService", "bookingDate", "bookingTime")
                    val data = arrayOf(
                        userId.toString(),
                        name.text.toString(),
                        service.text.toString(),
                        tv_dateDisplay.text.toString(),
                        tv_timeDisplay.text.toString()
                    )

                    val putData = PutData("https://serviceuapp.000webhostapp.com/bookings.php", "POST", field, data)
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            val result = putData.result
                            withContext(Dispatchers.Main) {
                                if (result == "Booking Successful") {

                                    Toast.makeText(this@Book, "Booking Successful", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@Book, Services::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this@Book, "Booking Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }

        }
        getSpDetails()

        pickDate()
    }
    //
    fun getSpDetails(){
        val providerName = intent.getStringExtra("providerName")
        val providerService = intent.getStringExtra("providerService")

        name = findViewById(R.id.name)
        service = findViewById(R.id.service)

        name.text = providerName
        service.text = providerService
    }


    private fun getDateTimeCalendar() {
        val calendar = Calendar.getInstance()
        day = calendar.get(Calendar.DAY_OF_MONTH)
        month = calendar.get(Calendar.MONTH)
        year = calendar.get(Calendar.YEAR)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        btn_datetimePicker.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        val date = Calendar.getInstance()
        date.set(savedYear, savedMonth, savedDay)

        tv_dateDisplay.text = dateFormat.format(date.time)

        getDateTimeCalendar()

        TimePickerDialog(this, this, hour, minute, false).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        val amPm: String
        val formattedHour: Int

        if (savedHour >= 12){
            amPm = "PM"
            formattedHour = if (savedHour > 12) savedHour - 12 else savedHour
        }else{
            amPm = "AM"
            formattedHour = if (savedHour==0) 12 else savedHour
        }
        val formattedMinute = String.format(Locale.getDefault(), "%02d", savedMinute)
        tv_timeDisplay.text = "$formattedHour:$formattedMinute $amPm"
    }

    private fun dateTimeValidation(): Boolean {
        val selectDate = tv_dateDisplay.text.toString()
        val selectTime = tv_timeDisplay.text.toString()

        if (selectDate.isEmpty() || selectTime.isEmpty()) {
            Toast.makeText(this, "Please select a date and time to book", Toast.LENGTH_SHORT).show()
            return false
        }

        val dateTimeString = "$selectDate $selectTime"
        val dateFormat = SimpleDateFormat("MMM d, yyyy h:mm a", Locale.getDefault())

        try {
            val selectedDate = dateFormat.parse(dateTimeString)

            if (selectedDate != null) {
                val currentDate = Calendar.getInstance().time

                if (selectedDate.before(currentDate)) {
                    Toast.makeText(this, "Please select a future date and time to book", Toast.LENGTH_SHORT).show()
                    return false
                }

                selectedDateTime = selectedDate
            } else {
                Toast.makeText(this, "Invalid date and time format", Toast.LENGTH_SHORT).show()
                return false
            }
        } catch (e: ParseException) {
            Toast.makeText(this, "Please select date and time", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}
