package com.example.chronomedit


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivity : AppCompatActivity() {
    private var totalSeconds = 0
    private var running = false
    private lateinit var handler: Handler


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startButton = findViewById<Button>(R.id.start_button)
        val restartButton = findViewById<Button>(R.id.restart_button)
        val resetButton = findViewById<Button>(R.id.reset_button)
        val finishButton = findViewById<Button>(R.id.finish_button)
        val timerTextView = findViewById<TextView>(R.id.timer_text)
        var listView=findViewById<ListView>(R.id.listView)
        var timelist=ArrayList<String>()
        var listWiewLayout =findViewById<LinearLayoutCompat>(R.id.listWiewLayout)
        var linearlayout1 = findViewById<LinearLayoutCompat>(R.id.linearlayout1)


        handler = Handler()

        startButton.setOnClickListener {
            running = true
            handler.postDelayed(timerRunnable, 1000)
            timerTextView.setVisibility(View.INVISIBLE)
        }

        restartButton.setOnClickListener {
            running = false
            handler.removeCallbacks(timerRunnable)
            timerTextView.setVisibility(View.VISIBLE)
            timelist.add(timerTextView.text.toString())
            val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,timelist)
            listView.adapter = adapter
            totalSeconds = 0

            running = true
            handler.postDelayed(timerRunnable, 1000)
            timerTextView.setVisibility(View.INVISIBLE)


        }

        finishButton.setOnClickListener {
            running = false
            handler.removeCallbacks(timerRunnable)
            //val width:Int= linearlayout1.getMeasuredHeight()
            listWiewLayout.layoutParams = ConstraintLayout.LayoutParams(400,1200)
        }


        resetButton.setOnClickListener {
            //val width:Int= linearlayout1.getMeasuredHeight()
            running = false
            totalSeconds = 0
            updateTimerText(timerTextView)
            timerTextView.setVisibility(View.VISIBLE)
            listWiewLayout.layoutParams = ConstraintLayout.LayoutParams(400,30)

            timelist.clear()

            val params = restartButton.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(10,200,10,10)

        }
    }

    private val timerRunnable = object : Runnable {
        override fun run() {
            val timerTextView = findViewById<TextView>(R.id.timer_text)
            totalSeconds++
            updateTimerText(timerTextView)
            handler.postDelayed(this, 1000)
        }
    }

    private fun updateTimerText(timerTextView: TextView) {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        val formattedTime = String.format("%02d:%02d", minutes, seconds)
        timerTextView.text = formattedTime
    }
}


