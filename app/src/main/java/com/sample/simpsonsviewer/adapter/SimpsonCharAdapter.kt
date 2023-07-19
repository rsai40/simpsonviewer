package com.sample.simpsonsviewer.adapter

import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.sample.simpsonsviewer.activity.DetailsActivity
import com.sample.simpsonsviewer.activity.MainActivity
import com.sample.simpsonsviewer.databinding.CharacterItemBinding
import com.sample.simpsonsviewer.model.RelatedTopicModel
import com.sample.simpsonsviewer.network.ApiInterface

class SimpsonCharAdapter(
    private var mainActivity: MainActivity,
    private var simpList: ArrayList<RelatedTopicModel>
) : Adapter<RecyclerView.ViewHolder>() {

    private lateinit var simpCharacterItemBinding: CharacterItemBinding
    var charList = simpList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        simpCharacterItemBinding =
            CharacterItemBinding.inflate(LayoutInflater.from(mainActivity), parent, false)
        return ViewHolder(simpCharacterItemBinding)
    }

    inner class ViewHolder(val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder) {
            with(charList[position]) {

                simpCharacterItemBinding.charText.text = Html.fromHtml(this.Result).toString()
                val iconUrl = charList[position].Icon.URL

                if (charList[position].Icon.URL != "") {
                    var url = ApiInterface.IMAGE_URL + iconUrl;
                    Glide.with(mainActivity)
                        .asBitmap()
                        .load(url)
                        .into(simpCharacterItemBinding.charImage)
                }
            }

            this.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, DetailsActivity::class.java)
                intent.putParcelableArrayListExtra("CharList", charList)
                intent.putExtra("position", position)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    mainActivity,
                    simpCharacterItemBinding.charImage,
                    ViewCompat.getTransitionName(simpCharacterItemBinding.charImage)!!
                )
                v.context.startActivity(intent, options.toBundle())
            }
        }
    }



    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    fun filterFavList(filterList: ArrayList<RelatedTopicModel>) {
        charList = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return charList.size
    }
}



/*
* fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                //searchableList.clear()
                 val filterResults = FilterResults()

                var searchableList: MutableList<RelatedTopicModel> = arrayListOf()

                if (constraint.isNullOrBlank()) {
                    searchableList.addAll(simpList)
                } else {

                    val filterPattern = constraint.toString().lowercase().trim()
                    for (item in simpList) {
                        if (item.Result!!.lowercase().contains(filterPattern)) {
                            searchableList.add(item)
                        }
                    }
                }
                filterResults.values= searchableList

                return filterResults

                /*return filterResults.also {
                    it.values = searchableList
                }*/
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                //if (searchableList.isNullOrEmpty()) {
                simpList= results!!.values as ArrayList<RelatedTopicModel>
                    notifyDataSetChanged()
                    //Toast.makeText(mainActivity, "nothing found", Toast.LENGTH_LONG).show()
                //}
                //onNothingFound?.invoke()

            }
        }
    }

    fun filter(query: String) {
        val filteredList = simpList.filter { item ->
            // Customize your filtering logic here based on your data model and query
            item.Result.contains(query, ignoreCase = true)

        }

       // searchableList.addAll(filteredList)
        // Update the dataset with the filtered items
        notifyDataSetChanged()
    }


    fun searchChar(searchStr: String) {
        for (char in simpList) {
            if (char.Result.contains(searchStr)) {
                //searchableList.addAll(simpList)
                notifyDataSetChanged()
            }
        }
    }

    fun upToDate(newList: Collection<RelatedTopicModel>?) {
        simpList = ArrayList()
        simpList.addAll(newList!!)
        notifyDataSetChanged()
    }

* */