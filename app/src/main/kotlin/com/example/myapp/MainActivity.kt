package com.example.myapp

import android.app.Activity
import android.net.Uri
import android.view.View
import com.KT_kt.*

class MainActivity : ktActivity() {
    override fun onCreate() {
        setContentView(mainLay())
    }
}

fun Activity.mainLay(): View {
    val songList = mutableListOf<Uri>()
    val mp = KtMediaPlayer()
    lateinit var songToPlay: Uri
    val songtxt = KtTextView("No song selected")
    
    fun playSong() {
        if(songList.isEmpty())return
        songToPlay = songList.random()
        songtxt.text = songToPlay.toString()
        
        mp.reset();mp.setFile(this@mainLay,songToPlay);mp.prepareAsync()
    }

    mp.setOnPreparedListener {
        it.start()
    }

    mp.setOnCompletionListener {
       if(songList.isNotEmpty()) playSong() else songtxt.text = "......";
    }
    
    return KtColumnLayout {
        val listView = KtListView(songList) { song ->
            KtTextView(song.toString()){
                textSize = 24f;setTextColor(KtColor.rgb(120, 9, 90))
            }
        }
        
        val selectbtn = KtButton("Select list of songs"){

            setOnClickListener {
                KtFilePickerMultiple("audio/*") { uris ->
                    songList.clear();songList.addAll(uris);listView.refresh()
                    songtxt.text = "${songList.size} songs selected"
                }
            }
        }

        val playbtn = KtButton("Start playing"){
            setOnClickListener {if(songList.isNotEmpty())playSong() else KtToast("empty").show();}
        }

        KtAddViews(songtxt,selectbtn, playbtn,listView)
    }
}
