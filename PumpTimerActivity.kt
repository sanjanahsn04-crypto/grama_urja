package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PumpTimerActivity : AppCompatActivity() {

    // Minutes needed per acre for each crop
    private val cropMinutes = mapOf(
        "Paddy (Rice)"  to 60,
        "Ragi"          to 40,
        "Sugarcane"     to 30,
        "Vegetables"    to 20,
        "Cotton"        to 25
    )
    private val acreValues = listOf(0.5, 1.0, 2.0, 3.0, 5.0)

    private var selectedMins  = 60
    private var selectedAcres = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pump_timer)

        val spinnerCrop  = findViewById<Spinner>(R.id.spinnerCrop)
        val spinnerAcres = findViewById<Spinner>(R.id.spinnerAcres)
        val tvResult     = findViewById<TextView>(R.id.tvResult)

        // Set up crop spinner
        val cropAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            cropMinutes.keys.toList()
        )
        cropAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCrop.adapter = cropAdapter

        // Set up acres spinner
        val acreAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            acreValues.map { "${it} acre" }
        )
        acreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAcres.adapter = acreAdapter

        fun calculate() {
            val total = (selectedMins * selectedAcres).toInt()
            tvResult.text = "$total minutes"
        }

        spinnerCrop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>, v: View?, pos: Int, id: Long) {
                selectedMins = cropMinutes.values.toList()[pos]
                calculate()
            }
            override fun onNothingSelected(p: AdapterView<*>) {}
        }

        spinnerAcres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>, v: View?, pos: Int, id: Long) {
                selectedAcres = acreValues[pos]
                calculate()
            }
            override fun onNothingSelected(p: AdapterView<*>) {}
        }

        calculate()
    }
}
