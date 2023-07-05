package com.sample.characters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IconModel(var Height: String, var URL: String, var Width: String):Parcelable{

}
