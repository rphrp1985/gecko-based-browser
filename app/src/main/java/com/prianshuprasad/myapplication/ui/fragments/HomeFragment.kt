package com.prianshuprasad.myapplication.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.prianshuprasad.myapplication.*
import com.prianshuprasad.myapplication.R
//import com.prianshuprasad.myapplication.Database.bookmarkDatabase.BookmarkData
//import com.prianshuprasad.myapplication.Database.bookmarkDatabase.BookmarkDataviewholder
//import com.prianshuprasad.myapplication.Database.histroryDataBase.Historyviewholder
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.ui.viewmodels.HomeViewModel
import com.prianshuprasad.myapplication.utils.Database.bookmarkDatabase.BookmarkData
import com.prianshuprasad.myapplication.utils.Database.bookmarkDatabase.BookmarkDataviewholder
import com.prianshuprasad.myapplication.utils.Database.histroryDataBase.Historyviewholder
import com.prianshuprasad.myapplication.utils.browser.Browser
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_home.*
import org.mozilla.geckoview.*
import org.mozilla.geckoview.GeckoRuntimeSettings.COLOR_SCHEME_DARK
import org.mozilla.geckoview.GeckoSession.*
import java.io.*
import java.net.URI
import java.net.URL
import java.nio.file.FileSystem
import java.nio.file.Files
import javax.net.ssl.HttpsURLConnection


class HomeFragment(browser: Browser, listener: MainActivity2) : Fragment() {


    private val listener = listener
    private lateinit var viewModel: HomeViewModel

    private lateinit var progressView: ProgressBar
    val browser= browser
   private lateinit var rcview:RecyclerView
    private lateinit var geckoView: GeckoView
    private lateinit var urlEditText: EditText
    public lateinit var toolbar: Toolbar
    public var Currurl =""
    private var ToolbarStatus= true
    private lateinit var temp:ImageView
    private lateinit var swipeRefreshLayout:SwipeRefreshLayout
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var historyviewholder: Historyviewholder
    private lateinit var multifunction:ImageView
    private lateinit var slideup: SlidingUpPanelLayout
    private lateinit var slideup_rcview:RecyclerView
    private lateinit var slideUpAdapter: SlideUpAdapter
    private lateinit var bookmarkDataviewholder: BookmarkDataviewholder
    private lateinit var bookmark_item:MenuItem
    private lateinit var toolbar2: Toolbar
    private lateinit var findText:EditText
    private var openings=0;


    override fun onCreate(savedInstanceState: Bundle?) {
        searchAdapter = SearchAdapter(this)
        historyviewholder=  Historyviewholder(App())
        slideUpAdapter = SlideUpAdapter(this)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_home, container, false)
        val view= binding.rootView

         swipeRefreshLayout = binding.findViewById(R.id.swap_refresh)
        geckoView= binding.findViewById(R.id.geckoview)
        urlEditText= binding.findViewById(R.id.location_view)
        rcview= binding.findViewById(R.id.adressbar_rcview)
        multifunction= binding.findViewById(R.id.multiFuntion_mode)
        progressView= binding.findViewById(R.id.page_progress)
        toolbar= binding.findViewById(R.id.toolbar)
        slideup= binding.findViewById(R.id.sliding_layout)
        slideup_rcview = binding.findViewById(R.id.slideUpPanel_rcview)
        slideup_rcview.layoutManager= LinearLayoutManager(requireContext())
        slideup_rcview.adapter= slideUpAdapter
        toolbar2 = binding.findViewById(R.id.toolbar2)
        findText = binding.findViewById(R.id.find_in_page)
        editUrlEdittext()

        bookmarkDataviewholder= BookmarkDataviewholder(App())
        HideSlideScreen()

        rcview.layoutManager= LinearLayoutManager(requireContext())
        rcview.adapter = searchAdapter

       if(browser.sessionList.size==0)
            addNewSession()
        else {
//            browser.currIndex= getCurrIndx()
            openWeb(browser.currIndex)

        }

        if(openings==0) {
            try {
                val url = listener.intent?.data
                val uri = URI(url.toString())
                if(url==null)
                    throw  Exception("url undefined")

                addNewSession(url = url.toString())
            } catch (e: Exception) {
            }
            openings++
        }


        toolbar.post {
            toolbar.inflateMenu(R.menu.home_screen_menu)
            bookmark_item = toolbar.menu.findItem(R.id.action_bookmark)
        }
        toolbar2.post {
            toolbar2.inflateMenu(R.menu.home_screen_finder_menu)

        }



        toolbar.setOnMenuItemClickListener {


            when (it.getItemId()) {

                R.id.action_tabs->{

                    if(isUrl(Currurl)){

                        val bitmap = MyGeckoDisplay(browser.sessionList[browser.currIndex]).capturePixels()
                        bitmap.accept {
                            it?.let {
                                browser.sessionImage[browser.currIndex] = it
                            }
                        }

                    }
                    (activity as MainActivity2).openTabPreView()
                }

                R.id.action_previous->{
                   if( (browser.sessionList[browser.currIndex].navigationDelegate as MyNavigationDelegate).canGoBack  )
                       browser.sessionList[browser.currIndex].goBack()

                }

                R.id.action_new_tab -> { addNewSession() }

                R.id.action_settings->{   (activity as MainActivity2).openSettings() }

                R.id.action_new_anon_tab->{

                    addNewSession(isAnonymous = true)

                }
                R.id.action_source_code->{
                    browser.sessionList[browser.currIndex].loadUri("view-source:$Currurl")
                }
                R.id.action_snapshot->{
                    listener.PermissionManager(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),9)
                    val bitmap = MyGeckoDisplay(browser.sessionList[browser.currIndex]).capturePixels()

                    bitmap.accept {
                        it?.let {

                        saveImage(it)


                        }
                    }
                }

                R.id.action_history->{
                    (activity as MainActivity2).openHistoryBookmark()
                }

                R.id.action_find->{
                    openFinder()
                }
                R.id.action_download->{
                    (activity as MainActivity2).OpenDownloadFragment()
                }
                R.id.action_share_page->{
                  shareUrl(Currurl)
                }

                R.id.action_bookmark-> {
                    if (!browser.bookmarkMap.containsKey(Currurl)) {
                        bookmarkDataviewholder.insertnote(BookmarkData(Currurl,
                            (browser.sessionList[browser.currIndex].contentDelegate as MyContentDelegate).title))

                        setBookmark("Remove from Bookmark")
                       }else
                    {
                        browser.bookmarkMap[Currurl]?.let { it1 ->
                            bookmarkDataviewholder.deletenote(it1)
                            setBookmark("Add to Bookmark")
                        }

                    }



                }
                R.id.action_save_offline->{
                    listener.saveWebPage(Currurl)
                }
                R.id.action_view_offline->{
                    listener.openOfflinePages()
                }
                R.id.action_save_pdf->{
                    savepdf()

                }

                else -> {}
            }

            return@setOnMenuItemClickListener true;
        }

        toolbar2.setOnMenuItemClickListener {


            when (it.getItemId()) {

                R.id.action_prev->{
                    finderResult(browser.sessionList[browser.currIndex].finder.find(findText.text.toString(),FINDER_FIND_BACKWARDS))


                }
                R.id.action_next->{
                    finderResult(browser.sessionList[browser.currIndex].finder.find(findText.text.toString(),0))
                }
                R.id.action_close->{
                    closeFinder()
                }

            }

            return@setOnMenuItemClickListener true;
        }


        temp = binding.findViewById(R.id.temp)

        if(browser.settingsData.swipeRefresh==0)
            swipeRefreshLayout.isEnabled= false

        swipeRefreshLayout.setOnRefreshListener {
            browser.sessionList[browser.currIndex].loadUri(Currurl)
            Handler().postDelayed({
                swap_refresh.isRefreshing= false
            },1000)

        }


  searchHandler()

        multifunction.setOnClickListener {
            viewSecurityDetails()
        }

        return view
    }

//    override fun onDestroyView() {
//        listener.notifyUser("view destoy")
//        super.onDestroyView()
//    }
//
//    override fun onDestroy() {
//        listener.notifyUser("destoy")
//        super.onDestroy()
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


    }


    fun swipeRefreshLayoutControl(scrollX:Int,scrolly: Int){

        if(browser.settingsData.swipeRefresh!=0)
        swipeRefreshLayout.isEnabled = scrolly<=10

    }

    fun shareUrl(url: String){
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URl")
        i.putExtra(Intent.EXTRA_TEXT, "${url}")
        startActivity(Intent.createChooser(i, "Share URL"))
    }


    @SuppressLint("ResourceAsColor")
    fun openWeb(index:Int){


        if(browser.currIndex>= browser.sessionList.size)
        {
            addNewSession()

        }else {



            progressView.visibility= View.GONE
            browser.currIndex = index

            if(!browser.sessionList[index].isOpen){

                browser.SesssionSateMap[browser.sessionList[index]]?.let {
                    try {
                        browser.sessionList[index].restoreState(it)
                    }catch (e:Exception){
                        try {
                            browser.sessionList[index].reload()
                        }catch (e:Exception){
                            listener.triggerRebirth(listener)
                        }
                    }
                }


            }


            val myNavigationDelegate:MyNavigationDelegate= browser.sessionList[index].navigationDelegate as MyNavigationDelegate
            geckoView.setSession(browser.sessionList[index])

      setSecurityMode(browser.sessionList[browser.currIndex])

            Currurl= myNavigationDelegate.getCurrentUrl()


            Handler().postDelayed({
                    setAdressbar()
                },300)

        }

    }

    fun addNewSession(isAnonymous:Boolean= false, url:String = "https://google.com"){

        val permissionDelegate = MyPermissionDelegate(listener,browser)
        val contentDelegate:MyContentDelegate = MyContentDelegate(isAnonymous= isAnonymous,listener,browser)
        val webNotificationDelegate = MyWebNotificationDelegate(listener)
        val myPromptDelegate = MyPromptDelegate(listener,browser)
        val mySelectActionDelegate = MySelectActionDelegate(listener)
        val myContentBlockingDelegate = MyContentBlockingDelegate(listener)

        var y= GeckoSessionSettings()

        if(isAnonymous) {
            val x = GeckoSessionSettings.Builder(y);
            x.usePrivateMode(true)

            y = x.build()
        }

        val geckoSession = GeckoSession(y)

        geckoSession.permissionDelegate = permissionDelegate
        geckoSession.promptDelegate= myPromptDelegate
        geckoSession.selectionActionDelegate= mySelectActionDelegate
        geckoSession.contentBlockingDelegate= myContentBlockingDelegate
        geckoSession.progressDelegate = MyProgressDetegate(listener,browser)
        geckoSession.autofillDelegate
        geckoView.autofillEnabled= true


        val runtime = GeckoRuntime.getDefault(requireContext())
        geckoSession.open(runtime)

        geckoSession.mediaSessionDelegate = MyMediaSessionDelegate(listener)

        runtime.settings.fontSizeFactor= browser.settingsData.fontSize

        runtime.settings.forceUserScalableEnabled = browser.settingsData.force_zoom==1

        if(listener.getNightMode())
        runtime.settings.preferredColorScheme = COLOR_SCHEME_DARK




        runtime.autocompleteStorageDelegate= MyStorageDelegate(listener,browser)

        if(browser.settingsData.privNotTrack==1){
        geckoSession.settings.useTrackingProtection = true
        }
        if(browser.settingsData.privHttpsOnly==1) {
            runtime.settings.setAllowInsecureConnections(GeckoRuntimeSettings.HTTPS_ONLY)
        }


        geckoSession.scrollDelegate= MyScrollDelegate(listener)
        geckoSession.contentBlockingDelegate= MyContentBlockingDelegate(listener)


        runtime.webNotificationDelegate = webNotificationDelegate
        runtime.settings.loginAutofillEnabled= true

         val navigationDelegate= MyNavigationDelegate(listener,browser)

        geckoSession.navigationDelegate = navigationDelegate

        geckoSession.contentDelegate= contentDelegate
        val loader = GeckoSession.Loader().uri(url).flags(LOAD_FLAGS_ALLOW_POPUPS)
        geckoSession.load(loader)


            browser.sessionList.add(geckoSession)
            browser.sessionRuntime.add(runtime)
            browser.naviggationDelegateList.add(navigationDelegate)
            browser.sessionImage.add(null)
            browser.currIndex = browser.sessionList.size - 1



               browser.isAnonymousList.add(isAnonymous)

        openWeb(browser.currIndex)




    }


    fun editUrlEdittext(){
        urlEditText.setOnClickListener {

          toolbarMenu(false)

        }

        urlEditText.setOnTouchListener { v, event ->
          toolbarMenu(false)
            urlEditText.setHint(browser.settingsData.defEngineName)


            return@setOnTouchListener false
        }


        urlEditText.setOnEditorActionListener(object :
            View.OnFocusChangeListener, TextView.OnEditorActionListener {

            override fun onFocusChange(view: View?, hasFocus: Boolean) {
//                Toast.makeText(listener,"$hasFocus",Toast.LENGTH_SHORT).show()
            }

            override fun onEditorAction(
                textView: TextView,
                actionId: Int,
                event: KeyEvent?,
            ): Boolean {


                urlEditText.requestFocus()
                onCommit(textView.text.toString())
                val imm: InputMethodManager =
                    activity!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager


                imm.hideSoftInputFromWindow(view?.getWindowToken(), 0);
               setAdressbar()
//                textView.hideKeyboard()
                return true
            }
        })



        findText.setOnEditorActionListener(object :
            View.OnFocusChangeListener, TextView.OnEditorActionListener {

            override fun onFocusChange(view: View?, hasFocus: Boolean) {

            }

            override fun onEditorAction(
                textView: TextView,
                actionId: Int,
                event: KeyEvent?,
            ): Boolean {

               finderResult( browser.sessionList[browser.currIndex].finder.find(findText.text.toString(),0))

//                textView.hideKeyboard()
                return true
            }
        })














    }
    fun onCommit(text: String) {
        isSearchMode(false)

        toolbarMenu(true)
        if ((text.contains(".") ||
                    text.contains(":")) &&
            !text.contains(" ")) {
            browser.sessionList[browser.currIndex].loadUri(text)
        } else {
            browser.sessionList[browser.currIndex].loadUri("${browser.settingsData.defEngineUrl}$text" )
        }

        geckoView.requestFocus()
    }


    fun updateUrl(url: String, session: GeckoSession){

        if(browser.sessionList[browser.currIndex]==session && !rcview.isVisible){
        Currurl= url
        setAdressbar()
        }
    }

    fun toolbarMenu(action:Boolean){
        if(getToolbarStatus()==action)
            return
        ToolbarStatus= action

        if(action){


            toolbar.post {
                isSearchMode(false)
                toolbar.inflateMenu(R.menu.home_screen_menu)
            }}else{
                isSearchMode(true)
                toolbar.menu.clear()
            }
        }

   fun getToolbarStatus()= ToolbarStatus

  fun setAdressbar(){

      urlEditText.setText(Currurl)
      checkBookmark()
      closeFinder()
      listener.saveTabs()

  }

    fun checkBookmark(){

        if(browser.bookmarkMap.containsKey(Currurl)){
            bookmark_item.title="Remove from Bookmark"

        }else
        {
            bookmark_item.title="Add to Bookmarks"
        }

    }

    fun setBookmark(str:String){
        bookmark_item.title=str
    }



    fun isUrl(text: String): Boolean {
        return (text.contains(".") ||
                (text.contains(":") && text.contains("//"))) &&
                !text.contains(" ")


    }

    fun isSearchMode(value:Boolean){
        if(value){

            setSearchEngine()

            progressView.visibility= View.GONE
             rcview.visibility= View.VISIBLE
            geckoView.visibility= View.GONE

            swipeRefreshLayout.isEnabled= false

        }else{


            progressView.visibility= View.GONE
            rcview.visibility= View.GONE
            geckoView.visibility= View.VISIBLE

            if(browser.settingsData.swipeRefresh!=0)
            swipeRefreshLayout.isEnabled= true


            swipeRefreshLayout.isRefreshing= false
            setSecurityMode(browser.sessionList[browser.currIndex])
            setSecurityMode(browser.sessionList[browser.currIndex])


        }
    }


    fun searchHandler(){

        urlEditText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                if(!rcview.isVisible || browser.settingsData.privAutoComplete==0)
                    return

                var query:String=""
                val lin2 = StringBuilder()
                Thread {
                    try {
                        val url = URL("https://suggestqueries.google.com/complete/search?client=firefox&q=${urlEditText.text.toString()}") //my app link change it
                        val uc: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                        val br =
                            BufferedReader(InputStreamReader(uc.getInputStream()))
                        var line: String?

                        while (br.readLine().also { line = it } != null) {

                            lin2.append(line)
                        }
                        query= urlEditText.text.toString()

                        val str = lin2.toString()
                        listener.runOnUiThread {
                            listener.homeFragment.texttoArray(str,query)
                        }
//                        texttoArray(str,query)

                        Log.d("texts", "onClick: $lin2")
                    } catch (e: IOException) {}
                }.start()

            }

        })

    }

    fun texttoArray(str:String,query:String)
    {

        var x=""

        for(i in (5+query.length) until str.length){
            if(str[i].equals(']'))
                break;
            x+=str[i];




        }

        var arrt = x.split(',')
        var arr: ArrayList<String> = ArrayList()

        for(i in 0..arrt.size-1){
            if(arrt[i].length<=2)
                continue
            if(arrt[i].contains(query))
            arr.add(arrt[i].substring(1,arrt[i].length-1)  )
        }

        searchAdapter.update(arr)

    }

    fun progessbarHandler(session: GeckoSession,state:Int){

        if(session!= browser.sessionList[browser.currIndex])
            return


        if (state in 1..99) {
            progressView.visibility = View.VISIBLE
            progressView.progress= state
            if(isUrl(Currurl)){

                val bitmap = MyGeckoDisplay(browser.sessionList[browser.currIndex]).capturePixels()

                bitmap.accept {
                    it?.let {
                        browser.sessionImage[browser.currIndex] = it
                    }
                }

            }


        } else {
            progressView.visibility = View.GONE
            if(isUrl(Currurl)){

                val bitmap = MyGeckoDisplay(browser.sessionList[browser.currIndex]).capturePixels()

                bitmap.accept {
                    it?.let {
                        browser.sessionImage[browser.currIndex] = it
                    }
                }

            }
        }

    }


    fun setSecurityMode(session: GeckoSession){
    if(session!= browser.sessionList[browser.currIndex] || rcview.isVisible)
        return

        val progressDetegate= browser.sessionList[browser.currIndex].progressDelegate as MyProgressDetegate

        if(progressDetegate.isSecure){
            multifunction.setImageResource(R.drawable.ic_baseline_enhanced_encryption_24)
        }else
        {
            multifunction.setImageResource(R.drawable.ic_baseline_no_encryption_gmailerrorred_24)

        }
    }

    fun setSearchEngine()
    {


        when(browser.settingsData.defEngineName){
            "GOOGLE"->{
                multifunction.setImageResource(R.drawable.google)

            }
            "DUCK DUCK GO"->{
                multifunction.setImageResource(R.drawable.duck)
            }
            "MSN"->{
                multifunction.setImageResource(R.drawable.ic_baseline_search_24)
            }
            "YAHOO"->{
                multifunction.setImageResource(R.drawable.yahoo)
            }
        }


    }

    fun viewSecurityDetails(){
        if(rcview.isVisible)
            return

        val li = LayoutInflater.from(listener)
        val promptsView: View = li.inflate(R.layout.prompt_security_info, null)


        val certificate= promptsView.findViewById<TextView>(R.id.certificate)
        val host= promptsView.findViewById<TextView>(R.id.host)
        val origin= promptsView.findViewById<TextView>(R.id.origin)
        val isexception= promptsView.findViewById<TextView>(R.id.isException)
        val issecure= promptsView.findViewById<TextView>(R.id.issecure)

        val secInfo = (browser.sessionList[browser.currIndex].progressDelegate as MyProgressDetegate).securityInfo

        certificate.text ="${secInfo?.certificate}"
        host.text= "Certificate host : ${secInfo?.host}"
        origin.text ="Certificate Origin : ${secInfo?.origin}"
        isexception.text="Exception : ${secInfo?.isException}"
        issecure.text= "Secured : ${secInfo?.isSecure}"

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(listener)


        alertDialogBuilder.setView(promptsView)

        alertDialogBuilder
            .setCancelable(true)

            .setNegativeButton("OK",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alertDialog: AlertDialog = alertDialogBuilder.create()


        alertDialog.show()

    }

    private var geckoResult: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>?=null
    private  var request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.LoginSelectOption>?=null
    private lateinit var slideUplayoutArray:ArrayList<String>

    fun ShowSlideScreen(arr:ArrayList<String>,geckoResul: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>,req: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.LoginSelectOption>){


        if(arr.size==0){
            HideSlideScreen()
            return
        }

        slideUpAdapter.update(arr)
        slideup_rcview.visibility= View.VISIBLE
//        slideup.bringToFront()
        slideUplayoutArray= arr

        geckoResult= geckoResul
        request= req

        Handler().postDelayed({
            slideup.panelState= SlidingUpPanelLayout.PanelState.EXPANDED
        },500)


    }
    private  var request1: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.CreditCardSelectOption>?=null
    @JvmName("ShowSlideScreen1")
    fun ShowSlideScreen(arr:ArrayList<String>, geckoResul: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>, req: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.CreditCardSelectOption>){


        if(arr.size==0){
            HideSlideScreen()
            return
        }

        slideUpAdapter.update(arr)
        slideup_rcview.visibility= View.VISIBLE
//        slideup.bringToFront()
        slideUplayoutArray= arr

        geckoResult= geckoResul
        request1= req

        Handler().postDelayed({
            slideup.panelState= SlidingUpPanelLayout.PanelState.EXPANDED
        },500)


    }

    private  var request2: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.AddressSelectOption>?=null
    @JvmName("ShowSlideScreen2")
    fun ShowSlideScreen(arr:ArrayList<String>, geckoResul: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>, req: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.AddressSelectOption>){


        if(arr.size==0){
            HideSlideScreen()
            return
        }

        slideUpAdapter.update(arr)
        slideup_rcview.visibility= View.VISIBLE
//        slideup.bringToFront()
        slideUplayoutArray= arr

        geckoResult= geckoResul
        request2= req

        Handler().postDelayed({
            slideup.panelState= SlidingUpPanelLayout.PanelState.EXPANDED
        },500)


    }




    fun onSlideUpOptionSelected(pos:Int){
        if(request!=null)
        geckoResult?.complete(request?.confirm(request!!.options[pos]))

        if(request1!=null){
            geckoResult?.complete(request1?.confirm(request1!!.options[pos]))

        }
        if(request2!=null){
            geckoResult?.complete(request2?.confirm(request2!!.options[pos]))

        }

        geckoResult=null
        request= null
        request1= null
        request2= null
        HideSlideScreen()


    }

    fun HideSlideScreen(){

        if(geckoResult!=null && request!=null) {
            geckoResult?.complete(request!!.dismiss())
            geckoResult=null
            request= null

        }

        if(geckoResult!=null && request1!=null) {
            geckoResult?.complete(request1!!.dismiss())
            geckoResult=null
            request1= null

        }

        if( geckoResult!=null && request2!=null  ) {
            geckoResult?.complete(request2!!.dismiss())
            geckoResult=null
            request2= null

        }

        slideup.panelHeight=0
            slideup.panelState= SlidingUpPanelLayout.PanelState.HIDDEN

    }


    fun AskLoginSave(
        geckoResul: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>,
        req: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.LoginSaveOption>,
    ){


        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(listener)
        alertDialogBuilder.setMessage("Save Login Details")

        alertDialogBuilder.setTitle("Alert !")
        alertDialogBuilder.setCancelable(true)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("Save",
                DialogInterface.OnClickListener{ dialog, id ->

                    geckoResul.complete(req!!.confirm(req!!.options[0]))

                })
            .setNegativeButton("Not Now",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })

        val alertDialog: AlertDialog = alertDialogBuilder.create()


        alertDialog.show()

    }

    fun savepdf(){

        listener.PermissionManager(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 9)
        try{
        if(Build.VERSION.SDK_INT<29){


        browser.sessionList[browser.currIndex].saveAsPdf().accept {
            it?.use { input ->
                val outputStream = FileOutputStream(File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)   ,
                        "${(browser.sessionList[browser.currIndex].contentDelegate as MyContentDelegate).title}.pdf"))
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024) // buffer size
                    while (true) {
                        val byteCount = input.read(buffer)
                        if (byteCount < 0) break
                        output.write(buffer, 0, byteCount)
                    }
                    output.flush()
                }
            }



        }
        }else{
                    browser.sessionList[browser.currIndex].saveAsPdf().accept {
                        try {
                        val pdfInputStream: InputStream = it!!

                        val values = ContentValues().apply {
                            put(MediaStore.Downloads.DISPLAY_NAME, "${(browser.sessionList[browser.currIndex].contentDelegate as MyContentDelegate).title}")
                            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
                            put(MediaStore.Downloads.IS_PENDING, 1)
                        }

                        val resolver = requireContext().contentResolver

                        val collection =
                            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                        val itemUri = resolver.insert(collection, values)

                        if (itemUri != null) {
                            resolver.openFileDescriptor(itemUri, "w").use { parcelFileDescriptor ->
                                ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor)
                                    .write(pdfInputStream.readBytes())
                            }
                            values.clear()
                            values.put(MediaStore.Downloads.IS_PENDING, 0)
                            resolver.update(itemUri, values, null, null)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }



        }

        Toast.makeText(listener,"Webpage saved as Pdf",Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            listener.notifyUser("Some Error Occured")
        }

    }

    private fun saveImage(bitmap: Bitmap) {
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            //These for devices running on android < Q
            //So I don't think an explanation is needed here
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            //Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            listener.notifyUser("Saved to Photos")
        }

    }


    fun closeFinder(){

        browser.sessionList[browser.currIndex].finder.clear()
        toolbar.visibility = View.VISIBLE
        toolbar2.visibility = View.GONE
    }


    fun openFinder(){
        toolbar.visibility = View.GONE
        toolbar2.visibility = View.VISIBLE
        findText.setText("")


        browser.sessionList[browser.currIndex].finder.find("",FINDER_FIND_WHOLE_WORD)

    }

    fun finderResult(x:GeckoResult<FinderResult>){
        x.accept {
            it?.let {
                if(!it.found){
                    listener.notifyUser("No match found")
                }
            }
        }
    }





















}




