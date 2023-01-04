package com.prianshuprasad.myapplication

//import com.prianshuprasad.myapplication.histroryDataBase.BrowsingHistory
//import com.prianshuprasad.myapplication.histroryDataBase.Historyviewholder

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import androidx.annotation.AnyThread
import androidx.annotation.UiThread
import androidx.lifecycle.MutableLiveData
import com.prianshuprasad.myapplication.MediaNotification.MediaNotification
import com.prianshuprasad.myapplication.autocompleteDatabase.AutoCompleteData
import com.prianshuprasad.myapplication.autocompleteDatabase.AutocompleteDataviewholder
import com.prianshuprasad.myapplication.histroryDataBase.BrowsingHistory
import com.prianshuprasad.myapplication.histroryDataBase.Historyviewholder
import com.prianshuprasad.myapplication.siteDatabase.SiteData
import com.prianshuprasad.myapplication.siteDatabase.SiteDataviewholder
import org.mozilla.gecko.annotation.WrapForJNI
import org.mozilla.geckoview.*
import org.mozilla.geckoview.ContentBlocking.*
import org.mozilla.geckoview.GeckoSession.HistoryDelegate.HistoryList
import org.mozilla.geckoview.GeckoSession.PermissionDelegate.*
import org.mozilla.geckoview.GeckoSession.PromptDelegate.*
import org.mozilla.geckoview.GeckoSession.SelectionActionDelegate.ClipboardPermission
import org.mozilla.geckoview.GeckoSession.VisitFlags
import java.net.URL
import java.util.*


class Delegates {
}


var globalUrl:String=""
public class MyNavigationDelegate(listner: MainActivity2,browser: Browser) : GeckoSession.NavigationDelegate {
    var canGoBack = false
    var url=""
    val browser = browser
    val listner= listner
//    val application= App()
    override fun onCanGoBack(session: GeckoSession, canGoBack: Boolean) {
        this.canGoBack = canGoBack
    }

    override fun onLocationChange(
        session: GeckoSession,
        urli: String?,
        perms: MutableList<ContentPermission>,
    ) {

        val base_url = urli?.let { getBaseUrl(it) }
        var enableScript=true
        var isBaseurlfound=false;
        var isDesktopMode=false

        if(browser.sessionList[browser.currIndex]==session)
        listner.homeFragment.HideSlideScreen()

        urli?.let { listner.updateUrl(it,session) }

        if (urli != null) {
            globalUrl= urli
            url= urli
        }

        if(!session.settings.usePrivateMode &&isUrl(globalUrl) &&  browser.settingsData.privHistory==1){
            Historyviewholder(App()).insertnote(BrowsingHistory(globalUrl, globalUrl,System.currentTimeMillis()))

        }

        if(browser.settingsData.permJavaScript!=1){

            session.settings.allowJavascript=false
            enableScript= false
        }
        if(browser.settingsData.permDesktop==1)
        {
            if(session.settings.userAgentMode!=1){
                session.settings.userAgentMode=1
                reload(session)
            }
            isDesktopMode=true
        }

        for(site in browser.arr){

            if(base_url==site.coreAdress)
                isBaseurlfound= true
            if(site.coreAdress==base_url && site.permJavaScript!=1){
                session.settings.allowJavascript=false
                enableScript= false
            }
            if(base_url==site.coreAdress && site.permDesktop==1){
                if(session.settings.userAgentMode!=1){
                    session.settings.userAgentMode=1
                    reload(session)
                }
                isDesktopMode=true
            }

        }
        if(enableScript)
            session.settings.allowJavascript=true

        if(!isBaseurlfound && base_url!="")
            base_url?.let { listner.addDeafultWebsite(it) }

         if(!isDesktopMode) {
             if(session.settings.userAgentMode!=0){
                 session.settings.userAgentMode=0
                 reload(session)
             }
         }




    }

    override fun onLoadError(
        session: GeckoSession,
        uri: String?,
        error: WebRequestError,
    ): GeckoResult<String>? {

         val s:String? =""
        return GeckoResult.fromValue(s)
        return super.onLoadError(session, uri, error)
    }
    fun reload(session: GeckoSession){
//        val url = (session.navigationDelegate as MyNavigationDelegate).getCurrentUrl()
        session.reload()
    }
    fun getBaseUrl(urlstr :String):String{
        if(!isUrl(urlstr))
            return "";
        var baseUrl:String=""
        try {
            val url = URL(urlstr)
             baseUrl= url.getProtocol().toString() + "://" + url.getHost()
        }
        catch (e:Exception){
            baseUrl= urlstr
        }

        return baseUrl;
    }
    fun isUrl(text: String): Boolean {
        return (text.contains(".") ||
                (text.contains(":") && text.contains("//"))) &&
                !text.contains(" ")


    }




    fun getCurrentUrl()= url;

}

class MyHistoryDelegate: GeckoSession.HistoryDelegate{

    inner class MyHistoryItem: GeckoSession.HistoryDelegate.HistoryItem{

        @AnyThread
        override fun getTitle(): String {
            return ""

        }

        @AnyThread
        override fun getUri(): String {
            throw UnsupportedOperationException("HistoryItem.getUri() called on invalid object.")
            return " "
        }


    }

    public inner class MyHistoryList: List<MyHistoryItem>{
        override val size: Int
            get() = TODO("Not yet implemented")

        override fun contains(element: MyHistoryItem): Boolean {
            TODO("Not yet implemented")
        }

        override fun containsAll(elements: Collection<MyHistoryItem>): Boolean {
            TODO("Not yet implemented")
        }

        override fun get(index: Int): MyHistoryItem {
            TODO("Not yet implemented")
        }

        override fun indexOf(element: MyHistoryItem): Int {
            TODO("Not yet implemented")
        }

        override fun isEmpty(): Boolean {
            TODO("Not yet implemented")
        }

        override fun iterator(): Iterator<MyHistoryItem> {
            TODO("Not yet implemented")
        }

        override fun lastIndexOf(element: MyHistoryItem): Int {
            TODO("Not yet implemented")
        }

        override fun listIterator(): ListIterator<MyHistoryItem> {
            TODO("Not yet implemented")
        }

        override fun listIterator(index: Int): ListIterator<MyHistoryItem> {
            TODO("Not yet implemented")
        }

        override fun subList(fromIndex: Int, toIndex: Int): List<MyHistoryItem> {
            TODO("Not yet implemented")
        }


    }

    @UiThread
    override fun onVisited(
        session: GeckoSession,
        url: String,
        lastVisitedURL: String?,
        @VisitFlags flags: Int,
    ): GeckoResult<Boolean?>? {
        return null
    }

    @UiThread
    override fun getVisited(
        session: GeckoSession, urls: Array<String?>,
    ): GeckoResult<BooleanArray?>? {
        return null
    }


    override fun onHistoryStateChange(
        session: GeckoSession, historyList: HistoryList,
    ) {

    }





}


class MyContentDelegate(var isAnonymous:Boolean=false, listner: MainActivity2,browser: Browser):GeckoSession.ContentDelegate{
    var title:String=""
    var url=""
    val application= App()
    val listner= listner
    val browser= browser



    override fun onTitleChange(session: GeckoSession, title: String?) {
          this.title= title!!

        if(!isAnonymous && browser.settingsData.privHistory==1)
        Historyviewholder(application).insertnote(BrowsingHistory(title, globalUrl,System.currentTimeMillis()))

    }

     override fun onPreviewImage(
        session: GeckoSession, previewImageUrl: String,
    ) {

    }

    override fun onKill(session: GeckoSession) {

//        browser.SesssionSateMap[session]?.let {
//            session.restoreState(it)
//        }


        super.onKill(session)
    }

    override fun onCloseRequest(session: GeckoSession) {



        super.onCloseRequest(session)
    }

    override fun onCrash(session: GeckoSession) {

//        browser.SesssionSateMap[session]?.let {
//            session.restoreState(it)
//        }

        super.onCrash(session)
    }

    override fun onContextMenu(
        session: GeckoSession,
        screenX: Int,
        screenY: Int,
        element: GeckoSession.ContentDelegate.ContextElement,
    ) {

        listner.ShowUrLContextMenu(element)
        super.onContextMenu(session, screenX, screenY, element)
    }

    override fun onExternalResponse(session: GeckoSession, response: WebResponse) {
//        Toast.makeText(listner,"download = ${response.uri}",Toast.LENGTH_SHORT).show()
 listner.beginDownload(response)
        super.onExternalResponse(session, response)
    }




}

class App : Application() {
    var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}

public class MyWebNotificationDelegate(listner: MainActivity2): WebNotificationDelegate{

val listner = listner

    @AnyThread
    @WrapForJNI
    override fun onShowNotification(notification: WebNotification) {

        listner.pushNotification(notification)
    }


    @AnyThread
    @WrapForJNI
    override fun onCloseNotification(notification: WebNotification) {



    }



}



public  class  MyPermissionDelegate(listner:MainActivity2,browser: Browser): GeckoSession.PermissionDelegate{


    val application = App()
    val listner= listner
    val browser= browser

    val siteDataviewholder=  SiteDataviewholder(Application())


    override fun onAndroidPermissionsRequest(
        session: GeckoSession,
        permissions: Array<out String>?,
        callback: GeckoSession.PermissionDelegate.Callback,
    ) {
          listner.PermissionManager(permissions = permissions as Array<String> ,10,callback)


    }

    override fun onContentPermissionRequest(
        session: GeckoSession,
        perm: GeckoSession.PermissionDelegate.ContentPermission,
    ): GeckoResult<Int>? {


            //to Be managed later

        if(perm.permission== PERMISSION_XR || perm.permission==PERMISSION_MEDIA_KEY_SYSTEM_ACCESS )
            return GeckoResult.fromValue(ContentPermission.VALUE_ALLOW)

        when(perm.permission){
            PERMISSION_DESKTOP_NOTIFICATION->{
                if(browser.settingsData.permNoti!=1)
                    return GeckoResult.fromValue(ContentPermission.VALUE_DENY)

            }
            PERMISSION_GEOLOCATION->{
                if(browser.settingsData.permLocation!=1)
                    return GeckoResult.fromValue(ContentPermission.VALUE_DENY)

            }
            PERMISSION_AUTOPLAY_INAUDIBLE,
            PERMISSION_AUTOPLAY_AUDIBLE,
            -> {
                if (browser.settingsData.permMedia != 1)
                    return GeckoResult.fromValue(ContentPermission.VALUE_DENY)

            }
            PERMISSION_PERSISTENT_STORAGE->{
                if(browser.settingsData.permStorage!=1)
                    return GeckoResult.fromValue(ContentPermission.VALUE_DENY)

            }
            PERMISSION_STORAGE_ACCESS->{
                if(browser.settingsData.permCokies!=1)
                    return GeckoResult.fromValue(ContentPermission.VALUE_DENY)

            }

        }



        val navigationDelegate:MyNavigationDelegate = session.navigationDelegate as MyNavigationDelegate

        val url = navigationDelegate.getCurrentUrl()

        val base_url = getBaseUrl(url)


            for (sitedata in browser.arr) {

                if (sitedata.coreAdress != base_url)
                    continue

                when (perm.permission) {
                    PERMISSION_DESKTOP_NOTIFICATION -> {



                        if (sitedata.permNoti == 1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_ALLOW)
                        else if (sitedata.permNoti == -1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_DENY)
                        else {
                            alert("Notification", sitedata)
                            return GeckoResult.fromValue(ContentPermission.VALUE_PROMPT)

                        }
                        break;

                    }
                    PERMISSION_GEOLOCATION->{
                        if (sitedata.permLocation == 1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_ALLOW)
                        else if (sitedata.permLocation == -1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_DENY)
                        else {
                            alert("Location", sitedata)
                            return GeckoResult.fromValue(ContentPermission.VALUE_PROMPT)

                        }
                        break;

                    }
                    PERMISSION_AUTOPLAY_INAUDIBLE,
                    PERMISSION_AUTOPLAY_AUDIBLE,
                    -> {
                        if (sitedata.permMedia == 1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_ALLOW)
                        else if (sitedata.permMedia == -1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_DENY)
                        else {
                            alert("Media Autoplay", sitedata)
                            return GeckoResult.fromValue(ContentPermission.VALUE_PROMPT)

                        }
                        break;

                    }
                    PERMISSION_PERSISTENT_STORAGE->{

                        if (sitedata.permStorage == 1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_ALLOW)
                        else if (sitedata.permStorage == -1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_DENY)
                        else {
                            alert("Storage", sitedata)
                            return GeckoResult.fromValue(ContentPermission.VALUE_PROMPT)

                        }
                        break;

                    }
                    PERMISSION_STORAGE_ACCESS->{
                        if (sitedata.permCokies == 1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_ALLOW)
                        else if (sitedata.permCokies == -1)
                            return GeckoResult.fromValue(ContentPermission.VALUE_DENY)
                        else {
                            alert("Cookies", sitedata)
                            return GeckoResult.fromValue(ContentPermission.VALUE_PROMPT)

                        }
                        break;

                    }



                }

            }


       when(perm.permission) {
         PERMISSION_DESKTOP_NOTIFICATION->  alert("Notification", siteData = SiteData(coreAdress = base_url))
           PERMISSION_GEOLOCATION->  alert("Location", siteData = SiteData(coreAdress = base_url))
           PERMISSION_AUTOPLAY_INAUDIBLE,PERMISSION_AUTOPLAY_AUDIBLE-> siteDataviewholder.insertnote(SiteData(coreAdress = base_url, permMedia = 1))
           PERMISSION_PERSISTENT_STORAGE-> {
               siteDataviewholder.insertnote(SiteData(coreAdress = base_url, permStorage = 1))
           }
           PERMISSION_STORAGE_ACCESS-> {

               siteDataviewholder.insertnote(SiteData(coreAdress = base_url, permCokies = 1))

           }
       }
        return GeckoResult.fromValue(ContentPermission.VALUE_PROMPT)

    }

    fun getBaseUrl(urlstr :String):String{
        val url = URL(urlstr)
        val baseUrl: String = url.getProtocol().toString() + "://" + url.getHost()

        return baseUrl;
    }

    override fun onMediaPermissionRequest(
        session: GeckoSession,
        uri: String,
        video: Array<out GeckoSession.PermissionDelegate.MediaSource>?,
        audio: Array<out GeckoSession.PermissionDelegate.MediaSource>?,
        callback: GeckoSession.PermissionDelegate.MediaCallback,
    ) {


        val base_url = getBaseUrl(uri)

        for(site in browser.arr) {

            if(site.coreAdress!=base_url)
                continue

            video?.let {
                if(site.permCamera==1)
                {
                    for (i in 0..it.size - 1) {

                        callback.grant(video?.get(i), audio?.get(0))


                    }
                }
                if(site.permCamera==0){
                    alert("Camera",site,callback)
                    return@let
                }


            }

            audio?.let {
                if(site.permMicrophone==1)
                {
                    for (i in 0..it.size - 1)
                        callback.grant(video?.get(0), audio?.get(i))
                }
                if(site.permMicrophone==0){
                    alert("Microphone",site,callback)
                    return@let
                }

            }

         return

        }

        if(video!=null)  alert("Camera", SiteData(coreAdress = base_url),callback)
        if(audio!=null)   alert("Microphone",SiteData(coreAdress = base_url),callback)


    }


    fun alert(
        str: String = "",
        siteData: SiteData, callback: GeckoSession.PermissionDelegate.MediaCallback? = null,
        audio: Array<out GeckoSession.PermissionDelegate.MediaSource>? = null,
        video: Array<out GeckoSession.PermissionDelegate.MediaSource>? = null,


        ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(listner)

        builder.setMessage("Site is Requesting for $str permisssion?")

        builder.setTitle("Alert !")
        builder.setCancelable(true)
        builder.setPositiveButton("Allow",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->

                when(str) {

                   "Notification"-> {
                       siteData.permNoti = 1
                       siteDataviewholder.insertnote(siteData)
                       dialog!!.cancel()
                   }
                    "Location"-> {
                        siteData.permLocation = 1
                        siteDataviewholder.insertnote(siteData)
                        dialog!!.cancel()
                    }
                    "Media Autoplay"-> {
                        siteData.permMedia = 1
                        siteDataviewholder.insertnote(siteData)
                        dialog!!.cancel()
                    }
                    "Cookies"-> {
                        siteData.permCokies= 1
                        siteDataviewholder.insertnote(siteData)
                        dialog!!.cancel()
                    }

                    "Storage"-> {
                        siteData.permStorage = 1
                        siteDataviewholder.insertnote(siteData)
                        dialog!!.cancel()
                    }
                    "Camera"->{   siteData.permCamera=1;
                        siteDataviewholder.insertnote(siteData)
                        video?.let {
                            for (i in 0..it.size - 1) {

                                callback?.grant(video?.get(i), audio?.get(i))


                            }

                        }

                    }
                    "Microphone"->{  siteData.permMicrophone=1;
                        siteDataviewholder.insertnote(siteData)
                        audio?.let {
                            for (i in 0..it.size - 1) {

                                callback?.grant(video?.get(i), audio?.get(i))


                            }

                        }

                    }

                }


            } as DialogInterface.OnClickListener)

           builder.setNegativeButton("Deny",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->


                when(str) {

                    "Notification"-> {
                        siteData.permNoti=-1
                        SiteDataviewholder(App()).insertnote(siteData)
                        dialog.cancel()
                    }
                    "Location"-> {
                        siteData.permLocation=-1
                        SiteDataviewholder(App()).insertnote(siteData)
                        dialog.cancel()
                    }
                    "Storage"-> {
                        siteData.permStorage = -1
                        siteDataviewholder.insertnote(siteData)
                        dialog!!.cancel()
                    }
                    "Cookies"-> {
                        siteData.permCokies= -1
                        siteDataviewholder.insertnote(siteData)
                        dialog!!.cancel()
                    }
                    "Media Autoplay"-> {
                        siteData.permMedia=-1
                        SiteDataviewholder(App()).insertnote(siteData)
                        dialog.cancel()
                    }
                    "Camera"->{   siteData.permCamera=-1;
                        siteDataviewholder.insertnote(siteData)
                        callback?.reject()

                    }
                    "Microphone"->{  siteData.permMicrophone=-1;
                        siteDataviewholder.insertnote(siteData)
                        callback?.reject()


                    }

                }

            } as DialogInterface.OnClickListener)


        val alertDialog: AlertDialog = builder.create()

        alertDialog.show()

    }



}

class MyMediaDelegate:GeckoSession.MediaDelegate{



    override fun onRecordingStatusChanged(
        session: GeckoSession,
        devices: Array<out GeckoSession.MediaDelegate.RecordingDevice>,
    ) {
        super.onRecordingStatusChanged(session, devices)
    }



}
class MyContentBlockingDelegate(listner: MainActivity2):ContentBlocking.Delegate{
    val listner = listner
    override fun onContentLoaded(session: GeckoSession, event: ContentBlocking.BlockEvent) {

        val myevent = MyBlockingEvent(event.uri,event.antiTrackingCategory,event.safeBrowsingCategory,event.cookieBehaviorCategory,event.isBlocking)



        super.onContentLoaded(session,myevent )



//        event.cookieBehaviorCategory

    }
}


class MyBlockingEvent(uri: String, atCat: Int, sbCat: Int, cbCat: Int, isBlocking: Boolean) :ContentBlocking.BlockEvent(uri, atCat,
    sbCat,
    cbCat,
    isBlocking){

    val uri: String? = uri

    @CBAntiTracking
    private val mAntiTrackingCat = atCat

    @CBSafeBrowsing
    private val mSafeBrowsingCat = sbCat

    @CBCookieBehavior
    private val mCookieBehaviorCat = cbCat
    private val mIsBlocking = isBlocking

    @CBAntiTracking
    override fun getAntiTrackingCategory(): Int {
        return mAntiTrackingCat
    }

    /**
     * The safe browsing category types of the blocked resource.
     *
     * @return One or more of the [SafeBrowsing] flags.
     */
    @UiThread
    @CBSafeBrowsing
    override fun getSafeBrowsingCategory(): Int {
        return mSafeBrowsingCat
    }

    /**
     * The cookie types of the blocked resource.
     *
     * @return One or more of the [CookieBehavior] flags.
     */
    @UiThread
    @CBCookieBehavior
    override fun getCookieBehaviorCategory(): Int {
        return 2
    }

    /* package */
//    fun fromBundle(bundle: GeckoBundle): BlockEvent? {
//        val uri = bundle.getString("uri")
//        val blockedList = bundle.getString("blockedList")
//        val loadedList = TextUtils.join(",", bundle.getStringArray("loadedLists"))
//        val error = bundle.getLong("error", 0L)
//        val category = bundle.getLong("category", 0L)
//        val matchedList = blockedList ?: loadedList
//
//        // Note: Even if loadedList is non-empty it does not necessarily
//        // mean that the event is not a blocking event.
//        val blocking = blockedList != null || error != 0L || isBlockingGeckoCbCat(category)
//        return BlockEvent(
//            uri,
//            atListToAtCat(matchedList)
//                    or cmListToAtCat(matchedList)
//                    or fpListToAtCat(matchedList)
//                    or stListToAtCat(matchedList),
//            errorToSbCat(error),
//            geckoCatToCbCat(category),
//            blocking)
//    }

    @UiThread
    override fun isBlocking(): Boolean {
        return mIsBlocking
    }


}

class MyScrollDelegate(listner: MainActivity2):GeckoSession.ScrollDelegate{
    val listner= listner
    override fun onScrollChanged(session: GeckoSession, scrollX: Int, scrollY: Int) {
       listner.homeFragment.swipeRefreshLayoutControl(scrollX,scrollY)
//        super.onScrollChanged(session, scrollX, scrollY)
    }
}

class MyPromptDelegate(listner: MainActivity2,browser: Browser):GeckoSession.PromptDelegate{
    val listner= listner
    val browser= browser
    @UiThread
    override fun onPopupPrompt(
        session: GeckoSession, prompt: PopupPrompt,
    ): GeckoResult<PromptResponse?>? {


        val builder: AlertDialog.Builder = AlertDialog.Builder(listner)

        builder.setMessage("Site is requesting to open pop up window")

        builder.setTitle("Alert !")
        builder.setCancelable(true)
        builder.setPositiveButton("Allow",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->

                listner.homeFragment.addNewSession(url = prompt.targetUri.toString())
                listner.notifyUser("Opening pop up in new tab")
               prompt.confirm(AllowOrDeny.ALLOW)

            } as DialogInterface.OnClickListener)

        builder.setNegativeButton("Deny",
            DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->

                listner.notifyUser("Pop up blocked")
                prompt.confirm(AllowOrDeny.ALLOW)

            } as DialogInterface.OnClickListener)


        val alertDialog: AlertDialog = builder.create()

        alertDialog.show()

        return GeckoResult.fromValue(prompt.confirm(AllowOrDeny.ALLOW))

    }


    override fun onChoicePrompt(
        session: GeckoSession,
        prompt: GeckoSession.PromptDelegate.ChoicePrompt,
    ): GeckoResult<PromptResponse>? {
        val response= GeckoResult<PromptResponse>()
        listner.choicePrompt(prompt,response)
        return response
    }


    override fun onFilePrompt(
        session: GeckoSession,
        prompt: GeckoSession.PromptDelegate.FilePrompt,
    ): GeckoResult<PromptResponse>? {
        val responce:  GeckoResult<PromptResponse>? = GeckoResult<PromptResponse>()
        listner.selectFile(prompt,responce)
        return responce

    }

    override fun onDateTimePrompt(
        session: GeckoSession,
        prompt: GeckoSession.PromptDelegate.DateTimePrompt,
    ): GeckoResult<PromptResponse>? {

        val response= GeckoResult<PromptResponse>()
         listner.DateSelect(prompt,response)
  return  response

    }





    override fun onAlertPrompt(
        session: GeckoSession,
        prompt: GeckoSession.PromptDelegate.AlertPrompt,
    ): GeckoResult<PromptResponse>? {


        val builder: AlertDialog.Builder = AlertDialog.Builder(listner)

        builder.setMessage("${prompt.message}")

        builder.setTitle("${prompt.title}")
        builder.setCancelable(true)
        builder.setPositiveButton("ok",
            DialogInterface.OnClickListener { dialog: DialogInterface?, which: Int ->



            } as DialogInterface.OnClickListener)

        val alertDialog: AlertDialog = builder.create()

        alertDialog.show()




        return super.onAlertPrompt(session, prompt)
    }

    override fun onCreditCardSave(
        session: GeckoSession,
        request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.CreditCardSaveOption>,
    ): GeckoResult<PromptResponse>? {
        val geckoresult= GeckoResult<PromptResponse>()

        if(browser.settingsData.autoSaveCard==1)
            return  GeckoResult.fromValue(request.confirm(request.options[0]))

    return  geckoresult

        return super.onCreditCardSave(session, request)
    }

    override fun onCreditCardSelect(
        session: GeckoSession,
        request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.CreditCardSelectOption>,
    ): GeckoResult<PromptResponse>? {

//        Toast.makeText(listner,"Crdit card  selec\",Toast.LENGTH_SHORT).show()
        val array :ArrayList<String> = ArrayList()

        for(data in browser.autocompleteList){

            if(data.type.equals("Card"))
                array.add(data.username)

        }
        val x= GeckoResult<PromptResponse>()

        listner.homeFragment.ShowSlideScreen(array,x,request)

        return x
        return super.onCreditCardSelect(session, request)
    }

        override fun onLoginSelect(
            session: GeckoSession,
            request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.LoginSelectOption>,
        ): GeckoResult<PromptResponse>? {


        val array :ArrayList<String> = ArrayList()

        for(data in browser.autocompleteList){
            if("${(session.navigationDelegate as MyNavigationDelegate).url}".contains(data.coreAdress) && data.type.equals("Password")){

             array.add(data.username)
            }
        }
            val x= GeckoResult<PromptResponse>()

        listner.homeFragment.ShowSlideScreen(array,x,request)

        return x

    }




    override fun onLoginSave(
        session: GeckoSession,
        request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.LoginSaveOption>,
    ) : GeckoResult<PromptResponse>? {


        val geckoresult= GeckoResult<PromptResponse>()

        if(browser.settingsData.autoSavePassword==1)
      return  GeckoResult.fromValue(request.confirm(request.options[0]))

                     listner.homeFragment.AskLoginSave(geckoresult,request)


     return  geckoresult
//        return GeckoResult.fromValue(prom)

    }

    override fun onAddressSave(
        session: GeckoSession,
        request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.AddressSaveOption>,
    ): GeckoResult<PromptResponse>? {


        val geckoresult= GeckoResult<PromptResponse>()

        if(browser.settingsData.autoSaveAdress==1)
            return  GeckoResult.fromValue(request.confirm(request.options[0]))

//        listner.homeFragment.AskLoginSave(geckoresult,request)


        return  geckoresult




    }


    override fun onAddressSelect(
        session: GeckoSession,
        request: GeckoSession.PromptDelegate.AutocompleteRequest<Autocomplete.AddressSelectOption>,
    ): GeckoResult<PromptResponse>? {
         val array :ArrayList<String> = ArrayList()

        for(data in browser.autocompleteList){
            if(data.type.equals("Address")){

                array.add(data.username)
            }
        }
        val x= GeckoResult<PromptResponse>()

        listner.homeFragment.ShowSlideScreen(array,x,request)

        return x
    }


    override fun onTextPrompt(
        session: GeckoSession,
        prompt: TextPrompt
    ): GeckoResult<PromptResponse>? {
        return super.onTextPrompt(session, prompt)
    }





}

class MySelectActionDelegate(listner: MainActivity2): GeckoSession.SelectionActionDelegate {
    val listner= listner




    override fun onShowClipboardPermissionRequest(
        session: GeckoSession, permission: ClipboardPermission,
    ): GeckoResult<AllowOrDeny?> {

//        Toast.makeText(listner,"clipbard permission --   -",Toast.LENGTH_SHORT).show()
        return GeckoResult.allow()
    }

    override fun onShowActionRequest(
        session: GeckoSession,
        selection: GeckoSession.SelectionActionDelegate.Selection,
    ) {



    }

    override fun onHideAction(session: GeckoSession, reason: Int) {
//        Toast.makeText(listner,"hide action",Toast.LENGTH_SHORT).show()
        super.onHideAction(session, reason)
    }


    @UiThread
    override fun onDismissClipboardPermissionRequest(session: GeckoSession) {

//        Toast.makeText(listner,"clipbard permission   dismiss",Toast.LENGTH_SHORT).show()
    }
}


class X(activity: Activity) : BasicSelectionActionDelegate(activity) {
    val listner= activity

    override fun onShowClipboardPermissionRequest(
        session: GeckoSession,
        permission: ClipboardPermission,
    ): GeckoResult<AllowOrDeny>? {

//        Toast.makeText(listner,"clipbard permission   basoc",Toast.LENGTH_SHORT).show()

        return super.onShowClipboardPermissionRequest(session, permission)
    }


}


class MyGeckoDisplay(session: GeckoSession?) : GeckoDisplay(session) {

    @UiThread
    override fun capturePixels(): GeckoResult<Bitmap?> {
        return screenshot().capture()
    }

    override fun screenshot(): ScreenshotBuilder {
        return super.screenshot()
    }


}

class MyProgressDetegate(listner: MainActivity2,browser: Browser):GeckoSession.ProgressDelegate{
   val listner= listner
    val browser= browser
     var securityInfo:GeckoSession.ProgressDelegate.SecurityInformation?=null
    public var isSecure=false
    override fun onProgressChange(session: GeckoSession, progress: Int) {
        listner.homeFragment.progessbarHandler(session,progress)
    }

    override fun onSecurityChange(
        session: GeckoSession,
        securityInf: GeckoSession.ProgressDelegate.SecurityInformation,
    ) {
        securityInfo= securityInf
        isSecure= securityInf.isSecure
        listner.homeFragment.setSecurityMode(session)
    }

    override fun onSessionStateChange(
        session: GeckoSession,
        sessionState: GeckoSession.SessionState,
    ) {
    browser.SesssionSateMap[session]= sessionState
    }





}

class MyStorageDelegate(listner: MainActivity2,browser: Browser):Autocomplete.StorageDelegate{

    val listner = listner
    val browser = browser
//    val autocompleteDataviewholder= autocompleteDataviewholder

    override fun onLoginFetch(): GeckoResult<Array<Autocomplete.LoginEntry>>? {
        val array :ArrayList<Autocomplete.LoginEntry> = ArrayList()

        for(data in browser.autocompleteList){

           if( data.type.equals("Password")) {
                val x = Autocomplete.LoginEntry.Builder()

                x.origin(data.origin)
                x.guid(data.guid)
                x.formActionOrigin(data.formActionorigin)
                x.httpRealm(data.httprealm)
                x.password(data.password)
                x.username(data.username)
                array.add(x.build())
            }
        }
        val arr:Array<Autocomplete.LoginEntry> = array.toTypedArray()

        if(arr.size==0)
            return null
        return GeckoResult.fromValue(arr)

    }

    override fun onLoginFetch(domain: String): GeckoResult<Array<Autocomplete.LoginEntry>>? {

        val array :ArrayList<Autocomplete.LoginEntry> = ArrayList()

        for(data in browser.autocompleteList){
            if(data.coreAdress.contains(domain) && data.type.equals("Password")){

                val x= Autocomplete.LoginEntry.Builder()

                x.origin(data.origin)
                x.guid(data.guid)
                x.formActionOrigin(data.formActionorigin)
                x.httpRealm(data.httprealm)
                x.password(data.password)
                x.username(data.username)
                array.add(x.build())
            }
        }
        val arr:Array<Autocomplete.LoginEntry> = array.toTypedArray()

       if(arr.size==0)
           return null
        return GeckoResult.fromValue(arr)

    }

    override fun onLoginSave(login: Autocomplete.LoginEntry) {

        val url = URL(login.origin)

        val autoCompleteData: AutoCompleteData= AutoCompleteData(coreAdress = url.host, guid = login.guid,
            formActionorigin = login.formActionOrigin, origin = login.origin, username = login.username, password = login.password,
            httprealm = login.httpRealm, type = "Password"
            )

        AutocompleteDataviewholder(App()).insertnote(autoCompleteData)

    }


    override fun onCreditCardFetch(): GeckoResult<Array<Autocomplete.CreditCard>>? {


        val array :ArrayList<Autocomplete.CreditCard> = ArrayList()

        for(data in browser.autocompleteList){
            if( data.type.equals("Card")){

                val x= Autocomplete.CreditCard.Builder()
                x.guid(data.guid)
               x.name(data.name_key)
                x.expirationMonth(data.exp_month)
                x.expirationYear(data.exp_year)
                x.number(data.number_key)
                array.add(x.build())
            }
        }
        val arr:Array<Autocomplete.CreditCard> = array.toTypedArray()

        if(arr.size==0)
            return null
        return GeckoResult.fromValue(arr)




        return super.onCreditCardFetch()
    }

    override fun onCreditCardSave(creditCard: Autocomplete.CreditCard) {


        val autoCompleteData: AutoCompleteData= AutoCompleteData( guid = creditCard.guid,
            type = "Card", name_key = creditCard.name, number_key = creditCard.number, exp_month = creditCard.expirationMonth,
            exp_year = creditCard.expirationYear
        )

        AutocompleteDataviewholder(App()).insertnote(autoCompleteData)

    }

    override fun onAddressFetch(): GeckoResult<Array<Autocomplete.Address>>? {
        val array :ArrayList<Autocomplete.Address> = ArrayList()

        for(data in browser.autocompleteList){
            if( data.type.equals("Address")){

                val x= Autocomplete.Address.Builder()
                x.guid(data.guid)
                x.name(data.name_key)
                x.addressLevel1(data.add1_key)
                x.addressLevel2(data.add2_key)
                x.addressLevel3(data.add3_key)
                x.additionalName(data.additional_name_key)
                x.country(data.country_key)
                x.email(data.email_key)
                x.familyName(data.family_name_key)
                x.givenName(data.given_name_key)
                x.organization(data.org_key)
                x.postalCode(data.postal_key)
                x.streetAddress(data.strt_add_key)
                x.tel(data.tel_key)
                array.add(x.build())
            }
        }
        val arr:Array<Autocomplete.Address> = array.toTypedArray()

        if(arr.size==0)
            return null
        return GeckoResult.fromValue(arr)

    }

    override fun onAddressSave(address: Autocomplete.Address) {

        val autoCompleteData: AutoCompleteData= AutoCompleteData( guid = address.guid,
            type = "Address", name_key = address.name, given_name_key = address.givenName,
            additional_name_key = address.additionalName, family_name_key = address.familyName,
            org_key = address.organization, strt_add_key = address.streetAddress, add1_key = address.addressLevel1,
            add2_key = address.addressLevel2, add3_key = address.addressLevel3, postal_key = address.postalCode,
            country_key =  address.country, tel_key = address.tel, email_key =  address.email


        )

        AutocompleteDataviewholder(App()).insertnote(autoCompleteData)
    }

    override fun onLoginUsed(login: Autocomplete.LoginEntry, usedFields: Int) {
        super.onLoginUsed(login, usedFields)
    }

}


class MyMediaSessionDelegate(listner: MainActivity2) : MediaSession.Delegate{
    val listner= listner
    val isplaying = MutableLiveData<Boolean>(false)
    val mediaNotification= MediaNotification(listner,isplaying)


    override fun onMetadata(
        session: GeckoSession,
        mediaSession: MediaSession,
        meta: MediaSession.Metadata,
    ) {
        mediaNotification.setMeta(meta.title.toString(),session,meta.artwork)

        super.onMetadata(session, mediaSession, meta)
    }

    override fun onActivated(session: GeckoSession, mediaSession: MediaSession) {
      listner.mNotification(mediaSession)
        mediaNotification.ShowNotification(session,mediaSession)
        super.onActivated(session, mediaSession)
    }

    override fun onDeactivated(session: GeckoSession, mediaSession: MediaSession) {
//        listner.notifyUser("deactivated")
        mediaNotification.onDeactivate()
        super.onDeactivated(session, mediaSession)
    }

    override fun onPause(session: GeckoSession, mediaSession: MediaSession) {
        isplaying.value= false
//        listner.notifyUser("onPause")
        super.onPause(session, mediaSession)
    }

    override fun onPlay(session: GeckoSession, mediaSession: MediaSession) {
        isplaying.value= true
//        listner.notifyUser("onPlay")
        super.onPlay(session, mediaSession)
    }

    override fun onStop(session: GeckoSession, mediaSession: MediaSession) {
        isplaying.value= false
//        listner.notifyUser("onStop")
        super.onStop(session, mediaSession)
    }




}




