package com.example.mymusicapp

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_music_player.*

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0

    val navigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.action_profile ->{
                val intent = Intent(this@MusicPlayerActivity, MainActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_music -> {
                return@OnNavigationItemSelectedListener false
            }
            R.id.action_gallery -> {
                val intent = Intent(this@MusicPlayerActivity, GalleryActivity::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        supportActionBar!!.title = "Reproductor"

        mp = MediaPlayer.create(this, R.raw.demons_imagine)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        //volume bar
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser){
                        var volumeNumber = progress / 100.0f
                        mp.setVolume(volumeNumber, volumeNumber)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        //position Bar
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser){
                        mp.seekTo(progress)
                    }
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        //thread
        Thread(Runnable {
            while (mp != null){
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e: InterruptedException){
                }
            }
        }).start()

        val bottomNavigationM = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        bottomNavigationM.setOnNavigationItemSelectedListener(navigation)
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            //Update position
            positionBar.progress = currentPosition

            //Update labels
            var elapsedTime = createTimeLabel(currentPosition)
            lbElapsedTime.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            lbRemainingTime.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String{
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }

    fun btnPlayClick(v: View){
        if (mp.isPlaying){
            //Stop
            mp.pause()
            btnPlay.setBackgroundResource(R.drawable.play)

        }else{
            //start
            mp.start()
            btnPlay.setBackgroundResource(R.drawable.pause)
        }
    }

}