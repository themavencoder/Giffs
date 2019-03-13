package com.example.breezil.giffs.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "saved_gifs_table")
data class SavedGif(

    var score: Double,
    var bitlyGifUrl: String,
    var bitlyUrl: String,
    var contentUrl: String,
    var embedUrl: String,
    var id: String,
    var importDatetime: String,
    var isSticker: Int,
    var rating: String,
    var slug: String,
    var source: String,
    var sourcePostUrl: String,
    var sourceTld: String,
    var title: String,
    var trendingDatetime: String,
    var type: String,
    var url: String,
    var username: String
) : Parcelable {
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var saved_id: Int = 0

}
