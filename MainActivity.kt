package com.ktkt

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.View
import com.KT_kt.*

lateinit var mp:KtMediaPlayer

class MainActivity : KtActivity() {
    override fun onCreate() {
        super.onCreate()
        mp = ktMediaPlayer()
        setContentView(screen())
    }

    override fun onDestroy() {
        mp?.release()
        super.onDestroy()
    }
}

private  fun Activity.screen():View {
    val mainLay = ktColumnLayout {

        val tView = ktTextView("hello world") {
            textSize = 23.sp
            setTextColor(KtColor.hex("#004466"))
        }
        val btn = ktButton("Play audio") {
            setOnClickListener {
                ktFilePicker("audio/*") { file ->

                    if (file != null) {
                        ktThread({musicPlay(file)}) {res ->
                            res.onSuccess {
                                tView.setText(file.toString())
                            }
                        }

                    } else {
                        tView.text = "No file selected"
                    }

                }
            }

        }
        addViews(tView, btn)
    }

    return mainLay

}

fun Context.musicPlay(file:Uri):Unit {
    mp?.setFile(file)
    mp.play()
}
