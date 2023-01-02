package com.prianshuprasad.myapplication.adapter
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.MainActivity2
import com.prianshuprasad.myapplication.R
import org.mozilla.geckoview.GeckoResult
import org.mozilla.geckoview.GeckoSession

class ChoicePromptAdapter(
    private val listener: MainActivity2,
    prompt: GeckoSession.PromptDelegate.ChoicePrompt,
    response: GeckoResult<GeckoSession.PromptDelegate.PromptResponse>,
    dlg: AlertDialog
) :
    RecyclerView.Adapter<ChoicePromptAdapter.ViewHolder>() {

     val prompt= prompt
    val response= response
    val dlg = dlg
    private val item: ArrayList<GeckoSession.PromptDelegate.ChoicePrompt.Choice> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var label:TextView
        var weburl:TextView
        var deleteHistory: ImageView



        init {

            label = view.findViewById(R.id.webName)
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


        view.setOnClickListener {

             response.complete(prompt.confirm(item[viewHolder.adapterPosition].id))
               dlg.cancel()
//            listener.open(item[viewHolder.adapterPosition].coreAdress)
        }



        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        val curritem = item[position]



        viewHolder.deleteHistory.visibility= View.GONE
        viewHolder.weburl.visibility= View.GONE
        viewHolder.label.text = curritem.label

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array: Array<GeckoSession.PromptDelegate.ChoicePrompt.Choice>){

        item.clear()
        item.addAll(array)

        notifyDataSetChanged()

    }





}
