
# version 1.9

* added:
    - ktIntent() /ktintent{dial()}
    - KtMediaPlayer(), with .setFile() which automatically does all and .play() .stop()  etc
    - functions must start  with small letter: camero casing  e.g   ``` fun ktButton(){}```
    - class must start with capital letter e.g ``` class KtActivity{} ```

* updated:
    - included aliases like orientation constant,gravity constant in KtWidgets file

---

# version 2.0

* added:
    - missing constant to the  ktAudio/video/image e.g val DATE_MODIFIED = MediaStore.AudiMedia.DATE_MODIFIED
    - ktThumbnail(uri: Uri,width: Int = 200,height: Int = 200)
    - @file:Suppress('ALL') in all files
    - ktSQlite()  with methods like .exec('..',args),.query('..',args)
    - KtAlias file where i can put all the constacts and aliases e.g ktColumnLayout
    - ktCheckedTextview ,ktChronometer,ktNumberPicker,ktDatePicker,ktCalendarView,ktTimePicker
    - ktAdapter and ktExpandableAdapter helpers for views like ktListView,ktSpiner + KtAdaptation
    - added diff types of default ktDialog and also use setView(view) to add c ustom view
    - add ktShapedrawable,ktRippledrawable and ktGradientDrawable

* updated:
    - have to use ktVibration fun now   with easy methods .start(),.stop() and .cancel()
    - KtColor to be an object not a class
    - KtClipBoard() copy(),.paste() and .clear()
    - ktIntent ,added openCamera picker,openDocument,closeActivity, etc
    - kt(views/widgets) e.g ktButton ,ktRowLayout etc (optional parameters)
    - removed the function overroding for the widgets, now its option parameters
    - ktCardLayout
    - ktListView to support dynamic list
    - ktFilePicker to support returning mutliple when par is true

* removed:
    - KtSelector file and its contents
    - ktLabelText
    - ktDivider
    - ktGap
    - KtShape