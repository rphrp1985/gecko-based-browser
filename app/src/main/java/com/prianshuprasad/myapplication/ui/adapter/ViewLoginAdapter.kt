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
//import com.prianshuprasad.myapplication.Database.autocompleteDatabase.AutoCompleteData
import com.prianshuprasad.myapplication.ui.fragments.HomeFragment
import com.prianshuprasad.myapplication.ui.fragments.TabFragment
import com.prianshuprasad.myapplication.ui.fragments.ViewLoginsFragment
import com.prianshuprasad.myapplication.utils.Database.autocompleteDatabase.AutoCompleteData
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.Image
import org.w3c.dom.Text

class ViewLLoginAdapter(private val listener: ViewLoginsFragment) :
    RecyclerView.Adapter<ViewLLoginAdapter.ViewHolder>() {


    private val item: ArrayList<AutoCompleteData> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var host: TextView
        var username:TextView
        var delete:ImageView


        init {

            host= view.findViewById(R.id.host)
            username = view.findViewById(R.id.username)
            delete= view.findViewById(R.id.delete)


        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_view_login, viewGroup, false)

        val viewHolder= ViewHolder(view)


        viewHolder.host.setOnClickListener {
            listener.showPassword(viewHolder.adapterPosition)
//            listener.onCommit(item[viewHolder.adapterPosition])
        }

        viewHolder.host.setOnClickListener {
            listener.showPassword(viewHolder.adapterPosition)
//            listener.onCommit(item[viewHolder.adapterPosition])
        }

        viewHolder.username.setOnClickListener {
            listener.showPassword(viewHolder.adapterPosition)
//            listener.onCommit(item[viewHolder.adapterPosition])
        }

        viewHolder.delete.setOnClickListener {
            listener.delete(viewHolder.adapterPosition)
        }

        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val curritem = item[position]

        viewHolder.host.text= curritem.coreAdress
        viewHolder.username.text = curritem.username


//        viewHolder.searchSuggestion.text= curritem




    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array:ArrayList<AutoCompleteData>){
        item.clear()
        item.addAll(array)


        notifyDataSetChanged()

    }





}
