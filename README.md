# version 
    * Major
        - deprecated ktFilePicker  use ktIntent().apply{pickFile()}.setOnResultListener{t->..}
        - deprecated KtFileManager 
        
    * Minor
        - added tag arguement in most views    
        - added short names to MATCH_PARENT as FULL AND WRAP_CONENT as AUTO
        - added View  .gone(),.show(),.hide()    or u can use the aliases  visibility = INVISIBLE/VISIBLE/GONE
        - made regular methods of 4 args to support 1(all) , 2(x|H,y|V)  ; setpadding/margin,setSize,setcornerradius
        - (file picker functions moved to KtFileManager file)
        - added useState<>()
        - added KtIntent().setOnResultListener{t->...}.startForResult()  //not.start()
        - added ktNotification(), it supports all the methods even creating all the types using it
        - added ktNotification().showForeground() use it to start foreground and not .show() to be used inside the service activity for 