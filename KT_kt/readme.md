# 2.2.0

* removed
    - deprecated the ktColor class and methods see @XKtColor.kt
    - deprecated ktVibrattion.cancel  - use .stop()
    - deprecated freeze(status:Boolean), need more working on
    - deprecated findViewByTag
    - deprecated setId,use android way
    - deprecated setTag,use android way
    - deprecated ktFilePicker(type:String,multiple:Boolean).{files-> ...} use  ktFilePicker(type:String,multiple:Boolean).apply {setOnFilesSelectedListener {file/s-> }.launch()
    
* added new
    - added freezesTextColor,freezesBackgroundColor propety  
    - added freezesTextSize,
    - added freezesMe
    - added KtService class, with overload onStartcommand function(1or2or3args) and easly called from class using ktIntent(TestService::class.java).startService().stop(): remember to use manifest
    
* updates
    - updated the getViewById and getViewByTag,they can be used any where on any scope that normal android findViewwithTag and findViewById 
      even ktIntent(TestService::class.java) {action = "PLAY"}.startService()
    
# 2.2.1
* updates
    - fixed ktRowLayout and ktColumnLayout, their parameters where not inline to their ktLinearLayout
    
# 2.3.0
* major changes    
    - added ktUseState(var/val),  just a simple way of reactivity or use the kotlin way with MutableState()  then use .value and .observe{}
        - val (name,setName) = useState(value)  then use name.value