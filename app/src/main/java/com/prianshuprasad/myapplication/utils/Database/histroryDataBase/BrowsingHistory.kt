package com.prianshuprasad.myapplication.utils.Database.histroryDataBase

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "notes_table")
class BrowsingHistory(@ColumnInfo(name="webName") val webName:String, @ColumnInfo(name="url")val url:String,@ColumnInfo(name="time")val time:Long,
                      @ColumnInfo(name="favicon")var favicon:String="") {
    @PrimaryKey(autoGenerate=true) var id=0;


}