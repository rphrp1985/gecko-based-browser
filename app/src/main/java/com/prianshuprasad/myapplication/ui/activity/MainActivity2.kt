package com.prianshuprasad.myapplication.ui.activity

//import com.prianshuprasad.myapplication.Database.autocompleteDatabase.AutoCompleteData
//import com.prianshuprasad.myapplication.Database.autocompleteDatabase.AutocompleteDataviewholder
//import com.prianshuprasad.myapplication.Database.bookmarkDatabase.BookmarkDataviewholder
//import com.prianshuprasad.myapplication.Database.downloadDataBase.DownloadData
//import com.prianshuprasad.myapplication.Database.downloadDataBase.DownloadDataviewholder
//import com.prianshuprasad.myapplication.Database.histroryDataBase.Historyviewholder
//import com.prianshuprasad.myapplication.Database.offlinePagesDataBase.OfflinePageviewholder
//import com.prianshuprasad.myapplication.Database.offlinePagesDataBase.offlinePage
//import com.prianshuprasad.myapplication.Database.savedTabDatabase.SavedTabviewholder
//import com.prianshuprasad.myapplication.Database.savedTabDatabase.savedTab
//import com.prianshuprasad.myapplication.Database.siteDatabase.SiteData
//import com.prianshuprasad.myapplication.Database.siteDatabase.SiteDataviewholder
import android.Manifest
import android.R.attr.label
import android.annotation.SuppressLint
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.hardware.biometrics.BiometricPrompt
import android.net.Uri
import android.os.*
import android.os.StrictMode.VmPolicy
import android.provider.OpenableColumns
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diebietse.webpage.downloader.DefaultFileSaver
import com.diebietse.webpage.downloader.WebpageDownloader
import com.prianshuprasad.myapplication.MyContentDelegate
import com.prianshuprasad.myapplication.MyNavigationDelegate
import com.prianshuprasad.myapplication.MyProgressDetegate
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.ui.adapter.ChoicePromptAdapter
import com.prianshuprasad.myapplication.ui.fragments.*
import com.prianshuprasad.myapplication.utils.Database.autocompleteDatabase.AutoCompleteData
import com.prianshuprasad.myapplication.utils.Database.autocompleteDatabase.AutocompleteDataviewholder
import com.prianshuprasad.myapplication.utils.Database.bookmarkDatabase.BookmarkDataviewholder
import com.prianshuprasad.myapplication.utils.Database.downloadDataBase.DownloadData
import com.prianshuprasad.myapplication.utils.Database.downloadDataBase.DownloadDataviewholder
import com.prianshuprasad.myapplication.utils.Database.histroryDataBase.Historyviewholder
import com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase.OfflinePageviewholder
import com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase.offlinePage
import com.prianshuprasad.myapplication.utils.Database.savedTabDatabase.SavedTabviewholder
import com.prianshuprasad.myapplication.utils.Database.savedTabDatabase.savedTab
import com.prianshuprasad.myapplication.utils.Database.siteDatabase.SiteData
import com.prianshuprasad.myapplication.utils.Database.siteDatabase.SiteDataviewholder
import com.prianshuprasad.myapplication.utils.browser.Browser
import com.prianshuprasad.myapplication.utils.services.notificationServices
import com.tonyodev.fetch2.*
import com.tonyodev.fetch2.Fetch.Impl.getInstance
import kotlinx.android.synthetic.main.item_history.*
import org.mozilla.geckoview.*
import org.mozilla.geckoview.GeckoSession.PromptDelegate.DateTimePrompt
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.absoluteValue


class MainActivity2 : AppCompatActivity() {

    lateinit var browser: Browser
    lateinit var homeFragment: HomeFragment
    private var sitePermissionMode:String=""

    val viewLoginsFragment: ViewLoginsFragment by lazy {
        ViewLoginsFragment(this,browser)
    }
    private lateinit var  prefs:SharedPreferences
    var BiometricsMode=""
    private lateinit var frameLayout: FrameLayout

    private var isNightMode=false;

    val fetchConfiguration: FetchConfiguration by lazy {
        FetchConfiguration.Builder(this)
            .setDownloadConcurrentLimit(5)
            .build()
    }

    val fetch: Fetch? by lazy {
         getInstance(fetchConfiguration)
    }



    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo


private val queueCallback:ArrayList<GeckoSession.PermissionDelegate.Callback> = ArrayList()


    var notitext=""

    lateinit var notification : NotificationCompat.Builder



    var currIndex=0;


    //biometrics
    private var cancellationSignal: CancellationSignal? = null

    // create an authenticationCallback
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            // here we need to implement two methods
            // onAuthenticationError and onAuthenticationSucceeded
            // If the fingerprint is not recognized by the app it will call
            // onAuthenticationError and show a toast
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)

                if(BiometricsMode.equals(""))
                finish()
                notifyUser("Authentication Error ")

                BiometricsMode=""
            }

            // If the fingerprint is recognized by the app then it will call
            // onAuthenticationSucceeded and show a toast that Authentication has Succeed
            // Here you can also start a new activity after that
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Authentication Succeeded")

                if(BiometricsMode.equals("View_Password"))
                {
                    viewLoginsFragment.onAuthSccess()
                    BiometricsMode=""
                    return ;
                }

               openWebView()
                // or start a new Activity

            }
        }


    override fun attachBaseContext(newBase: Context?) {

         prefs = PreferenceManager.getDefaultSharedPreferences(newBase)
        isNightMode = prefs.getBoolean("IS_Night", false)
        if(isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        super.attachBaseContext(newBase)

    }

     val settingsFragment: SettingsFragment by lazy {
         SettingsFragment(browser,siteDataviewholder,prefs)
    }

    val tabFragment: TabFragment by lazy {
        TabFragment(browser)
    }
    val historyBookmark: HistoryBookmark by lazy {
        HistoryBookmark(this)
    }
    val notificationService: notificationServices by lazy{
        notificationServices()
    }
     val siteNotificationFragment: SiteNotificationFragment by lazy{
        SiteNotificationFragment(this)
     }
    val permissionSettingsFragment: PermissionSettingsFragment by lazy {PermissionSettingsFragment() }

    val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(this)
    }
    val siteDataviewholder: SiteDataviewholder by lazy { SiteDataviewholder(application) }

     val settingsPrivacyFragment: SettingsPrivacyFragment by lazy{
         SettingsPrivacyFragment(listner = this, browser = browser, siteDataviewholder = siteDataviewholder)

     }
     val authenticationFragment: AuthenticationFragment by lazy{
         AuthenticationFragment(this)
    }
     val clearDataFragment: ClearDataFragment by lazy {
         ClearDataFragment(this,browser)
     }
     val autocompleteDataviewholder: AutocompleteDataviewholder by lazy {
         AutocompleteDataviewholder(application)
     }

     val bookmarkDataviewholder: BookmarkDataviewholder by lazy {
         BookmarkDataviewholder(application)
     }
    val  savedTabviewholder: SavedTabviewholder by lazy {
        SavedTabviewholder(application)
    }
    val  downloadFragment: DownloadFragment by lazy {
        DownloadFragment(this,browser,downloadDataviewholder)
    }
    val downloadDataviewholder: DownloadDataviewholder by lazy {
        DownloadDataviewholder(application)
    }
    val offlinePagesFragment: OfflinePagesFragment by lazy {
        OfflinePagesFragment(this,offlinePageviewholder)
    }
     val offlinePageviewholder: OfflinePageviewholder by lazy{
         OfflinePageviewholder(application)
    }
    val accessbilityFragment: AccessbilityFragment by lazy {
        AccessbilityFragment(this,siteDataviewholder)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportActionBar?.hide()

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        browser = Browser()


        homeFragment= HomeFragment(browser,this)


        frameLayout= findViewById(R.id.main_activity_fragment)

        Historyviewholder(application).allnotes

        siteDataviewholder.allnotes.observeForever {

            thread {
                var isSettingsFound = false
                browser.arr.clear()
//                 Toast.makeText(application,"Update ",Toast.LENGTH_SHORT).show()
                for (x in it) {
                    if (x.coreAdress == "Settings") {
                        browser.settingsData = x
                        isSettingsFound = true

                    } else {

                        browser.arr.add(x)
                    }


                }
                if (isSettingsFound) {

                  runOnUiThread {
                      recoverTabs()
                  }
                    return@thread

                }

                siteDataviewholder.insertnote(SiteData(coreAdress = "Settings",
                    permNoti = 1,
                    permAutoDownload = 1,
                    permCamera = 1,
                    permClipboard = 1,
                    permCokies = 1,
                    permDesktop = 0,
                    permJavaScript = 1,
                    permLocation = 1,
                    permMedia = 1,
                    permMicrophone = 1,
                    permMotionS = 1,
                    permPopup = 1,
                    permProtectdContent = 1,
                    permSound = 1,
                    permStorage = 1,
                    permUSB = 1,
                    swipeRefresh = 1,
                    autoSavePassword = 1
                ))
            }
                 return@observeForever
        }

        Handler().postDelayed({
            if(browser.settingsData.privFingerprint==1){
                openAuth()
            }else
                openWebView()
        },500)


        autocompleteDataviewholder.allnotes.observeForever {
            thread {
                browser.autocompleteList = it as ArrayList<AutoCompleteData>
            }
        }

       bookmarkDataviewholder.allnotes.observeForever {
          thread {
           browser.bookmarkMap.clear()
           for(data in it){
               browser.bookmarkMap.put(data.coreAdress,data)
           }
       }}
        downloadDataviewholder.allnotes

//        launchAppChooser()
    }


    fun getDownloads(){
        fetch?.getDownloads {

            browser.downloadArrayList = it as ArrayList<Download>
            downloadFragment.adapter?.update(browser.downloadArrayList)
        }
    }


    fun getNightMode()= isNightMode

    fun addDeafultWebsite(base_url:String){
        val sitedata= SiteData(coreAdress = base_url)
        siteDataviewholder.insertnote(sitedata)

    }

    private var isNew=true;

    fun recoverTabs(){
            Handler().postDelayed({

                    savedTabviewholder.allnotes.observeForever {
                        thread {
                        if (!isNew || browser.settingsData.saveTabs == 0)
                            return@thread

                        isNew = false
                        if (it == null) return@thread

                        for (tabs in it) {
                            runOnUiThread {

                                homeFragment.addNewSession(url = tabs.url)

                            }
                    }
                        }

                        return@observeForever
                }
            }, 1000)
        }


//    private fun launchAppChooser() {
////        Log.d(TAG, "launchAppChooser()")
//        val intent = Intent(Intent.ACTION_MAIN)
//        intent.addCategory(Intent.CATEGORY_APP_BROWSER)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//    }


    fun openSettings(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            settingsFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed

        fragmentTransaction.addToBackStack(settingsFragment.toString()) //add fragment to stack


    }

    fun openOfflinePages(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            offlinePagesFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fragment to stack

    }

    fun openSettingsSiteNotification(){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            siteNotificationFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fragment to stack

    }

    fun openViewLogins(mode:String){
        viewLoginsFragment.Mode=mode
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            viewLoginsFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fra
    }

    fun openAccesbility(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            accessbilityFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fragment to stack

    }

    fun openClearDataFragment(){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            clearDataFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fragment to stack

    }

    fun openSettingsPrivacyFragment(){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            settingsPrivacyFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fragment to stack
    }

    fun OpenDownloadFragment(){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            downloadFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(siteNotificationFragment.toString()) //add fragment to stack
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO){

        }else{

        }
    }


    fun openWebView(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity_fragment,
                homeFragment, "Home_Fragment")
                .commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed
    }

    fun openTabPreView(){

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            tabFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(tabFragment.toString()) //add fragment to stack

    }

    fun openHistoryBookmark(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            historyBookmark).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed


        fragmentTransaction.addToBackStack(historyBookmark.toString())
    }

    fun openPermissionSettings(){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in,
            R.anim.slide_out,
            R.anim.slide_in,
            R.anim.slide_out
        ).replace(R.id.main_activity_fragment,
            permissionSettingsFragment).addToBackStack(null).commit()

    }



    override fun onBackPressed() {

        if(homeFragment.isVisible && !homeFragment.getToolbarStatus()){
            homeFragment.toolbarMenu(true)
            homeFragment.setAdressbar()
            return
        }


        if (browser.currIndex!=-1 && browser.currIndex<browser.naviggationDelegateList.size && browser.naviggationDelegateList[browser.currIndex].canGoBack && homeFragment.isVisible  ) {
            browser.sessionList[browser.currIndex].goBack()
        } else {

            super.onBackPressed()
        }
    }

    fun pushNotification(webnotification: WebNotification){

//        Toast.makeText(this,"Notifucation   ${webnotification.imageUrl}",Toast.LENGTH_SHORT).show()
        createNotificationChannel(webnotification.tag)

        val intent = Intent(this, MainActivity2::class.java).apply{
            flags =
            Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        }

        intent.putExtra("notification_url", webnotification.source)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        notification =
            NotificationCompat.Builder(this, webnotification.tag)
                .setSmallIcon(R.drawable.ic_baseline_web_24)
                .setContentTitle(webnotification.title)
                .setContentText(webnotification.textDirection)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOngoing(webnotification.requireInteraction)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)



        notificationManager.notify((0..1000000).random(), notification.build())


    }

    fun createNotificationChannel(channelId:String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelId
            val descriptionText = ""
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)


        try {

            val url= intent!!.getStringExtra("notification_url")

                       if (url != null) {
                homeFragment.addNewSession(url=url)

            }

        } catch (e: Exception) { }


    }

    override fun onResume() {


        if(browser.settingsData.privFingerprint==1)
        {
            openAuth()
        }

        Thread {
            for (session in browser.sessionList) {
                if (!session.isOpen) {
                    browser.SesssionSateMap[session]?.let {
                        try {
                            session.restoreState(it)
                        } catch (e: Exception) {
                            try {
                                session.reload()
                            } catch (e: Exception) {
                                triggerRebirth(this)
                            }
                        }
                    }
                }
            }
        }

        super.onResume()

    }



     fun PermissionManager(
         permissions: Array<String>,
         requestCode: Int,
         callback: GeckoSession.PermissionDelegate.Callback,
     ) {

        for (permission in permissions) {

            if (ContextCompat.checkSelfPermission(this@MainActivity2,
                    permission) == PackageManager.PERMISSION_DENIED
            ) {

                queueCallback.add(callback)
                // Requesting the permission
                ActivityCompat.requestPermissions(this@MainActivity2,
                    arrayOf(permission),
                    requestCode)
            }else
                callback.grant()
        }

     }

    fun PermissionManager(
        permissions: Array<String>,
        requestCode: Int,

    ) {

        for (permission in permissions) {

            if (ContextCompat.checkSelfPermission(this@MainActivity2,
                    permission) == PackageManager.PERMISSION_DENIED
            ) {


                ActivityCompat.requestPermissions(this@MainActivity2,
                    arrayOf(permission),
                    requestCode)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            Toast.makeText(this,"Permisiion   -- ",Toast.LENGTH_SHORT).show()
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && queueCallback.isNotEmpty()) {

                queueCallback[0].grant()
                queueCallback.removeAt(0)


                         } else {

                queueCallback[0].reject()
                queueCallback.removeAt(0)
            }
        }


        if(requestCode==9){

        }


    }



    fun sitePermissionFragmentModeSet(str:String){
        sitePermissionMode= str

    }

    fun sitePermissionFragmentModeGet(): String {
        return sitePermissionMode

    }

    fun updateUrl(url:String,session: GeckoSession)
    {
        homeFragment.updateUrl(url,session)
    }

    fun openAuth(){


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity_fragment,
            authenticationFragment).commit()// content_fragment is id of FrameLayout(XML file) where fragment will be displayed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            BiometricHandler()
        }else
        {
            notifyUser("Something Wrong Happend!")
            finish()
        }

    }


    fun BiometricHandler(){

        checkBiometricSupport()

        val biometricPrompt = BiometricPrompt.Builder(this)
            .setTitle("Biometric Authentication")
            .setDescription("Sign in to open browser")
            .setNegativeButton("Cancel", this.mainExecutor, DialogInterface.OnClickListener { dialog, which ->
                notifyUser("Authentication Cancelled")
               if(BiometricsMode.equals(""))
                finish()

                BiometricsMode=""
            }).build()

        // start the authenticationCallback in mainExecutor
        biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("Authentication was Cancelled by the user")

            if(BiometricsMode.equals(""))
            finish()

            BiometricsMode=""
        }
        return cancellationSignal as CancellationSignal
    }

    // it checks whether the app the app has fingerprint permission
//    @RequiresApi(Build.VERSION_CODES.M)
     fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            notifyUser("Biometrics authentication has not been enabled in settings")
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Biometrics Authentication Permission is not enabled")
            return false
        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }


     fun notifyUser(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    fun ShowUrLContextMenu(element: GeckoSession.ContentDelegate.ContextElement){
        val li = LayoutInflater.from(this)
        val promptsView: View = li.inflate(R.layout.item_url_context_menu, null)

        val heading = promptsView.findViewById<TextView>(R.id.heading)
        val new_tab = promptsView.findViewById<TextView>(R.id.new_tab)
        val new_anon_tab = promptsView.findViewById<TextView>(R.id.new_anon_tab)
        val copy_link = promptsView.findViewById<TextView>(R.id.copy_link)
        val download_link = promptsView.findViewById<TextView>(R.id.download_link)
        val share_link = promptsView.findViewById<TextView>(R.id.share_link)
        heading.text= element.linkUri
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(promptsView)
        alertDialogBuilder
            .setCancelable(true)

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
        new_tab.setOnClickListener {
            element.linkUri?.let { it1 -> homeFragment.addNewSession(url = it1) }
            alertDialog.cancel()
        }
        new_anon_tab.setOnClickListener {
            element.linkUri?.let { it1 -> homeFragment.addNewSession(url = it1, isAnonymous = true) }
            alertDialog.cancel()
        }
        copy_link.setOnClickListener {

            val clipboard: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label.toString(), element.linkUri)
            clipboard.setPrimaryClip(clip)
            notifyUser("Link copied to clipboard")

            alertDialog.cancel()

        }

        share_link.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URl")
            i.putExtra(Intent.EXTRA_TEXT, "${element.linkUri}")
            startActivity(Intent.createChooser(i, "Share URL"))

            alertDialog.cancel()
        }


    }

    @SuppressLint("Range")
    fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.getScheme().equals("content")) {
            val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.getPath()
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result
    }


fun getName(url:String):String{
    val fileName: String = URL(url).getFile()
    return fileName.substring(fileName.lastIndexOf('/') + 1)

}

    fun beginDownload(response: WebResponse) {

        PermissionManager(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 9)
        PermissionManager(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 9)



        val url = response.uri
        val file = "file:///sdcard/Download/${getName(url)}"

        val request = Request(url, file)
        request.priority = Priority.HIGH
        request.networkType = NetworkType.ALL

        request.addHeader("clientKey", "${response.headers["clientKey"]}")



        fetch!!.enqueue(request,
            { updatedRequest: Request? ->
                notifyUser("Downloading")

                downloadDataviewholder.insertnote(DownloadData(title = getName(url),
                    id = updatedRequest!!.id,
                    url = url, fileurl = file))
//               browser.downloadList.add(DownloadData(id=updatedRequest!!.id))


            }
        ) { error: Error? ->
            notifyUser("Downloading Failed ${error}")
        }

    }


    fun saveWebPage(url: String){
        PermissionManager(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 9)
        PermissionManager(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 9)

        var downloadDir = File("file:///sdcard/", "download")
         downloadDir = File(application.filesDir, "download")
        val pageId = url.hashCode().absoluteValue.toString()
        thread {
            try {

                WebpageDownloader().download(url, DefaultFileSaver(File(downloadDir,pageId)))
                runOnUiThread {
                    notifyUser("web page saved")
                    offlinePageviewholder.insertnote(offlinePage(  weburl = homeFragment.Currurl  ,name=(browser.sessionList[browser.currIndex].contentDelegate as MyContentDelegate).title  ,url="${File(downloadDir, pageId)}/index.html") )
//                    homeFragment.addNewSession(url= "${File(downloadDir, pageId)}/index.html")
                }
            } catch (e: Exception) {
            runOnUiThread {
                notifyUser("Web Page not saved")
            }
            }
        }

    }




    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

//        Toast.makeText(this,"${v.id}",Toast.LENGTH_SHORT).show()
        menu.add(0, v.id, 0, "Delete")

    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
       downloadFragment.delete()
        return true
    }


    fun openFile( index:Int) {

        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val mime = contentResolver.getType(browser.downloadArrayList[index].fileUri)
//        Toast.makeText(this,"$mime",Toast.LENGTH_SHORT).show()

        val url = browser.downloadArrayList[index].fileUri.toString()
        val uri = browser.downloadArrayList[index].fileUri

        if (url.toString().contains(".doc") || url.toString().contains(".docx")) {

            intent.setDataAndType(uri, "application/msword")
        } else if (url.toString().contains(".pdf")) {

            intent.setDataAndType(uri, "application/pdf")
        } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {

            intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
        } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {

            intent.setDataAndType(uri, "application/vnd.ms-excel")
        } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {

            intent.setDataAndType(uri, "application/x-wav")
        } else if (url.toString().contains(".rtf")) {

            intent.setDataAndType(uri, "application/rtf")
        } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {

            intent.setDataAndType(uri, "audio/x-wav")
        } else if (url.toString().contains(".gif")) {

            intent.setDataAndType(uri, "image/gif")
        } else if (url.toString().contains(".jpg") || url.toString()
                .contains(".jpeg") || url.toString().contains(".png")
        ) {

            intent.setDataAndType(uri, "image/jpeg")
        } else if (url.toString().contains(".txt")) {
            // Text file
            intent.setDataAndType(uri, "text/plain")
        } else if (url.toString().contains(".3gp") || url.toString()
                .contains(".mpg") || url.toString().contains(".mpeg") || url.toString()
                .contains(".mpe") || url.toString().contains(".mp4") || url.toString()
                .contains(".avi")
        ) {
            // Video files
            intent.setDataAndType(uri, "video/*")
        } else {

            intent.setDataAndType(uri, "*/*")
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)



    }

    fun saveTabs(){
        thread {
            savedTabviewholder.deleteAll()

            for (session in browser.sessionList) {
                runOnUiThread {
                savedTabviewholder.insertnote(savedTab((session.navigationDelegate as MyNavigationDelegate).url))
            }}
        }
    }

    override fun onPause() {

     saveTabs()

        super.onPause()

    }


    fun triggerRebirth(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        outState.putParcelable("browser",browser)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
//            browser= savedInstanceState?.getParcelable<Browser>("browser")!!

    }


    fun mNotification(mediaSessiono: MediaSession) {

        createNotificationChannel("media")
         val intent = Intent()
          intent.setAction("pause")

        val mediaStyle: androidx.media.app.NotificationCompat.MediaStyle =
            androidx.media.app.NotificationCompat.MediaStyle()


        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(
            this, 0,intent,0)
        val filter = IntentFilter("pause")
//        registerReceiver( MyBrodCastReciver(this, mediaSessiono, this),filter)

        notification =
            NotificationCompat.Builder(this, "media")
                .setSmallIcon(R.drawable.ic_baseline_web_24)
                .setStyle(mediaStyle)
                .setOngoing(true)
                .setContentTitle("Media Playing")
                .setContentText("Video playing")


       val id =(0..1000000).random()
        notificationManager.notify(id, notification.build())

        notificationManager.cancel(id)

    }

    private  var promptFile:GeckoSession.PromptDelegate.FilePrompt?= null
    private var responceFile:GeckoResult<GeckoSession.PromptDelegate.PromptResponse>? = null

     fun selectFile(
         prompt: GeckoSession.PromptDelegate.FilePrompt,
         responcei: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>?,
     ) {
         PermissionManager(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 9)
         PermissionManager(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 9)


         responceFile= responcei
        val intent = Intent()
         promptFile = prompt
            intent.action = Intent.ACTION_GET_CONTENT

         intent.type="*/*"

         startActivityForResult(intent, 10)
    }

    var temp:String=""

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

         if( requestCode==10){

             if(promptFile!=null && data!=null && responceFile!=null) {

                 temp= data.data?.let { fileFromContentUri(this, it) }?.toURI().toString()

                data?.data?.let {

                     responceFile!!.complete( promptFile!!.confirm(this, temp.toUri()))

                 }


             }

         }

         super.onActivityResult(requestCode, resultCode, data)

     }

///------------   On file prompt Helper---------------------------------------------
    fun fileFromContentUri(context: Context, contentUri: Uri): File {
        // Preparing Temp file name
        val fileExtension = getFileExtension(context, contentUri)
        val fileName = "temp_file" + if (fileExtension != null) ".$fileExtension" else ""

        // Creating Temp file
        val tempFile = File(context.cacheDir, fileName)
        tempFile.createNewFile()

        try {
            val oStream = FileOutputStream(tempFile)
            val inputStream = context.contentResolver.openInputStream(contentUri)

            inputStream?.let {
                copy(inputStream, oStream)
            }

            oStream.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tempFile
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        val fileType: String? = context.contentResolver.getType(uri)
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(fileType)
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }


//------------------------------------------------------------------------------

    fun choicePrompt(
        prompt: GeckoSession.PromptDelegate.ChoicePrompt,
        response: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>,
    ) {

        val li = LayoutInflater.from(this)
        val promptsView: View = li.inflate(R.layout.layout_choice_prompt, null)


        val rcview= promptsView.findViewById<RecyclerView>(R.id.rcView_choicePrompt)

        val secInfo = (browser.sessionList[browser.currIndex].progressDelegate as MyProgressDetegate).securityInfo

        rcview.layoutManager= LinearLayoutManager(this)
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(promptsView)

        alertDialogBuilder
            .setCancelable(true)

            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel()
                    response.complete(prompt.dismiss())
                })

        val alertDialog: AlertDialog = alertDialogBuilder.create()

        alertDialog.show()

        val adapter= ChoicePromptAdapter(this, prompt,response,alertDialog)

        rcview.adapter= adapter

        adapter.update(prompt.choices)

    }




    fun DateSelect(
        prompt: GeckoSession.PromptDelegate.DateTimePrompt,
        response: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>,
    ) {
        val mCalendar = Calendar.getInstance()
        val year = mCalendar[Calendar.YEAR]
        val month = mCalendar[Calendar.MONTH]
        val dayOfMonth = mCalendar[Calendar.DAY_OF_MONTH]
        val hour = mCalendar.get(Calendar.HOUR_OF_DAY)
        val minute = mCalendar.get(Calendar.MINUTE)
        var mDatePickerDialog: DatePickerDialog

        var mode=""
        var completed=0;
        val format: String
         if (prompt.type === DateTimePrompt.Type.DATE) {
           format= "yyyy-MM-dd"
            mode="date"
        } else if (prompt.type === DateTimePrompt.Type.MONTH) {
           format= "yyyy-MM"
             mode="date"
        } else if (prompt.type === DateTimePrompt.Type.WEEK) {
            format="yyyy-'W'ww"
             mode= "date"

        } else if (prompt.type === DateTimePrompt.Type.TIME) {
          format=  "HH:mm"
             mode= "time"
        } else if (prompt.type === DateTimePrompt.Type.DATETIME_LOCAL) {
           format= "yyyy-MM-dd'T'HH:mm"
             mode="date&time"
        } else {
            throw UnsupportedOperationException()
        }
        val formatter =  SimpleDateFormat(format, Locale.ROOT)


        if(prompt.type== DateTimePrompt.Type.TIME || prompt.type==DateTimePrompt.Type.DATETIME_LOCAL) {
            val timePickerDialog = TimePickerDialog(
                this,
                { view, hourOfDay, minute ->

                    val y= mCalendar.get(Calendar.YEAR)
                    val m= mCalendar.get(Calendar.MONTH)
                    val d= mCalendar.get(Calendar.DAY_OF_MONTH)


                    mCalendar.set(y,m,d,hourOfDay,minute);

                    if(mode.equals("date&time")) {
                        completed++;
                        if(completed==2)
                            completeDateTimePrompt(prompt,response,formatter,mCalendar)
                    }else
                    {
                        completeDateTimePrompt(prompt,response,formatter,mCalendar)
                    }
                },
                hour,
                minute,
                false
            )
            timePickerDialog.show()
        }



            mDatePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->


                mCalendar.set(year, month, dayOfMonth);
                if(mode.equals("date&time")) {
                    completed++;
                    if(completed==2)
                        completeDateTimePrompt(prompt,response,formatter,mCalendar)
                }else
                {
                    completeDateTimePrompt(prompt,response,formatter,mCalendar)
                }


            },year,month,dayOfMonth)

            mDatePickerDialog.show()

        mDatePickerDialog.setOnCancelListener { prompt.dismiss() }

//        mDatePickerDialogFragment.show(supportFragmentManager, "DATE PICK")
    }

    fun completeDateTimePrompt(
        prompt: DateTimePrompt,
        response: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>,
        formatter: SimpleDateFormat,
        mCalendar: Calendar,
    ) {
        response.complete(prompt.confirm(formatter.format(mCalendar.time)))
    }





    fun ShowOnselectContextMenu(
        session: GeckoSession,
        selection: GeckoSession.SelectionActionDelegate.Selection,
    ) {


        val li = LayoutInflater.from(this)
        val promptsView: View = li.inflate(R.layout.item_onselection_menu, null)
        val heading = promptsView.findViewById<TextView>(R.id.heading)
        val copy = promptsView.findViewById<TextView>(R.id.copy)
        val cut = promptsView.findViewById<TextView>(R.id.cut)
        val paste = promptsView.findViewById<TextView>(R.id.paste)
        val delete = promptsView.findViewById<TextView>(R.id.delete)
        val select_all = promptsView.findViewById<TextView>(R.id.Select_all)
        val paste_plain = promptsView.findViewById<TextView>(R.id.paste_plain)
        val unselect_all = promptsView.findViewById<TextView>(R.id.unselect)

        heading.text= selection.text
        var visibility_counter=0
        for(str in selection.availableActions){
            when(str.toString()){
                "org.mozilla.geckoview.CUT"-> {cut.visibility= View.VISIBLE
                 visibility_counter++;
                }
                "org.mozilla.geckoview.COPY"->{ copy.visibility= View.VISIBLE
                visibility_counter++}
                "org.mozilla.geckoview.DELETE"-> {delete.visibility= View.VISIBLE
                visibility_counter++}
                "org.mozilla.geckoview.PASTE"-> {paste.visibility= View.VISIBLE
                visibility_counter}
                "org.mozilla.geckoview.SELECT_ALL"-> {select_all.visibility= View.VISIBLE
                visibility_counter}
                "org.mozilla.geckoview.UNSELECT"-> {unselect_all.visibility=  View.VISIBLE
                visibility_counter++}
                "org.mozilla.geckoview.PASTE_AS_PLAIN_TEXT"-> {paste_plain.visibility= View.VISIBLE
                visibility_counter++}

            }
        }



        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(promptsView)


        alertDialogBuilder
            .setCancelable(true)

        val alertDialog: AlertDialog = alertDialogBuilder.create()


        if(visibility_counter!=0)
        alertDialog.show()



    cut.setOnClickListener {
        selection.cut()
        alertDialog.cancel()
    }

        copy.setOnClickListener {  selection.copy()
        alertDialog.cancel()

        }

        paste.setOnClickListener { selection.paste()
        alertDialog.cancel()}

        delete.setOnClickListener {  selection.delete()
        alertDialog.cancel()}

        select_all.setOnClickListener {
            selection.selectAll()
            alertDialog.cancel()
        }

        unselect_all.setOnClickListener {
            selection.unselect()
            alertDialog.cancel()
        }


        paste_plain.setOnClickListener {
            selection.pasteAsPlainText()
            alertDialog.cancel()
        }




    }










}


