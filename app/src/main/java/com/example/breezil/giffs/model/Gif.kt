package com.example.breezil.giffs.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Gif(
    @SerializedName("_score")
    val score: Double,
    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String,
    @SerializedName("bitly_url")
    val bitlyUrl: String,
    @SerializedName("content_url")
    val contentUrl: String,
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("import_datetime")
    val importDatetime: String,
    @SerializedName("is_sticker")
    val isSticker: Int,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("source_post_url")
    val sourcePostUrl: String,
    @SerializedName("source_tld")
    val sourceTld: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("trending_datetime")
    val trendingDatetime: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,

    @SerializedName("username")
    val username: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeDouble(score)
        parcel?.writeString(bitlyGifUrl)
        parcel?.writeString(bitlyUrl)
        parcel?.writeString(contentUrl)
        parcel?.writeString(embedUrl)
        parcel?.writeString(id)
        parcel?.writeString(importDatetime)
        parcel?.writeInt(isSticker)
        parcel?.writeString(rating)
        parcel?.writeString(slug)
        parcel?.writeString(source)
        parcel?.writeString(sourcePostUrl)
        parcel?.writeString(sourceTld)
        parcel?.writeString(title)
        parcel?.writeString(trendingDatetime)
        parcel?.writeString(type)
        parcel?.writeString(url)
        parcel?.writeString(username)

    }

    override fun describeContents(): Int {
       return 0
    }

    companion object CREATOR : Parcelable.Creator<Gif> {
        override fun createFromParcel(parcel: Parcel): Gif {
            return Gif(parcel)
        }

        override fun newArray(size: Int): Array<Gif?> {
            return arrayOfNulls(size)
        }
    }

}