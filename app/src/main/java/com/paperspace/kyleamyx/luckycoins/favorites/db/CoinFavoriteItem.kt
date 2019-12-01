package com.paperspace.kyleamyx.luckycoins.favorites.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class CoinFavoriteItem(
        @PrimaryKey
        val id: Int,
        @ColumnInfo(name = "logo")
        val logo: String? = null,
        @ColumnInfo(name = "name")
        val name: String? = null,
        @ColumnInfo(name = "symbol")
        val symbol: String? = null
) : Parcelable