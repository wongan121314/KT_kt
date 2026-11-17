package com.ktkt

import android.net.Uri
import com.KT_kt.*
import com.KT_kt.ktRowLayout
import kotlin.collections.forEach
import kotlin.random.Random

class MainActivity:KtActivity() {
    lateinit var mp:KtMediaPlayer
    lateinit  var musicFile:Uri

    val musicList = mutableListOf<Uri>()
    val txtView = findViewByTag <KtTextView >("txtView")
    val selectBtn = findViewByTag<KtButton>("selectionBtn")
    val listView = findViewByTag<KtListView>("listview")
    val playBtn = findViewByTag<KtButton>("playBtn")

    override fun onCreate() {
        super.onCreate()
        setContentView(mainScreen())

        // select the audios
        selectBtn?.setOnClickListener {
            ktFilePicker("audio/*", true) {files ->
                files!!.forEach {file ->
                    musicList.add(file)
                }
                ktToast("selected ${files.size.toString()}")
            }
            if(musicList.isNotEmpty())listView?.refresh() else ktToast("no selectec audios").show()
        }

        // play the song
        playBtn?.setOnClickListener {
            if(mp.isPlaying)mp.stop() else playSong()
        }
        mp.setOnCompletionListener {
            playSong()
        }

    }
    fun playSong() {
        musicFile = musicList.random()
        mp.setFile(musicFile)
        txtView?.text = musicFile.toString()
        mp.play()
    }
    fun mainScreen() :KtColumnLayout {
        val mainLay = ktColumnLayout {

            addViews(
                ktListView (musicList) {
                    setTag("listview")
                },

                ktTextView ("not playing", 20f, KtColor.hex("#004466")) {
                    setTag("txtView")},

                ktButton("select audio", 20f) {
                    setTag("selectionBtn") },

                ktButton("play or sotp", 16f) {
                    setTag("playBtn")
                }

            )
        }

        return mainLay

    }
    override fun onDestroy() {
        super.onDestroy()
    }

}
