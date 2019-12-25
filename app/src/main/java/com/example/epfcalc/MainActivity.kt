package com.example.epfcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

class MainActivity : AppCompatActivity() {

    private var dob : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c:Calendar = getInstance();
        val mYear = c.get(YEAR);
        val mMonth = c.get(MONTH);
        val mDay = c.get(DAY_OF_MONTH);

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, monthOfYear)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textViewSelectDob.text = sdf.format(c.time)

        }

        textViewSelectDob.setOnClickListener {
            DatePickerDialog(this@MainActivity, dateSetListener,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)).show()
        }

        buttonReset.setOnClickListener{
            textViewCalculatedInvestment.text = ""
            textViewCalculatedSaving.text = ""
            textViewCalculatedAge.text  = ""
            textViewSelectDob.text = "Select Date of Birth"
        }

        buttonOK.setOnClickListener {
            dob = c.get(Calendar.YEAR)

            textViewCalculatedAge.text = calcAge(dob).toString()
            textViewCalculatedSaving.text  = "RM" + basicSavings(calcAge(dob)).toString()
            textViewCalculatedInvestment.text = "RM" + calcInvestment().toString()
        }

    }

    private fun basicSavings(age : Int): Int{
        var savings = 0;

        when(age){
            in 16..20 -> savings = 5000
            in 21..25 -> savings = 14000
            in 26..30 -> savings = 29000
            in 31..35 -> savings = 50000
            in 36..40 -> savings = 78000
            in 41..45 -> savings = 116000
            in 46..50 -> savings = 165000
            in 51..55 -> savings = 228000
        }

        return savings
    }

    private fun calcAge(year: Int):Int{
        return getInstance().get(YEAR) - year
    }

    private fun calcInvestment():Double{
        return basicSavings(calcAge(dob))*0.3
    }
}
