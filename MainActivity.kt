
package com.example.testoingktv21x

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.widget.TextView
import com.KT_kt.*

class MainActivity : KtActivity() {

    private val musicfiles = mutableListOf<Uri>()

    override fun onCreate() {
        setContentView(
            ktColumnLayout {
                addViews(
                                        ktTextView(
                        "VERSION 2.1.1, simple music player app:",
                        22f,
                        Color.RED
                    ).apply {
                        tag = "tv"
                        id = 100
                        freezesMe = true
                    },
                    ktTextView(
                        "VERSION 2.1.1, simple music player app:",
                        22f,
                        Color.RED
                    ).apply {
                        tag = "tv"
                        id = 100
                        freezesMe = true
                    },

                    ktButton("Select audios from storage").apply {
                        setOnClickListener {

                            ktFilePicker("audio/*", true).apply {

                                setOnFilesSelectedListener { files ->

                                    if (files.isEmpty()) {
                                        getViewByTag<TextView>("tv")
                                        ?.text = "Select audios first"
                                        return@setOnFilesSelectedListener
                                    }

                                    musicfiles.clear()
                                    musicfiles.addAll(files)

                                    getViewByTag<TextView>("tv")
                                    ?.text = "${musicfiles.size} audios selected"

                                    play()
                                }
                            }.launch()
                        }
                    }
                )
            }
        )
    }

    fun play() {
        ktToast("start playing").show()
        ktIntent(TestService::class.java) {
            action = "PLAY"
            putParcelableArrayListExtra(
                "FILES",
                ArrayList(musicfiles)
            )
        }.startService()
    }
}

class TestService : KtService() {

    private lateinit var mp: KtMediaPlayer
    private val musicfiles = mutableListOf<Uri>()

    override fun onCreate() {
        super.onCreate()
        mp = ktMediaPlayer()
    }

    override fun onStartCommand(intent: Intent?): Int {

        when (intent?.action) {

            "PLAY" -> {

                val files = intent.getParcelableArrayListExtra<Uri>("FILES")

                if (!files.isNullOrEmpty()) {
                    musicfiles.clear()
                    musicfiles.addAll(files)
                    play()
                }
            }

            "STOP" -> {
                mp.stop()
                stopSelf()
            }
        }

        return START_NOT_STICKY
    }

    private fun play() {
        if (musicfiles.isNotEmpty()) {
            val file = musicfiles.random()

            mp.setFile(file)
            mp.isLooping = true
            mp.play()
        }
    }

    override fun onDestroy() {
        if (::mp.isInitialized) {
            mp.stop()
            mp.release()
        }

        super.onDestroy()
    }
}
