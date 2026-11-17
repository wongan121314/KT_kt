package com.testing

import android.R.color
import android.graphics.Color
import android.net.Uri
import android.view.Gravity
import android.widget.ListView
import android.widget.TextView
import com.KT_kt.KtActivity
import com.KT_kt.addViews
import com.KT_kt.findViewByTag
import com.KT_kt.freeze
import com.KT_kt.ktButton
import com.KT_kt.ktColumnLayout
import com.KT_kt.ktFilePicker
import com.KT_kt.ktListView
import com.KT_kt.ktMediaPlayer
import com.KT_kt.ktTextView
import com.KT_kt.ktToast
import com.KT_kt.refresh

class MainActivity: KtActivity() {

    val mp = ktMediaPlayer()
    val musicList: MutableList<Uri> = mutableListOf()

    override fun onCreate() {
        super.onCreate()

        setContentView(
            ktColumnLayout {
                addViews(
                    ktListView(musicList).apply {
                        setTag("lsView")
                    },
                    ktTextView ("testing .freeze(true)", color = Color.RED, size = 30f).apply {
                        freeze(true)
                        gravity = Gravity.CENTER
                        // setTag("txtView")
                        id = 1
                        tag = "txtView"
                    },
                    ktButton("pick files").apply {
                        setOnClickListener {
                            ktFilePicker("audio/*", true) {files ->
                                ktToast(files.size.toString()).show()
                                findViewByTag<TextView>("txtView")?.text = "${files.size}  were selected"
                                findViewByTag<TextView>("txtView")?.setTextColor(Color.BLUE)
                                
                                musicList.addAll(files)
                                
                                findViewByTag<ListView>("lsView")?.refresh()
                            }
                        }
                    }

                )
            }

        )
    }
}
