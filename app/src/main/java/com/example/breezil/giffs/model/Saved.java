package com.example.breezil.giffs.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "saved_gifs_table")
public class Saved implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int saved_id;

    private Double score;
    private String bitlyGifUrl;
    private String bitlyUrl;
    private String contentUrl;
    private String embedUrl;
    private String id;
    private String importDatetime;
    private int isSticker;
    private String rating;
    private String slug;
    private String source;
    private String sourcePostUrl;
    private String sourceTld;
    private String title;
    private String trendingDatetime;
    private String type;
    private String url;
    private String username;


    public Saved(Double score, String bitlyGifUrl, String bitlyUrl, String contentUrl, String embedUrl,
                    String id, String importDatetime, int isSticker, String rating, String slug, String source,
                    String sourcePostUrl, String sourceTld, String title, String trendingDatetime, String type,
                    String url, String username) {
        this.score = score;
        this.bitlyGifUrl = bitlyGifUrl;
        this.bitlyUrl = bitlyUrl;
        this.contentUrl = contentUrl;
        this.embedUrl = embedUrl;
        this.id = id;
        this.importDatetime = importDatetime;
        this.isSticker = isSticker;
        this.rating = rating;
        this.slug = slug;
        this.source = source;
        this.sourcePostUrl = sourcePostUrl;
        this.sourceTld = sourceTld;
        this.title = title;
        this.trendingDatetime = trendingDatetime;
        this.type = type;
        this.url = url;
        this.username = username;
    }

    @Ignore
    public Saved(int saved_id, Double score, String bitlyGifUrl, String bitlyUrl, String contentUrl, String embedUrl,
                    String id, String importDatetime, int isSticker, String rating, String slug, String source,
                    String sourcePostUrl, String sourceTld, String title, String trendingDatetime, String type,
                    String url, String username) {
        this.saved_id = saved_id;
        this.score = score;
        this.bitlyGifUrl = bitlyGifUrl;
        this.bitlyUrl = bitlyUrl;
        this.contentUrl = contentUrl;
        this.embedUrl = embedUrl;
        this.id = id;
        this.importDatetime = importDatetime;
        this.isSticker = isSticker;
        this.rating = rating;
        this.slug = slug;
        this.source = source;
        this.sourcePostUrl = sourcePostUrl;
        this.sourceTld = sourceTld;
        this.title = title;
        this.trendingDatetime = trendingDatetime;
        this.type = type;
        this.url = url;
        this.username = username;
    }


    protected Saved(Parcel in) {
        saved_id = in.readInt();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readDouble();
        }
        bitlyGifUrl = in.readString();
        bitlyUrl = in.readString();
        contentUrl = in.readString();
        embedUrl = in.readString();
        id = in.readString();
        importDatetime = in.readString();
        isSticker = in.readInt();
        rating = in.readString();
        slug = in.readString();
        source = in.readString();
        sourcePostUrl = in.readString();
        sourceTld = in.readString();
        title = in.readString();
        trendingDatetime = in.readString();
        type = in.readString();
        url = in.readString();
        username = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(saved_id);
        if (score == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(score);
        }
        dest.writeString(bitlyGifUrl);
        dest.writeString(bitlyUrl);
        dest.writeString(contentUrl);
        dest.writeString(embedUrl);
        dest.writeString(id);
        dest.writeString(importDatetime);
        dest.writeInt(isSticker);
        dest.writeString(rating);
        dest.writeString(slug);
        dest.writeString(source);
        dest.writeString(sourcePostUrl);
        dest.writeString(sourceTld);
        dest.writeString(title);
        dest.writeString(trendingDatetime);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(username);
    }

    public static final Creator<Saved> CREATOR = new Creator<Saved>() {
        @Override
        public Saved createFromParcel(Parcel in) {
            return new Saved(in);
        }

        @Override
        public Saved[] newArray(int size) {
            return new Saved[size];
        }
    };

    public int getSaved_id() {
        return saved_id;
    }

    public void setSaved_id(int saved_id) {
        this.saved_id = saved_id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getBitlyGifUrl() {
        return bitlyGifUrl;
    }

    public void setBitlyGifUrl(String bitlyGifUrl) {
        this.bitlyGifUrl = bitlyGifUrl;
    }

    public String getBitlyUrl() {
        return bitlyUrl;
    }

    public void setBitlyUrl(String bitlyUrl) {
        this.bitlyUrl = bitlyUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImportDatetime() {
        return importDatetime;
    }

    public void setImportDatetime(String importDatetime) {
        this.importDatetime = importDatetime;
    }

    public int getIsSticker() {
        return isSticker;
    }

    public void setIsSticker(int isSticker) {
        this.isSticker = isSticker;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourcePostUrl() {
        return sourcePostUrl;
    }

    public void setSourcePostUrl(String sourcePostUrl) {
        this.sourcePostUrl = sourcePostUrl;
    }

    public String getSourceTld() {
        return sourceTld;
    }

    public void setSourceTld(String sourceTld) {
        this.sourceTld = sourceTld;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrendingDatetime() {
        return trendingDatetime;
    }

    public void setTrendingDatetime(String trendingDatetime) {
        this.trendingDatetime = trendingDatetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "SavedGif{" +
                "saved_id=" + saved_id +
                ", score=" + score +
                ", bitlyGifUrl='" + bitlyGifUrl + '\'' +
                ", bitlyUrl='" + bitlyUrl + '\'' +
                ", contentUrl='" + contentUrl + '\'' +
                ", embedUrl='" + embedUrl + '\'' +
                ", id='" + id + '\'' +
                ", importDatetime='" + importDatetime + '\'' +
                ", isSticker=" + isSticker +
                ", rating='" + rating + '\'' +
                ", slug='" + slug + '\'' +
                ", source='" + source + '\'' +
                ", sourcePostUrl='" + sourcePostUrl + '\'' +
                ", sourceTld='" + sourceTld + '\'' +
                ", title='" + title + '\'' +
                ", trendingDatetime='" + trendingDatetime + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }


}