package com.example.squadzframes.model

import android.os.Parcel
import android.os.Parcelable

data class Post (
        val location: String? = "",
        val host: String? = "",
        val time: String? = "",
        val party: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(location)
        parcel.writeString(host)
        parcel.writeString(time)
        parcel.writeString(party)
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