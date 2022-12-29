package com.prianshuprasad.myapplication


import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import com.prianshuprasad.myapplication.autocompleteDatabase.AutoCompleteData
import com.prianshuprasad.myapplication.bookmarkDatabase.BookmarkData
import com.prianshuprasad.myapplication.downloadDataBase.DownloadData
import com.prianshuprasad.myapplication.siteDatabase.SiteData
import com.tonyodev.fetch2.Download
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.GeckoSession

class Browser() :Parcelable {

    public var sessionList = mutableListOf<GeckoSession>()
    public var sessionImage = mutableListOf<Bitmap?>()

    public var sessionRuntime = mutableListOf<GeckoRuntime>()

    public var naviggationDelegateList = mutableListOf<MyNavigationDelegate>()

    var isAnonymousList = mutableListOf<Boolean>()

    var bookmarkMap: MutableMap<String,BookmarkData> = mutableMapOf()
    var SesssionSateMap: MutableMap<GeckoSession,GeckoSession.SessionState> = mutableMapOf()

    var arr:ArrayList<SiteData> = ArrayList()
      var settingsData: SiteData = SiteData()

    public var autocompleteList:ArrayList<AutoCompleteData> = ArrayList()
    public var downloadList:ArrayList<DownloadData> = ArrayList()
    public var downloadArrayList:ArrayList<Download> = ArrayList()

    //    var myContentDelegate:MyContentDelegate= MyContentDelegate()
    var currIndex=0;

    constructor(parcel: Parcel) : this() {
        currIndex = parcel.readInt()
    }


    public fun setHttpsOnly(value:Int){

        for(runtime in sessionRuntime){
      runtime.settings.setAllowInsecureConnections(value)
        }

    }

    fun setAntiTracking(value:Boolean)
    {
        for(session in sessionList){
            session.settings.useTrackingProtection = value
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(currIndex)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Browser> {
        override fun createFromParcel(parcel: Parcel): Browser {
            return Browser(parcel)
        }

        override fun newArray(size: Int): Array<Browser?> {
            return arrayOfNulls(size)
        }
    }


}