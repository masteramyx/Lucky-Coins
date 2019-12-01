package com.paperspace.kyleamyx.luckycoins.list.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "list")
@Parcelize
data class CoinListDbImageItem(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "logo")
        val logo: String? = null) : Parcelable