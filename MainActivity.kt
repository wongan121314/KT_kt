
package com.myapp

import android.graphics.Color
import com.KT_kt.*

class MainActivity : KtActivity() {

    override fun onCreate() {
        //configure your themes
        val (theme, setTheme) = ktTheme().apply {
            setStyle["primaryBgTheme"](Color.GREEN, Color.BLUE)
            setStyle["primaryTxtTheme"](Color.WHITE, Color.BLACK)
        }

        // toggle
        val nameToggle = ktToggle("james", "john")

        // counter
        val numcount = ktCounter(mutableListOf(1, 2, 3, 4, 5))
        //set theme mode: stystem(),day(),night()
        setTheme.system()
        super.onCreate()

        setContentView(

            ktConstraintLayout().apply {
                layHeight = FULL
                layWidth = FULL
                setBackgroundColor(theme.primary)
                addViews(
                    // HEADER
                    ktTextView("KT_kt lib testing: using ktContaintLayout,stringId,ktToggle,ktCounter") {
                        id = stringId("header")
                        setTextColor(theme["primaryTxtTheme"])
                        setBackgroundColor(theme["primaryBgTheme"])
                        setTextSize(13.sp)
                        topToTop = PARENT_ID
                        startToStart = PARENT_ID
                        endToEnd = PARENT_ID
                        topMargin = 5.dp
                        //bottomToBottom = PARENT_ID

                    },
                    ktButton("testing ktToggle") {
                        id = stringId("btn1")
                        setTextColor(theme["primaryTxtTheme"])
                        setBackgroundColor(theme["primaryBgTheme"])
                        setTextSize(23.sp)
                        topToTop = stringId("header")
                        startToStart = PARENT_ID
                        endToEnd = PARENT_ID
                        bottomToBottom = stringId("btn2")
                        setOnClickListener {
                            text = nameToggle.toggle()
                        }
                    },
                    ktButton("testing ktCounter") {
                        id = stringId("btn2")
                        setTextColor(theme["primaryTxtTheme"])
                        setBackgroundColor(theme["primaryBgTheme"])
                        setTextSize(23.sp)
                        topToTop = stringId("btn1")
                        startToStart = PARENT_ID
                        endToEnd = PARENT_ID
                        bottomToBottom = PARENT_ID
                        setOnClickListener {
                            text = numcount.count().toString()
                        }
                    }
                )
            }
        )

    }

}
