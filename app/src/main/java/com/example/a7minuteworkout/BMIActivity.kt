package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode
import android.view.Window;
import android.view.WindowManager
import androidx.core.content.ContextCompat

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNITS_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)
        val actionbar = supportActionBar

        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "CALCULATE BMI"
        }
        val window: Window = this@BMIActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this@BMIActivity, R.color.colorAccent)

        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if(currentVisibleView == METRIC_UNITS_VIEW) {
                if(validateMetricUnits()) {
                    val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() / 100
                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue/ (heightValue*heightValue)

                    displayBMIResult(bmi)
                }
                else {
                    Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                if(validateUSUnits()) {
                    val heightValueFeet: String = etUSUnitHeightFeet.text.toString()
                    val heightValueInch: String = etUSUnitHeightInch.text.toString()
                    val heightValue: Float = heightValueInch.toFloat() + heightValueFeet.toFloat()*12
                    val weightValue: Float = etUSUnitWeight.text.toString().toFloat()

                    val bmi = 703 * (weightValue/ (heightValue*heightValue))
                    displayBMIResult(bmi)
                }
                else {
                    Toast.makeText(this@BMIActivity, "Please enter valid values", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitView()
        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitView()
            }
            else {
                makeVisibleUsUnitView()
            }
        }
    }

    private fun makeVisibleMetricUnitView() {

        currentVisibleView = METRIC_UNITS_VIEW

        llMetricUnitsView.visibility = View.VISIBLE

        etMetricUnitWeight.text!!.clear()
        etMetricUnitHeight.text!!.clear()

        llUsUnitsView.visibility = View.GONE

        llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitView() {

        currentVisibleView = US_UNITS_VIEW

        llMetricUnitsView.visibility = View.GONE

        etUSUnitWeight.text!!.clear()
        etUSUnitHeightFeet.text!!.clear()
        etUSUnitHeightInch.text!!.clear()

        llUsUnitsView.visibility = View.VISIBLE

        llDisplayBMIResult.visibility = View.INVISIBLE

    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String


        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }
        else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }
        else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        }
        else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

//        tvBMIDescription.visibility = View.VISIBLE
//        tvBMIType.visibility = View.VISIBLE
//        tvBMIValue.visibility = View.VISIBLE
//        tvYourBMI.visibility = View.VISIBLE

        llDisplayBMIResult.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if(etMetricUnitHeight.text.toString().isEmpty() || etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true

        if(etUSUnitHeightFeet.text.toString().isEmpty() || etUSUnitHeightInch.text.toString().isEmpty() || etUSUnitWeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }
}