package com.example.squadzframes.model

import android.os.Parcel
import android.os.Parcelable

data class Comment(
        val postId: String? = "",
        val author: String? = "",
        val time: Long = 0L,
        val content: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(postId)
        parcel.writeString(author)
        parcel.writeLong(time)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Comment> {
        override fun createFromParcel(parcel: Parcel): Comment {
            return Comment(parcel)
        }

        override fun newArray(size: Int): Array<Comment?> {
            return arrayOfNulls(size)
        }
    }
}


