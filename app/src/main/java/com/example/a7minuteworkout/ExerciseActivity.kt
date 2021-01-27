package com.example.a7minuteworkout

import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var restTimerDuration: Long = 10
    private var exerciseTimerDuration: Long = 30

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        var toolbarExerciseActivity = findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolbarExerciseActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        tts = TextToSpeech(this, this)

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

        if(tts !=null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null) {
            player!!.stop()
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
                currentExercisePosition++
                var restViewLl = findViewById<LinearLayout>(R.id.llRestView)
                restViewLl.visibility = View.GONE
                var exerciseViewLl = findViewById<LinearLayout>(R.id.llExerciseView)
                exerciseViewLl.visibility = View.VISIBLE
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView() {

        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false

            player!!.start()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }


        var restViewLl = findViewById<LinearLayout>(R.id.llRestView)
        restViewLl.visibility = View.VISIBLE
        var exerciseViewLl = findViewById<LinearLayout>(R.id.llExerciseView)
        exerciseViewLl.visibility = View.GONE

        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        var upcommingExerciseNameTv = findViewById<TextView>(R.id.tvUpcomingExerciseName)
        upcommingExerciseNameTv.text = exerciseList!![currentExercisePosition+1].getName()
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
                if(currentExercisePosition < exerciseList?.size!! - 1) {
                    setupRestView()
                }
                else {
                    Toast.makeText(this@ExerciseActivity, "Congratulations! You have completed the 7 minutes workout.", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun setupExerciseView() {
        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()

        var imageIv = findViewById<ImageView>(R.id.ivImage)
        imageIv.setImageResource(exerciseList!![currentExercisePosition].getImage())

        var exerciseNameTv = findViewById<TextView>(R.id.tvExerciseName)
        exerciseNameTv.text = exerciseList!![currentExercisePosition].getName()

        speakOut(exerciseList!![currentExercisePosition].getName())
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "The language specified is not supported!", Toast.LENGTH_SHORT).show()
                Log.e("TTS", "The language specified is not supported!")
            }
        }
        else {
            Toast.makeText(this, "Initialization Failed!", Toast.LENGTH_SHORT).show()
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}