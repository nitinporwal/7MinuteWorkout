package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class ExerciseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        var toolbarExerciseActivity = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbarExerciseActivity)

        val actionBar = supportActionBar
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}