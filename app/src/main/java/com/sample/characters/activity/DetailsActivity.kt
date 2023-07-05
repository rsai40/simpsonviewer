package com.sample.characters.activity

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.sample.characters.databinding.ActivityDetailsBinding
import com.sample.characters.model.RelatedTopicModel
import com.sample.characters.network.ApiInterface

class DetailsActivity : AppCompatActivity() {

    private lateinit var detailsBinding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsBinding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = detailsBinding.root
        setContentView(view)

        val fade = android.transition.Fade()
        window.enterTransition = fade
        window.exitTransition = fade

        val charList = intent?.getParcelableArrayListExtra<RelatedTopicModel>("CharList")!!
        val pos = intent.getIntExtra("position", 0)

        detailsBinding.charText.text = Html.fromHtml(charList[pos].Result).toString()
        detailsBinding.charDesc.text = Html.fromHtml(charList[pos].Text).toString()

        if (charList[pos].Icon.URL != "") {
            val imageUrl = ApiInterface.IMAGE_URL + charList[pos].Icon.URL

            Glide.with(this@DetailsActivity)
                .asBitmap()
                .load(imageUrl)
                .into(detailsBinding.charImage)
        }
    }
}