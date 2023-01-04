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
import com.prianshuprasad.myapplication.ui.fragments.TabFragment
import com.prianshuprasad.myapplication.utils.Database.histroryDataBase.BrowsingHistory
//import com.prianshuprasad.myapplication.Database.histroryDataBase.BrowsingHistory
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.Image

class HistoryViewAdapter(private val listener: HistoryView) :
    RecyclerView.Adapter<HistoryViewAdapter.ViewHolder>() {


    private val item: ArrayList<BrowsingHistory> = ArrayList()

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
            listener.openHistoryTab(item[viewHolder.adapterPosition].url)
        }
        viewHolder.weburl.setOnClickListener {
            listener.openHistoryTab(item[viewHolder.adapterPosition].url)
        }

        viewHolder.deleteHistory.setOnClickListener {
            listener.deleteHistory(item[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        val curritem = item[position]



        viewHolder.webname.text = curritem.webName
       viewHolder.weburl.text = curritem.url



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array:ArrayList<BrowsingHistory>){

        item.clear()
        item.addAll(array)

        notifyDataSetChanged()

    }





}
