package com.ktkt

import com.KT_kt.*

// private val count = useState(0)   can be even here
class MainActivity : KtActivity() {

    private val count = useState(0)

    override fun onCreate() {
        super.onCreate()

        ktDebugger().all().show()

        setContentView(
            ktButton("0").apply {
                useEffect(count) {text = it.toString()}
                setOnClickListener {
                    count.value += 1
                }
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
