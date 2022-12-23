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
import com.prianshuprasad.myapplication.fragments.TabFragment
import org.mozilla.geckoview.GeckoSession
import org.mozilla.geckoview.GeckoView
import org.mozilla.geckoview.Image

class TabAdapter(private val listener: TabFragment) :
    RecyclerView.Adapter<TabAdapter.ViewHolder>() {


    private val item: ArrayList<GeckoSession> = ArrayList()
    private  var browser:Browser = Browser()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        var geckoView: GeckoView
        var webName: TextView
        var closeButton:ImageView
        var image:ImageView


        init {
//            geckoView=view.findViewById(R.id.tabWebView)
            webName = view.findViewById(R.id.webName)
            closeButton =view.findViewById(R.id.closeTab)
            image= view.findViewById(R.id.image)


        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_view, viewGroup, false)

        val viewHolder= ViewHolder(view)

        viewHolder.image.setOnClickListener {
            listener.openWeb(viewHolder.adapterPosition)
        }

        viewHolder.closeButton.setOnClickListener {
           listener.removeTab(viewHolder.adapterPosition)
        }


        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val curritem = item[position]



        viewHolder.webName.text = (curritem.contentDelegate as MyContentDelegate).title





        if(browser.sessionImage[position]!=null)
        viewHolder.image.setImageBitmap(browser.sessionImage[position])
        else
            viewHolder.image.setImageResource(R.drawable.ic_baseline_web_24)


        viewHolder.closeButton.bringToFront()


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun updatenews(array:ArrayList<GeckoSession>,browseri: Browser){
        item.clear()
        item.addAll(array)
         browser= browseri

        notifyDataSetChanged()

    }





}
