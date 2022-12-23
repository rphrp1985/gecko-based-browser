package com.prianshuprasad.myapplication.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.*
import com.prianshuprasad.myapplication.autocompleteDatabase.AutoCompleteData
import com.prianshuprasad.myapplication.autocompleteDatabase.AutocompleteDataviewholder

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewLoginsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewLoginsFragment(listner:MainActivity2,browser:Browser) : Fragment() {

    public var Mode:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }
    }
    val listner= listner
    val  browser= browser
    private lateinit var viewLLoginAdapter:ViewLLoginAdapter
    private val arr:ArrayList<AutoCompleteData> = ArrayList()
    private lateinit var autocompleteDataviewholder:AutocompleteDataviewholder

    private lateinit var rcview:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding= inflater.inflate(R.layout.fragment_view_logins, container, false)

        viewLLoginAdapter= ViewLLoginAdapter(this)
        rcview= binding.findViewById(R.id.view_login_rcview)
        rcview.adapter= viewLLoginAdapter
        rcview.layoutManager= LinearLayoutManager(requireContext())
        autocompleteDataviewholder= AutocompleteDataviewholder(App())

        val toolbar= binding.findViewById<Toolbar>(R.id.toolbar)
toolbar.title= "Saved ${Mode}"
        toolbar.setNavigationOnClickListener {
            listner.onBackPressed()
        }


        autocompleteDataviewholder.allnotes.observeForever {
            arr.clear()
            for( data in browser.autocompleteList){
                if(data.type.equals(Mode))
                arr.add(data)

            }

            viewLLoginAdapter.update(arr)

        }



        return  binding.rootView
    }
    var position=-1;
    fun showPassword(pos:Int){
        position= pos


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            listner.BiometricsMode="View_Password"
            listner.BiometricHandler()
        }


    }

    fun onAuthSccess(){

        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(listner)
        alertDialogBuilder.setMessage("Username : ${arr[position].username} \n" +
                "Password : ${arr[position].password}")

        alertDialogBuilder.setTitle("Alert !")
        alertDialogBuilder.setCancelable(true)

        alertDialogBuilder
            .setCancelable(true)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener{ dialog, id ->


                })


        val alertDialog: AlertDialog = alertDialogBuilder.create()


        alertDialog.show()

    }

    fun delete(pos:Int){

        autocompleteDataviewholder.deletenote(arr[pos])
    }



}