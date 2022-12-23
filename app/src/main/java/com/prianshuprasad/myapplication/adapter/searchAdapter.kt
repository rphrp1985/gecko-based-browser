package com.prianshuprasad.myapplication


import android.graphics.Bitmap
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.fragments.HomeFragment
import com.prianshuprasad.myapplication.fragments.TabFragment
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.Image

class SearchAdapter(private val listener: HomeFragment) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    private val item: ArrayList<String> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var searchSuggestion: TextView


        init {

            searchSuggestion = view.findViewById(R.id.search_suggestion)


        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_search, viewGroup, false)

        val viewHolder= ViewHolder(view)


        view.setOnClickListener {
            listener.onCommit(item[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val curritem = item[position]

        viewHolder.searchSuggestion.text= curritem




    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array:ArrayList<String>){
        item.clear()
        item.addAll(array)


        notifyDataSetChanged()

    }





}
