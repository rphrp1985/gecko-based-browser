package com.prianshuprasad.myapplication.utils.Database.autocompleteDatabase

import android.webkit.WebStorage
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.lang.Math.random
import kotlin.math.roundToInt

@Entity(tableName = "autocomplete_table")
data class AutoCompleteData(@ColumnInfo(name="coredress") val coreAdress:String="", @ColumnInfo(name="type")var type:String?=null,
                            @ColumnInfo(name="guid")var guid:String?="",
                            @ColumnInfo(name="formaction")var formActionorigin:String?="",
                            @ColumnInfo(name="origin")var origin: String="",
                            @ColumnInfo(name="httprealm")var httprealm:String?="",
                            @ColumnInfo(name="username")var username:String="",
                            @ColumnInfo(name="password")var password:String="",
                            @ColumnInfo(name="name_key")var name_key:String="",
                            @ColumnInfo(name="givrn_name_key")var given_name_key:String="",
                            @ColumnInfo(name="add_name_key")var additional_name_key:String="",
                            @ColumnInfo(name="family_name_key")var family_name_key:String="",
                            @ColumnInfo(name="org_key")var org_key:String="",
                            @ColumnInfo(name="strt_add_key")var strt_add_key:String="",
                            @ColumnInfo(name="add1_key")var add1_key:String="",
                            @ColumnInfo(name="add2_key")var add2_key:String="",
                            @ColumnInfo(name="add3_key")var add3_key:String="",
                            @ColumnInfo(name="postalcode_key")var postal_key:String="",
                            @ColumnInfo(name="country_key")var country_key:String="",
                            @ColumnInfo(name="tel_key")var tel_key:String="",
                            @ColumnInfo(name="email_key")var email_key:String="",
                            @ColumnInfo(name="number_key")var number_key:String="",
                            @ColumnInfo(name="exp_month")var exp_month:String="",
                            @ColumnInfo(name="exp_year")var exp_year:String="",





                            ) {
    @PrimaryKey(autoGenerate=true) var id =0;
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