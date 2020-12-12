package com.example.squadzframes.model

import android.os.Parcel
import android.os.Parcelable

data class Post (
        val id: String? = "",
        val content: String? = "",
        val author: String? = "",
        val timestamp: Long = 0L,
        val likes: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(content)
        parcel.writeString(author)
        parcel.writeLong(timestamp)
        parcel.writeInt(likes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}