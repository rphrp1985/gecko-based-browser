package com.prianshuprasad.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
//import com.prianshuprasad.myapplication.Database.downloadDataBase.DownloadHelper
import com.prianshuprasad.myapplication.ui.fragments.DownloadFragment
import com.prianshuprasad.myapplication.utils.Database.downloadDataBase.DownloadHelper
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Status
import java.net.URL
import java.util.concurrent.TimeUnit

class DownloadAdapter(private val listener: DownloadFragment) :
    RecyclerView.Adapter<DownloadAdapter.ViewHolder>() {

    private val helperMap:MutableMap<Int, DownloadHelper> = mutableMapOf()
    private val item: ArrayList<Download> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title:TextView = view.findViewById(R.id.titleTextView)
        var progressBar:ProgressBar =view.findViewById(R.id.progressBar)
        var actionButton:Button = view.findViewById(R.id.actionButton)
        var progressText:TextView = view.findViewById(R.id.progress_TextView)
        var downloadSpeed:TextView = view.findViewById(R.id.downloadSpeedTextView)
        var remainingText:TextView = view.findViewById(R.id.remaining_TextView)
        var status:TextView = view.findViewById(R.id.status_TextView)
        var icon:ImageView = view.findViewById(R.id.icon)



    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_download_item, viewGroup, false)

        listener.listener.registerForContextMenu(view);

        val viewHolder= ViewHolder(view)

        view.setOnLongClickListener {
            listener.contextMenuId=  item[viewHolder.adapterPosition].id

                return@setOnLongClickListener false
        }
        view.setOnClickListener {

//            Toast.makeText(listener.listener,"On setonlcikclistrebr",Toast.LENGTH_SHORT).show()
            val index= viewHolder.adapterPosition
            if(item[index].status==Status.COMPLETED){
                listener.openFile(index)
            }

        }

        viewHolder.actionButton.setOnClickListener {
            if(viewHolder.actionButton.text=="Pause"){
                listener.pause(item[viewHolder.adapterPosition].id)
            }

            if(viewHolder.actionButton.text=="Resume"){
                listener.resume(item[viewHolder.adapterPosition].id)
            }



        }



        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val curritem = item[position]

        viewHolder.title.text = getName(curritem.file)
        viewHolder.progressBar.visibility= View.VISIBLE
        viewHolder.downloadSpeed.visibility= View.VISIBLE
        viewHolder.status.visibility= View.VISIBLE
        viewHolder.progressText.visibility = View.VISIBLE
        viewHolder.remainingText.visibility = View.VISIBLE
        viewHolder.actionButton.visibility =View.VISIBLE
        viewHolder.icon.setImageResource(R.drawable.ic_baseline_file_download_24)

        if(curritem.status==Status.COMPLETED){


            viewHolder.progressBar.visibility= View.GONE
            viewHolder.downloadSpeed.visibility= View.GONE
            viewHolder.status.text = getSize(curritem.downloaded)
            viewHolder.progressText.visibility = View.GONE
            viewHolder.remainingText.text = URL(curritem.url).host
            viewHolder.actionButton.visibility =View.GONE
            viewHolder.icon.setImageResource(R.drawable.ic_baseline_file_download_done_24)
//
        }

        if(curritem.status==Status.DOWNLOADING)
        {
          val x= getMapdata(curritem.id)


            viewHolder.progressBar.progress= curritem.progress
            viewHolder.downloadSpeed.text= x?.speed?.let { getSpeed(it) }
            viewHolder.remainingText.text = x?.eta?.let { getETA(it) }
            viewHolder.actionButton.text="Pause"
            viewHolder.status.text= "Downloading"

        }

        if(curritem.status==Status.PAUSED){

            viewHolder.progressBar.progress = curritem.progress
            viewHolder.downloadSpeed.text=""
            viewHolder.remainingText.text =""
            viewHolder.actionButton.text="Resume"
            viewHolder.status.text= "Paused"


        }

        if(curritem.status== Status.FAILED){

            viewHolder.progressBar.visibility= View.GONE
            viewHolder.downloadSpeed.visibility= View.GONE
            viewHolder.status.text = "Failed"
            viewHolder.progressText.visibility = View.GONE
            viewHolder.remainingText.text = URL(curritem.url).host
            viewHolder.actionButton.visibility =View.GONE
            viewHolder.icon.setImageResource(R.drawable.ic_baseline_file_download_off_24)
        }

        if(curritem.status == Status.ADDED|| curritem.status == Status.NONE||curritem.status == Status.QUEUED){
            viewHolder.remainingText.text ="Undefined"
            viewHolder.actionButton.text="Pause"

        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array:ArrayList<Download>){
        item.clear()
        item.addAll(array)


        notifyDataSetChanged()

    }

    fun getSpeed(bytes:Long):String{
        val size_bytes = bytes
        var cnt_size: String=""

        val size_kb = size_bytes / 1024
        val size_mb = size_kb / 1024
        val size_gb = size_mb / 1024

        cnt_size = if (size_gb > 0) {
            "$size_gb GB"
        } else if (size_mb > 0) {
            "$size_mb MB"
        } else {
            "$size_kb KB"
        }
        return cnt_size+"/s"
    }
    fun getSize(bytes:Long):String{
        val size_bytes = bytes
        var cnt_size: String=""

        val size_kb = size_bytes / 1024
        val size_mb = size_kb / 1024
        val size_gb = size_mb / 1024

        cnt_size = if (size_gb > 0) {
            "$size_gb GB"
        } else if (size_mb > 0) {
            "$size_mb MB"
        } else {
            "$size_kb KB"
        }
        return cnt_size
    }

    fun getETA(time:Long):String{
        val str =String.format("%d min, %d sec",
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );

        return str;
    }



    fun getName(url:String):String{
        val fileName: String = URL(url).getFile()
        return fileName.substring(fileName.lastIndexOf('/') + 1)

    }

    fun updateHelperMap(id: Int, x: DownloadHelper){
        helperMap.put(id,x);
    }

    fun getMapdata(id:Int): DownloadHelper?{
        if(helperMap.containsKey(id))
            return helperMap[id]!!;
        else
            return null

    }




}
