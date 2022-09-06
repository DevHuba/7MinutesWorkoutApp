package eu.devhuba.a7_minutes_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import eu.devhuba.a7_minutes_workout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.tbBMI)

        if (supportActionBar != null) {
            //Enable back arrow in toolBar
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //Hide toolbar title
            supportActionBar?.setDisplayShowTitleEnabled(false);
        }


        //Taskbar back button
        binding.tbBMI.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }

        binding.btnCalculateUnits.setOnClickListener {

                //BMI calculation
                val height: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weight: Float = binding.etMetricUnitWeight.text.toString().toFloat()
                val bmi = weight / (height * height)

                //Show BMI calculation results in UI
                displayBMIResults(bmi)

        }


    }

    private fun displayBMIResults(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        //Descriptions accordingly to bmi value
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! Please eat more and Your body praise you ..."
        } else if (bmi.compareTo(15f) > 0 && (bmi.compareTo(16f)) <= 0) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! Please eat more and Your body praise you ..."
        } else if (bmi.compareTo(16f) > 0 && (bmi.compareTo(18.5f)) <= 0) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! Please eat more and Your body praise you ..."
        } else if (bmi.compareTo(18.5f) > 0 && (bmi.compareTo(25f)) <= 0) {
            bmiLabel = "Good"
            bmiDescription = "Congrats ! You are in a good shape !"
        } else if (bmi.compareTo(25f) > 0 && (bmi.compareTo(30f)) <= 0) {
            bmiLabel = "Overweight"
            bmiDescription = "Be sure that you feel yourself comfortable  and if no  do more workout ..."
        } else if (bmi.compareTo(30f) > 0 && (bmi.compareTo(35f)) <= 0) {
            bmiLabel = "Obese"
            bmiDescription = "Strongly recommend You to take care of yourself !"
        } else if (bmi.compareTo(35f) > 0 && (bmi.compareTo(40f)) <= 0) {
            bmiLabel = "Very obese"
            bmiDescription = "Take a gym pass right now !"
        } else {
            bmiLabel = "Extremely obese"
            bmiDescription = "Nothing to say here..."
        }

        //Change color of BMI value
        if (bmi <= 18.4 || bmi >= 25.1) {
            binding.tvBMIValue.setTextColor(ContextCompat.getColor(this, R.color.colorFour))
            binding.tvBMITitle.setTextColor(ContextCompat.getColor(this, R.color.colorFour))
        } else {
            binding.tvBMIValue.setTextColor(ContextCompat.getColor(this, R.color.colorSix))
            binding.tvBMITitle.setTextColor(ContextCompat.getColor(this, R.color.colorSix))

        }

        //Format bmi raw value
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding.llDisplayBMIResult.visibility = View.VISIBLE
        binding.tvBMIValue.text = bmiValue
        binding.tvBMITitle.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription

    }



}