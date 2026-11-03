package com.example.myapp

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout as AndroidLinearLayout

class MainActivity : ktActivity() {

    private val red = Color.rgb(220, 53, 69)
    private val dark = Color.rgb(35, 39, 47)
    private val grey = Color.rgb(110, 117, 128)
    private val light = Color.rgb(245, 246, 250)
    private val white = Color.WHITE
    private var nname = ""

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    override fun onCreate() {

        // Main screen
        val layout = LinearLayout {
            orientation = AndroidLinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(20))
            setBackgroundColor(light)
        }

        // Header card
        val header = LinearLayout {
            orientation = AndroidLinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(24))

            background = Shape {
                setColor(red)
                cornerRadius = dp(20).toFloat()
            }
        }

        val greeting = TextView {
            text = "WELCOME BACK 👋"
            textSize = 14f
            setTextColor(Color.WHITE)
        }

        val title = TextView {
            text = "Your Dashboard"
            textSize = 28f
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.WHITE)
        }

        val subtitle = TextView {
            text = "Manage your people in one place."
            textSize = 14f
            setTextColor(Color.WHITE)
        }

        header.addView(greeting)
        header.addView(title)
        header.addView(subtitle)

        // Login section
        val loginTitle = TextView {
            text = "Quick Login"
            textSize = 20f
            setTypeface(null, Typeface.BOLD)
            setTextColor(dark)
        }

        val username = EditText {
            hint = "Enter your username"
            textSize = 16f
            // singleLine = true2
            setSingleLine(true)
            setPadding(dp(14), dp(12), dp(14), dp(12))

            background = Shape {
                setColor(white)
                cornerRadius = dp(12).toFloat()
                setStroke(dp(1), Color.rgb(225, 228, 235))
            }
        }

        val login = Button {
            text = "Login"
            textSize = 16f
            setTextColor(Color.WHITE)

            background = Shape {
                setColor(red)
                cornerRadius = dp(12).toFloat()
            }

            setOnClickListener {
                val name = username.text.toString().trim()
                nname = name
                if (name.isEmpty()) {
                    username.error = "Please enter your username"
                } else {
                    Toast("Welcome, $name!").show()
                }
            }
        }

        // People section
        val peopleTitle = TextView {
            text = "People"
            textSize = 22f
            setTypeface(null, Typeface.BOLD)
            setTextColor(dark)
        }

        val peopleSubtitle = TextView {
            text = "Your registered members"
            textSize = 14f
            setTextColor(grey)
        }

        val names = listOf("John", "Mary", "Peter")
        val ages = listOf(20, 22, 19)

        val list = ListView(names.indices.toList()) { index ->

            val person = LinearLayout {
                orientation = AndroidLinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(dp(16), dp(14), dp(16), dp(14))

                background = Shape {
                    setColor(white)
                    cornerRadius = dp(14).toFloat()
                    setStroke(dp(1), Color.rgb(235, 237, 242))
                }
            }

            val personInfo = LinearLayout {
                orientation = AndroidLinearLayout.VERTICAL
            }

            val personName = TextView {
                text = names[index]
                textSize = 17f
                setTypeface(null, Typeface.BOLD)
                setTextColor(dark)
            }

            val personAge = TextView {
                text = "Age ${ages[index]}"
                textSize = 14f
                setTextColor(grey)
            }

            personInfo.addView(personName)
            personInfo.addView(personAge)

            val badge = TextView {
                text = "${ages[index]}"
                textSize = 15f
                setTypeface(null, Typeface.BOLD)
                setTextColor(red)
                gravity = Gravity.CENTER

                background = Shape {
                    setColor(Color.rgb(255, 235, 238))
                    cornerRadius = dp(12).toFloat()
                }

                setPadding(dp(14), dp(10), dp(14), dp(10))
            }

            // Give the information the remaining horizontal space.
            person.addView(
                personInfo,
                AndroidLinearLayout.LayoutParams(
                    0,
                    AndroidLinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
                )
            )

            person.addView(badge)

            person
        }

        // Assemble the screen
        layout.addView(
            header,
            AndroidLinearLayout.LayoutParams(
                AndroidLinearLayout.LayoutParams.MATCH_PARENT,
                AndroidLinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(24)
            }
        )

        layout.addView(
            loginTitle,
            AndroidLinearLayout.LayoutParams(
                AndroidLinearLayout.LayoutParams.MATCH_PARENT,
                AndroidLinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(12)
            }
        )

        layout.addView(
            username,
            AndroidLinearLayout.LayoutParams(
                AndroidLinearLayout.LayoutParams.MATCH_PARENT,
                AndroidLinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(12)
            }
        )

        layout.addView(
            login,
            AndroidLinearLayout.LayoutParams(
                AndroidLinearLayout.LayoutParams.MATCH_PARENT,
                AndroidLinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dp(28)
            }
        )

        layout.addView(peopleTitle)
        layout.addView(peopleSubtitle)

        layout.addView(
            list,
            AndroidLinearLayout.LayoutParams(
                AndroidLinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        setContentView(layout)
    }

    override fun onBackPressed() {

        val dialogContent = LinearLayout {
            orientation = AndroidLinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(24), dp(28), dp(24), dp(28))

            background = Shape {
                setColor(Color.WHITE)
                cornerRadius = dp(20).toFloat()
            }
        }

        val message = TextView {
            text = "Leaving already? ${nname}"
            textSize = 23f
            setTypeface(null, Typeface.BOLD)
            setTextColor(dark)
            gravity = Gravity.CENTER
        }

        val description = TextView {
            text = "Are you sure you want to leave this page?"
            textSize = 15f
            setTextColor(grey)
            gravity = Gravity.CENTER
        }

        val stay = Button {
            text = "Stay here"
            setTextColor(Color.WHITE)

            background = Shape {
                setColor(red)
                cornerRadius = dp(12).toFloat()
            }

            setOnClickListener {
                // Dismiss the dialog.
                (parent as? View)?.let {
                    // No action needed here.
                }
            }
        }

        dialogContent.addView(message)
        dialogContent.addView(description)
        dialogContent.addView(stay)

        val dialog = Dialog {
            setTitle("Exit")
            setContentView(dialogContent)
        }

        dialog.show()
    }
}
