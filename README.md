
* Major
    - added ktLayout, all widgets inside it will use widthP,heightP,topP,bottomP,leftp and rightP,centerX = Boolean,centerY = Boolean
       0.5f  = 50%,1.0f = 100% or matchparent, not propety defined = auto or wrapcontent,
       * do not use any android propety simillar to the above  e.g height, use heighP instead
```kotlin
setContentView(
    ktLayout {

        addViews(

            // Title
            ktTextView("Test the layout").apply {
                widthP = 0.8f
                height = WRAP_CONTENT
                topP = 0.05f
                leftP = 0.1f

                setBackgroundColor(dark)
                setTextColor(white)
            },

            // Card
            ktCardLayout().apply {
                widthP = 0.3f
                heightP = 0.3f
                topP = 0.20f
                centerX = true

                setBackgroundColor(green)
            }
        )
    }
}            
```
    - removed ktSelector
* Minor
    - fixed the ktLayout, it now to render its children when final size is known, also 0f  means auto wrap, 1f means matchparent,also ktLayout can be of wrap content sizes too
    - added layHeight and layWidth,some elements dont use width, they use layoutParams.width which is now layWidth
    - added useEffect, it can be used when not delegating ,there fore val x = useState() outside the class or as classs property will work ,just use useEffect(x){it}  [see](DOCS/home.md)
    - added ktAppKill(),ktAppQuit() and ktAppExit()\
    - added ktDebugger, it shows an overlay that thos all logcats [see](DOCS/home.md)

# issues
- ~~ktTextView not showing   : resolved, dont use width or height use with P~~
- ~~ktLayout on scrollView not working, resolved it, by using non zeroing
package com.ktkt~~
- want to be able to copy text from the ktdebugger
- need more work on the ktLayout, want to be simillar to contrainLayout


---
# examples

* useState and useEffect 
```kotlin
val count = useState(0)
class MainActivity: KtActivity() {
    override fun onCreate() {
        setContentView(
            ktButton("0") {
                useEffect(count) {text = it.toString()}
                setOnClickListener {
                    count.value += 1
                }
            }
        )
    }
}
```


```kotlin
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
}
```

* ktdebugger
```kotlin
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
}
```
