package com.prianshuprasad.myapplication.utils.Database.savedTabDatabase

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "tab_table")
class savedTab(@ColumnInfo(name="url")var url:String="") {
    @PrimaryKey(autoGenerate=true) var id=0;


}