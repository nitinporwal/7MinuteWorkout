package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var restTimerDuration: Long = 10
    private var exerciseTimerDuration: Long = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercise = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        var toolbarExerciseActivity = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()

        setupRestView()

        var restViewLl = findViewById<LinearLayout>(R.id.llRestView)
        restViewLl.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar() {
        var restProgressBar = findViewById<ProgressBar>(R.id.restProgressBar)
        restProgressBar.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                restProgressBar.progress = restTimerDuration.toInt()-restProgress
                var timer_tv = findViewById<TextView>(R.id.tvRestTimer)
                timer_tv.text = (restTimerDuration.toInt()-restProgress).toString()
            }

            override fun onFinish() {
                var restViewLl = findViewById<LinearLayout>(R.id.llRestView)
                restViewLl.visibility = View.GONE
                var exerciseViewLl = findViewById<LinearLayout>(R.id.llExerciseView)
                exerciseViewLl.visibility = View.VISIBLE
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setExerciseProgressBar() {
        var exerciseProgressBar = findViewById<ProgressBar>(R.id.exerciseProgressBar)
        exerciseProgressBar.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = exerciseTimerDuration.toInt()-exerciseProgress
                var timer_tv = findViewById<TextView>(R.id.tvExerciseTimer)
                timer_tv.text = (exerciseTimerDuration.toInt()-exerciseProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we will start the next rest screen.", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setupExerciseView() {
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }
}