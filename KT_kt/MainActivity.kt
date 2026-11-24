package com.ktkt

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.KT_kt.*

class xMainActivity : KtActivity() {

    private val musicfiles = mutableListOf<Uri>()

    private val dark = Color.rgb(16, 19, 28)
    private val cardColor = Color.rgb(32, 40, 58)
    private val green = Color.rgb(53, 212, 154)
    private val white = Color.WHITE
    private val muted = Color.rgb(165, 176, 194)

    private lateinit var songTitle: TextView
    private lateinit var songInfo: TextView
    private lateinit var fileCount: TextView

    override fun onCreate() {
        super.onCreate()

        setContentView(
            ktScrollView(bgColor = dark) {

                addView(
                    ktColumnLayout(
                        width = ViewGroup.LayoutParams.MATCH_PARENT,
                        bgColor = dark
                    ) {

                        setPadding(20, 24, 20, 24)

                        // HEADER
                        addViews(
                            ktRowLayout {

                                gravity = Gravity.CENTER_VERTICAL

                                addViews(
                                    ktTextView(
                                        "My Music",
                                        25f,
                                        white
                                    ).apply {
                                        setTypeface(null, Typeface.BOLD)
                                    },

                                    ktTextView(
                                        "  ♪",
                                        25f,
                                        green
                                    )
                                )

                                addView(
                                    ktTextView(
                                        "2.1.2",
                                        12f,
                                        green
                                    )
                                )
                            },

                            ktGap(24),

                            // PLAYER CARD
                            ktCardLayout(
                                bgColor = cardColor,
                                cornerRadius = 24f,
                                elevation = 5f
                            ) {

                                addView(
                                    ktColumnLayout {

                                        setPadding(20, 22, 20, 22)

                                        gravity = Gravity.CENTER_HORIZONTAL

                                        // ARTWORK
                                        addView(
                                            ktCardLayout(
                                                width = 190,
                                                height = 190,
                                                bgColor = Color.rgb(48, 65, 85),
                                                cornerRadius = 24f
                                            ) {

                                                addView(
                                                    ktTextView(
                                                        "♫",
                                                        90f,
                                                        green
                                                    ).apply {
                                                        gravity = Gravity.CENTER
                                                    },
                                                    FrameLayout.LayoutParams(
                                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                                        ViewGroup.LayoutParams.MATCH_PARENT
                                                    )
                                                )
                                            }
                                        )

                                        ktGap(24)

                                        // SONG INFORMATION
                                        addView(
                                            ktTextView(
                                                "No song playing",
                                                22f,
                                                white
                                            ).apply {
                                                gravity = Gravity.CENTER
                                                setTypeface(null, Typeface.BOLD)
                                                tag = "song_title"
                                            }
                                        )

                                        ktGap(5)

                                        addView(
                                            ktTextView(
                                                "Select music from your device",
                                                14f,
                                                muted
                                            ).apply {
                                                gravity = Gravity.CENTER
                                                tag = "song_info"
                                            }
                                        )

                                        ktGap(25)

                                        // PROGRESS BAR
                                        addView(
                                            ktSeekBar().apply {
                                                max = 100
                                                progress = 0
                                            }
                                        )

                                        addView(
                                            ktRowLayout {

                                                gravity = Gravity.CENTER_VERTICAL

                                                addView(
                                                    ktTextView(
                                                        "00:00",
                                                        12f,
                                                        muted
                                                    )
                                                )

                                                addView(
                                                    ktTextView(
                                                        "00:00",
                                                        12f,
                                                        muted
                                                    ).apply {
                                                        gravity = Gravity.END
                                                        layoutParams =
                                                        LinearLayout.LayoutParams(
                                                            0,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                                            1f
                                                        )
                                                    }
                                                )
                                            }
                                        )

                                        ktGap(20)

                                        // PLAYBACK CONTROLS
                                        addView(
                                            ktRowLayout {

                                                gravity = Gravity.CENTER

                                                addViews(

                                                    ktButton(
                                                        "⏮",
                                                        25f,
                                                        white,
                                                        Color.TRANSPARENT
                                                    ),

                                                    ktButton(
                                                        "▶",
                                                        27f,
                                                        dark,
                                                        green
                                                    ).apply {
                                                        setPadding(20, 8, 20, 8)
                                                        background = android.graphics.drawable.GradientDrawable().apply {
                                                            setColor(green)
                                                            cornerRadius = 60f
                                                        }
                                                    },

                                                    ktButton(
                                                        "⏭",
                                                        25f,
                                                        white,
                                                        Color.TRANSPARENT
                                                    )
                                                )
                                            }
                                        )
                                    }
                                )
                            },

                            ktGap(28),

                            // LIBRARY HEADER
                            ktRowLayout {

                                gravity = Gravity.CENTER_VERTICAL

                                addView(
                                    ktTextView(
                                        "Your playlist",
                                        20f,
                                        white
                                    ).apply {
                                        setTypeface(null, Typeface.BOLD)
                                    }
                                )

                                addView(
                                    ktTextView(
                                        "0 songs",
                                        13f,
                                        green
                                    ).apply {
                                        gravity = Gravity.END
                                        layoutParams =
                                        LinearLayout.LayoutParams(
                                            0,
                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                            1f
                                        )
                                        tag = "file_count"
                                    }
                                )
                            },

                            ktGap(14),

                            // LIBRARY CARD
                            ktCardLayout(
                                bgColor = cardColor,
                                cornerRadius = 16f,
                                elevation = 2f
                            ) {

                                addView(
                                    ktRowLayout {

                                        gravity = Gravity.CENTER_VERTICAL

                                        setPadding(16, 18, 16, 18)

                                        addView(
                                            ktTextView(
                                                "♫",
                                                30f,
                                                green
                                            )
                                        )

                                        addView(
                                            ktColumnLayout {

                                                setPadding(14, 0, 0, 0)

                                                addView(
                                                    ktTextView(
                                                        "Audio collection",
                                                        16f,
                                                        white
                                                    ).apply {
                                                        setTypeface(
                                                            null,
                                                            Typeface.BOLD
                                                        )
                                                    }
                                                )

                                                addView(
                                                    ktTextView(
                                                        "Your selected files appear here",
                                                        12f,
                                                        muted
                                                    )
                                                )
                                            }
                                        )
                                    }
                                )
                            },

                            ktGap(20),

                            // SELECT FILES BUTTON
                            ktButton(
                                "＋   SELECT AUDIO FILES",
                                16f,
                                dark,
                                green,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                58
                            ).apply {

                                setTypeface(null, Typeface.BOLD)

                                setOnClickListener {
                                    // selectAudios()
                                }
                            }
                        )
                    }
                )
            }
        )

        songTitle = getViewByTag("song_title")!!
        songInfo = getViewByTag("song_info")!!
        fileCount = getViewByTag("file_count")!!
    }

    // fun play() {

    //     ktToast("Starting playback").show()

    //     ktIntent(TestService::class.java) {

    //         action = "PLAY"

    //         putParcelableArrayListExtra(
    //             "FILES",
    //             ArrayList(musicfiles)
    //         )

    //     }.startService()
    // }
}

class xxTestService : KtService() {

    private lateinit var mp: KtMediaPlayer
    private val musicfiles = mutableListOf<Uri>()

    override fun onCreate() {
        super.onCreate()
        mp = ktMediaPlayer()
        mp?.setOnCompletionListener {
            play()
        }
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
