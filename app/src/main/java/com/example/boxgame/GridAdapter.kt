package com.example.boxgame

import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class BoxItem(
    var title:String,
    var isSelected:Boolean,
    var redCompleted:Boolean,
    var greenCompleted:Boolean,
    var blueCompleted:Boolean,
)

class GridAdapter(var list:ArrayList<BoxItem>, var selectRandom:() -> Unit):RecyclerView.Adapter<GridAdapter.GridViewHolder>() {
    class GridViewHolder(itemView:View):ViewHolder(itemView){
        val mainView = itemView.findViewById<LinearLayout>(R.id.mainView)
        val boxTitle = itemView.findViewById<TextView>(R.id.boxTitle)
    }

    private val mLastClickTime: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.boxTitle.text = "${position + 1}"
        holder.boxTitle.isSelected = list[position].isSelected

        val item = list[position]


        if(!item.isSelected){
            holder.boxTitle.setBackgroundResource(R.drawable.box_background)
        }else{
            if(item.redCompleted){
                holder.boxTitle.setBackgroundResource(R.drawable.box_background_selected)
                if(item.greenCompleted){
                    Log.i("SettingBlue", "Green")
                    holder.boxTitle.setBackgroundResource(R.drawable.box_background_green_selected)
                    if(item.blueCompleted){
                        Log.i("SettingBlue", "Blue")
                        holder.boxTitle.setBackgroundResource(R.drawable.box_background_blue_selected)
                    }
                }else if(item.blueCompleted){
                    Log.i("SettingBlue", "True")
                    holder.boxTitle.setBackgroundResource(R.drawable.box_background_blue_selected)
                }
            }else {
                holder.boxTitle.setBackgroundResource(R.drawable.box_background)
            }
        }

        holder.boxTitle.setOnClickListener {
            if (SystemClock.elapsedRealtime() - mLastClickTime >= 1000) {
                selectRandom()
            }

        }

        Log.i("ItemSelected $position", list[position].isSelected.toString())
    }

    fun setNewList(newList:ArrayList<BoxItem>) {
        list = newList
    }
}