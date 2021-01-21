package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ll_start = findViewById<LinearLayout>(R.id.llStart)
        ll_start.setOnClickListener {
            Toast.makeText(this, "Activity started", Toast.LENGTH_SHORT).show()
        }
    }
}