package com.sample.characters.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RelatedTopicModel(var FirstURL: String, var Result: String, var Text: String, var Icon: IconModel):Parcelable{

}
