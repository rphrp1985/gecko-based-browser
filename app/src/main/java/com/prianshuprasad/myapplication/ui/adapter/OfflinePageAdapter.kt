package com.prianshuprasad.myapplication.ui.adapter
import android.graphics.Bitmap
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.ui.fragments.HistoryView
import com.prianshuprasad.myapplication.ui.fragments.OfflinePagesFragment
import com.prianshuprasad.myapplication.ui.fragments.TabFragment
import com.prianshuprasad.myapplication.utils.Database.offlinePagesDataBase.offlinePage
//import com.prianshuprasad.myapplication.Database.histroryDataBase.BrowsingHistory
//import com.prianshuprasad.myapplication.Database.offlinePagesDataBase.offlinePage
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.Image

class OfflinePageAdapter(private val listener: OfflinePagesFragment) :
    RecyclerView.Adapter<OfflinePageAdapter.ViewHolder>() {


    private val item: ArrayList<offlinePage> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

      var webname:TextView
      var weburl:TextView
      var deleteHistory:ImageView



        init {

        webname = view.findViewById(R.id.webName)
        weburl= view.findViewById(R.id.webUrl)
            deleteHistory= view.findViewById(R.id.deleteHistory)



        }




    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_history, viewGroup, false)

        val viewHolder= ViewHolder(view)


        viewHolder.webname.setOnClickListener {
            listener.openPage(item[viewHolder.adapterPosition].url)
        }
        viewHolder.weburl.setOnClickListener {
            listener.openPage(item[viewHolder.adapterPosition].url)
        }

        viewHolder.deleteHistory.setOnClickListener {
            listener.delete(item[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        val curritem = item[position]



        viewHolder.webname.text = curritem.name
       viewHolder.weburl.text = curritem.weburl



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array:ArrayList<offlinePage>){

        item.clear()
        item.addAll(array)

        notifyDataSetChanged()

    }





}
