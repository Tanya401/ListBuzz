package com.example.listbuzz.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Priority: Parcelable {
High,
    Medium,
    Low
}