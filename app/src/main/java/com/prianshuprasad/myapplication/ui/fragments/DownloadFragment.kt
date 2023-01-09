package com.prianshuprasad.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prianshuprasad.myapplication.utils.browser.Browser
import com.prianshuprasad.myapplication.DownloadAdapter
import com.prianshuprasad.myapplication.ui.activity.MainActivity2
import com.prianshuprasad.myapplication.R
import com.prianshuprasad.myapplication.utils.Database.downloadDataBase.DownloadDataviewholder
import com.prianshuprasad.myapplication.utils.Database.downloadDataBase.DownloadHelper
//import com.prianshuprasad.myapplication.Database.downloadDataBase.DownloadDataviewholder
//import com.prianshuprasad.myapplication.Database.downloadDataBase.DownloadHelper
import com.tonyodev.fetch2.Download
import com.tonyodev.fetch2.Error
import com.tonyodev.fetch2.FetchListener
import com.tonyodev.fetch2core.DownloadBlock


class DownloadFragment(listener: MainActivity2, browser: Browser, downloadDataviewholder: DownloadDataviewholder) : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }



    val activeList:ArrayList<Int> = ArrayList()
    val listener= listener
    public var adapter:DownloadAdapter?= null
    val browser= browser
    val downloadDataviewholder = downloadDataviewholder

    var contextMenuId:Int=0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        listener.getDownloads()
        val binding= inflater.inflate(R.layout.fragment_download, container, false)

        val toolbar = binding.findViewById<Toolbar>(R.id.toolbar)

        toolbar.setNavigationOnClickListener {
            listener.onBackPressed()
        }

        adapter = DownloadAdapter(this)
        val rcview= binding.findViewById<RecyclerView>(R.id.download_rcview)

        rcview.layoutManager = LinearLayoutManager(requireContext())
        rcview.adapter= adapter


        listener.fetch?.getDownloads {

            browser.downloadArrayList = it as ArrayList<Download>
           adapter?.update(browser.downloadArrayList)
        }


        val fetchListener: FetchListener = object : FetchListener {
            override fun onQueued(download: Download, waitingOnNetwork: Boolean) {
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }



            }

            override fun onCompleted(download: Download) {

                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }

            }


            override fun onProgress(
                download: Download,
                etaInMilliSeconds: Long,
                downloadedBytesPerSecond: Long,
            ) {

                adapter?.updateHelperMap(download.id, DownloadHelper(etaInMilliSeconds,downloadedBytesPerSecond))
//                Toast.makeText(listener,"$downloadedBytesPerSecond  ${download.downloadedBytesPerSecond}",Toast.LENGTH_SHORT).show()
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }




            }

            override fun onPaused(download: Download) {

                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }




            }
            override fun onResumed(download: Download) {

                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }


            }
            override fun onStarted(
                download: Download,
                downloadBlocks: List<DownloadBlock>,
                totalBlocks: Int,
            ) {
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }


            }

            override fun onWaitingNetwork(download: Download) {
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }


            }

            override fun onAdded(download: Download) {
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }


            }

            override fun onCancelled(download: Download) {
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }

          }
            override fun onRemoved(download: Download) {
                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }

            }
            override fun onDeleted(download: Download) {

                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }



            }
            override fun onDownloadBlockUpdated(
                download: Download,
                downloadBlock: DownloadBlock,
                totalBlocks: Int,
            ) {

                listener.fetch?.getDownloads {

                    browser.downloadArrayList = it as ArrayList<Download>
                    adapter?.update(browser.downloadArrayList)
                }

            }

            override fun onError(download: Download, error: Error, throwable: Throwable?) {
                for(i in 0..browser.downloadList.size-1){
                    if(download.id== browser.downloadList[i].id){

                        browser.downloadList[i].status = "Failed"

                        if(activeList.contains(browser.downloadList[i].id))
                        {
                            activeList.remove(browser.downloadList[i].id)
                        }

                        downloadDataviewholder.insertnote(browser.downloadList[i])

                    }
                }


            }
        }

        listener.fetch?.addListener(fetchListener)

        return  binding.rootView
    }

    fun pause(id:Int){

        listener.fetch?.pause(id)
    }

    fun resume(id:Int){
        listener.fetch?.resume(id)

    }

    fun delete(){

        delete(contextMenuId)
    }

    fun delete(id:Int){

        listener?.fetch?.delete(id)


    }




    fun openFile( index:Int) {

        listener.openFile(index)
        return
//
//        val intent = Intent()
//        intent.action = Intent.ACTION_VIEW
//        val mime = requireContext().contentResolver.getType(browser.downloadArrayList[index].fileUri)
//        intent.setDataAndType(browser.downloadArrayList[index].fileUri, mime)
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        requireContext().startActivity(intent)


    }




}