package com.sample.simpsonsviewer.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.diabetics.ApiInterface
import com.sample.simpsonsviewer.adapter.SimpsonCharAdapter
import com.sample.simpsonsviewer.databinding.ActivityMainBinding
import com.sample.simpsonsviewer.model.RelatedTopicModel
import com.sample.simpsonsviewer.model.SimpsonCharModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Character.toLowerCase
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var simpsonCharAdapter: SimpsonCharAdapter
    lateinit var mainList: ArrayList<RelatedTopicModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)
        // setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            setUpResponse()
            mainBinding.characterRecycler.setHasFixedSize(true)
        }

        mainBinding.characterSearch.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().lowercase()
                 //simpsonCharAdapter.getFilter().filter(query)
                //simpsonCharAdapter.filter(query)
                filter(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val query = s.toString().lowercase()
                //simpsonCharAdapter.getFilter().filter(query)
                filter(query)
            }

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase()
                //simpsonCharAdapter.getFilter().filter(query)
                //simpsonCharAdapter.filter(query)
                filter(query)
            }
        })
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist/*: MutableList<RelatedTopicModel>*/ =
            ArrayList<RelatedTopicModel>()

        // running a for loop to compare elements.
        for (item in mainList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.Result.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this@MainActivity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            simpsonCharAdapter.filterFavList(filteredlist)
        }
    }

    fun searchChar(searchStr: String) {
        for (char in mainList) {
            if (char.Result.contains(searchStr)) {
                var searchList = kotlin.collections.ArrayList<RelatedTopicModel>()
                searchList.addAll(mainList)
                attachAdapter(searchList)
            }
        }
    }


    private fun filterWithQuery(query: String) {
        // Perform operation only is query is not empty
        if (query.isNotEmpty()) {
            // Call the function with valid query and take new filtered list.
            val filteredList: kotlin.collections.ArrayList<RelatedTopicModel> =
                onQueryChanged(query)
            // Call the adapter with new filtered array list
            attachAdapter(filteredList)
            // If the matches are empty hide RecyclerView and show error text
            toggleRecyclerView(filteredList)
        } else if (query.isEmpty()) {
            // If query is empty don't make changes to list
            attachAdapter(mainList)
        }
    }

    private fun onQueryChanged(filterQuery: String): ArrayList<RelatedTopicModel> {
        // Empty new array list which contains new strings
        val filteredList = ArrayList<RelatedTopicModel>()
        // Loop through each item in list
        for (charList in mainList) {
            // Before checking string matching convert string to lower case.
            if (charList.Result.lowercase().contains(filterQuery)) {
                // If the match is success, add item to list.
                filteredList.add(charList)
            }
        }
        return filteredList
    }

    private fun attachAdapter(list: kotlin.collections.ArrayList<RelatedTopicModel>) {
        val searchAdapter = SimpsonCharAdapter(this@MainActivity, list)
        mainBinding.characterRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
        mainBinding.characterRecycler.adapter = searchAdapter
    }

    private fun toggleRecyclerView(charList: List<RelatedTopicModel>) {
        if (charList.isEmpty()) {
            //noSearchResultsFoundText.visibility = View.VISIBLE
        } else {
            //recyclerView.visibility = View.VISIBLE
            //noSearchResultsFoundText.visibility = View.INVISIBLE
        }
    }

    private fun setUpResponse() {

        val api = ApiInterface.createApi().getCharacters()

        api.enqueue(object : Callback<SimpsonCharModel> {
            override fun onResponse(
                call: Call<SimpsonCharModel>,
                response: Response<SimpsonCharModel>
            ) {
                if (response.isSuccessful) {
                    mainList = response.body()!!.RelatedTopics as ArrayList<RelatedTopicModel>
                    //Log.d("TAG", "onResponse: $mainList")
                    Log.d("TAG", "onResponse: +$mainList")

                }

                CoroutineScope(Dispatchers.Main).launch {
                    mainBinding.characterRecycler.apply {
                        this.layoutManager = LinearLayoutManager(this@MainActivity)
                        simpsonCharAdapter = SimpsonCharAdapter(this@MainActivity, mainList)
                        this.adapter = simpsonCharAdapter
                    }
                }
            }

            override fun onFailure(call: Call<SimpsonCharModel>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}