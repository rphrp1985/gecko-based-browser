package com.prianshuprasad.myapplication.utils.Database.downloadDataBase

import android.webkit.WebStorage
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import java.lang.Math.random
import kotlin.math.roundToInt

@Entity(tableName = "download_table")
data class DownloadData(

                        @ColumnInfo(name="id")var id:Int =0,
                        @ColumnInfo(name="title")var title:String="",
                        @ColumnInfo(name="progress")var progress:Int=0,
                        @ColumnInfo(name="speed")var downloadSpeed:Long=0,
                        @ColumnInfo(name="eta")var eta:Long= Long.MAX_VALUE,
                        @ColumnInfo(name="status")var status:String="Waiting",
                        @ColumnInfo(name="isComplete")var isCompleted:Boolean= false ,
                        @ColumnInfo(name="isErroe")var isError:Boolean= false ,
                        @ColumnInfo(name="url")var url:String="" ,
                        @ColumnInfo(name="fileurl")var fileurl:String="" ,



                            ) {
    @PrimaryKey(autoGenerate=true) var did =0;
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

data class DownloadHelper(var eta:Long=0,var speed:Long=0)
