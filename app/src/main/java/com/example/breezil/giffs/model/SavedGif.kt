package com.example.breezil.giffs.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "saved_gifs_table")
data class SavedGif(

    @PrimaryKey(autoGenerate = true) var saved_id : Int,

    val score: Double,

    val bitlyGifUrl: String,
    val bitlyUrl: String,
    val contentUrl: String,
    val embedUrl: String,
    val id: String,
    val importDatetime: String,
    val isSticker: Int,
    val rating: String,
    val slug: String,
    val source: String,
    val sourcePostUrl: String,
    val sourceTld: String,
    val title: String,
    val trendingDatetime: String,
    val type: String,
    val url: String,
    val username: String ) : Parcelable {
//    constructor(score: Double, bitlyGifUrl: String, bitlyUrl: String, contentUrl: String, embedUrl: String,
//                id: String, importDatetime: String, isSticker: Int, rating: String, slug: String, source: String,
//                sourcePostUrl: String, sourceTld: String, title: String, trendingDatetime: String,
//                type: String, url: String, username: String ) : this()
}


//): Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readDouble(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readInt(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString())
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(saved_id)
//        parcel.writeDouble(score)
//        parcel.writeString(bitlyGifUrl)
//        parcel.writeString(bitlyUrl)
//        parcel.writeString(contentUrl)
//        parcel.writeString(embedUrl)
//        parcel.writeString(id)
//        parcel.writeString(importDatetime)
//        parcel.writeInt(isSticker)
//        parcel.writeString(rating)
//        parcel.writeString(slug)
//        parcel.writeString(source)
//        parcel.writeString(sourcePostUrl)
//        parcel.writeString(sourceTld)
//        parcel.writeString(title)
//        parcel.writeString(trendingDatetime)
//        parcel.writeString(type)
//        parcel.writeString(url)
//        parcel.writeString(username)
//
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<Gif> {
//        override fun createFromParcel(parcel: Parcel): Gif {
//            return Gif(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Gif?> {
//            return arrayOfNulls(size)
//        }
//    }
//
//}

