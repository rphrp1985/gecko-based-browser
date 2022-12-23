package com.prianshuprasad.myapplication.siteDatabase

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.lang.Math.random
import kotlin.math.roundToInt

@Entity(tableName = "site_table")
data class SiteData(@PrimaryKey() val coreAdress:String="", @ColumnInfo(name="permissionNotification")var permNoti:Int=0,
                    @ColumnInfo(name="permissionCookies") var permCokies:Int =1,
                    @ColumnInfo(name="permissionLocation") var permLocation:Int =0,
                    @ColumnInfo(name="permissionCamera") var permCamera:Int =0,
                    @ColumnInfo(name="permissionMicrophone") var permMicrophone:Int =0,
                    @ColumnInfo(name="permissionMotionSensor") var permMotionS:Int =0,
                    @ColumnInfo(name="permissionJavaScript") var permJavaScript:Int =1,
                    @ColumnInfo(name="permissionPopups") var permPopup:Int =0,
                    @ColumnInfo(name="permissionDesktopMode") var permDesktop:Int =0,
                    @ColumnInfo(name="permissionAutoDownload") var permAutoDownload:Int =0,
                    @ColumnInfo(name="permissionMedia") var permMedia:Int =1,
                    @ColumnInfo(name="permissionProtectedContent") var permProtectdContent:Int =1,
                    @ColumnInfo(name="permissionSound") var permSound:Int =1,
                    @ColumnInfo(name="permissionStorage") var permStorage:Int =1,
                    @ColumnInfo(name="permissionUSB") var permUSB:Int =0,
                    @ColumnInfo(name="permissionClipboard") var permClipboard:Int =0,
                    @ColumnInfo(name="privacyHttpsOnly") var privHttpsOnly:Int =0,
                    @ColumnInfo(name="privacyFingerPrint") var privFingerprint:Int =0,
                    @ColumnInfo(name="privacyPaymentMethod") var privpayment:Int =0,
                    @ColumnInfo(name="privacyNotTrack") var privNotTrack:Int =0,
                    @ColumnInfo(name="privacyAutoCompleteSearches") var privAutoComplete:Int =1,
                    @ColumnInfo(name="privacyHistory") var privHistory:Int =1,

                    @ColumnInfo(name="autoSavePasswords") var autoSavePassword:Int =1,
                    @ColumnInfo(name="autocard") var autoSaveCard:Int =1,
                    @ColumnInfo(name="autoaddress") var autoSaveAdress:Int =1,
                    @ColumnInfo(name="tabsave") var saveTabs:Int =0,

                    @ColumnInfo(name="swipeRefresh") var swipeRefresh:Int =1,
                    @ColumnInfo(name="SettingsDefaultSearchname") var defEngineName:String ="GOOGLE",
                    @ColumnInfo(name="SettingsDefaultSearchurl") var defEngineUrl:String ="https://www.google.com/search?q="



) {
//    @PrimaryKey(autoGenerate=true) var id =0;
//
//
//    fun strToNum(str:String):Int{
//        var x= random();
//
//        return x.roundToInt();
//    }

    //1 -> allowed
//    -1-> not allowed
//0 noting

}