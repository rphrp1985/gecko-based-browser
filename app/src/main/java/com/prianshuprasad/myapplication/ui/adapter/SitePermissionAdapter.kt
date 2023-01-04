package com.prianshuprasad.myapplication.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.ui.fragments.SiteNotificationFragment
import com.prianshuprasad.myapplication.utils.Database.siteDatabase.SiteData

//import com.prianshuprasad.myapplication.Database.siteDatabase.SiteData

class SitePermissionAdapter(private val listener: SiteNotificationFragment, mode:String) :
    RecyclerView.Adapter<SitePermissionAdapter.ViewHolder>() {


    private val item: ArrayList<SiteData> = ArrayList()
    val mode= mode

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var switch :SwitchCompat

        init {

       switch= view.findViewById(R.id.notification_site_name)



        }




    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_site_notification_list, viewGroup, false)

        val viewHolder= ViewHolder(view)
        viewHolder.switch.setOnClickListener {

            when(mode) {
                "Notification"-> {
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permNoti==1)
                    if(item[viewHolder.adapterPosition].permNoti==1)
                        item[viewHolder.adapterPosition].permNoti =0
                    else
                        item[viewHolder.adapterPosition].permNoti= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])
                }
                "Location"-> {
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permLocation==1)
                    if(item[viewHolder.adapterPosition].permLocation==1)
                        item[viewHolder.adapterPosition].permLocation =0
                    else
                        item[viewHolder.adapterPosition].permLocation= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])
                }
                "Media Autoplay"-> {
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permMedia==1)
                    if(item[viewHolder.adapterPosition].permMedia==1)
                        item[viewHolder.adapterPosition].permMedia =0
                    else
                        item[viewHolder.adapterPosition].permMedia= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])
                }
                "Camera"->{
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permCamera==1)
                    if(item[viewHolder.adapterPosition].permCamera==1)
                        item[viewHolder.adapterPosition].permCamera =0
                    else
                        item[viewHolder.adapterPosition].permCamera= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])

                }
                "Cookies"->{
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permCokies==1)
                    if(item[viewHolder.adapterPosition].permCokies==1)
                        item[viewHolder.adapterPosition].permCokies =0
                    else
                        item[viewHolder.adapterPosition].permCokies= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])

                }

                "Microphone"->{
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permMicrophone==1)
                    if(item[viewHolder.adapterPosition].permMicrophone==1)
                        item[viewHolder.adapterPosition].permMicrophone =0
                    else
                        item[viewHolder.adapterPosition].permMicrophone= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])

                }
                "Storage"->{
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permStorage==1)
                    if(item[viewHolder.adapterPosition].permStorage==1)
                        item[viewHolder.adapterPosition].permStorage =0
                    else
                        item[viewHolder.adapterPosition].permStorage= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])

                }
                "JavaScript"->{
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permJavaScript==1)
                    if(item[viewHolder.adapterPosition].permJavaScript==1)
                        item[viewHolder.adapterPosition].permJavaScript =0
                    else
                        item[viewHolder.adapterPosition].permJavaScript= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])

                }
                "Desktop Mode"->{
                    viewHolder.switch.isChecked = !(item[viewHolder.adapterPosition].permDesktop==1)
                    if(item[viewHolder.adapterPosition].permDesktop==1)
                        item[viewHolder.adapterPosition].permDesktop =0
                    else
                        item[viewHolder.adapterPosition].permDesktop= 1;
                    listener.updateNotificationSettings(item[viewHolder.adapterPosition])

                }



            }



        }





        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {


        val curritem = item[position]

    viewHolder.switch.text = curritem.coreAdress
        when(mode) {
          "Notification"->  viewHolder.switch.isChecked = curritem.permNoti == 1
            "Location"->  viewHolder.switch.isChecked = curritem.permLocation == 1
           "Camera"->    viewHolder.switch.isChecked = curritem.permCamera == 1
            "Microphone"-> viewHolder.switch.isChecked = curritem.permMicrophone == 1
            "Media Autoplay"-> viewHolder.switch.isChecked = curritem.permMedia == 1
            "Storage"-> viewHolder.switch.isChecked = curritem.permStorage == 1
            "Cookies"-> viewHolder.switch.isChecked = curritem.permCokies == 1
            "JavaScript"-> viewHolder.switch.isChecked = curritem.permJavaScript == 1
            "Desktop Mode"-> viewHolder.switch.isChecked = curritem.permDesktop == 1

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = item.size


    fun update(array:ArrayList<SiteData>){

        item.clear()
        item.addAll(array)

        notifyDataSetChanged()

    }





}
