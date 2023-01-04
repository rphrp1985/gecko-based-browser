package com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "offline_table")
class offlinePage(@ColumnInfo(name="url")var url:String="",@ColumnInfo(name="weburl")var weburl:String="",@ColumnInfo(name="name")var name:String="") {
    @PrimaryKey(autoGenerate=true) var id=0;


}