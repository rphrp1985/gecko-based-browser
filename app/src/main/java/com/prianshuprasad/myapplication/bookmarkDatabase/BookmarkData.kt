package com.prianshuprasad.myapplication.bookmarkDatabase

import android.webkit.WebStorage
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.lang.Math.random
import kotlin.math.roundToInt

@Entity(tableName = "bookmark_table")
data class BookmarkData(@PrimaryKey val coreAdress:String, @ColumnInfo(name="type")var name:String




                            ) {
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