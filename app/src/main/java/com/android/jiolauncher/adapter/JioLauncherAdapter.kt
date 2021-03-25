package com.android.jiolauncher.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.jiolauncher.R
import com.android.jiolauncherlibrary.data.JioLauncherDetails

class JioLauncherAdapter(jioLauncherDetails: ArrayList<JioLauncherDetails>) : RecyclerView.Adapter<JioLauncherAdapter.ViewHolder>() {
    private var jioLaunchers: MutableList<JioLauncherDetails> = jioLauncherDetails

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.app_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return jioLaunchers.size
    }

    //Assign values to created view
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val jioLauncherDetails: JioLauncherDetails = jioLaunchers[i]
        viewHolder.appName.text = "App Name : " + jioLauncherDetails.appName
        viewHolder.imgIcon.setImageDrawable(jioLauncherDetails.appIcon)
        viewHolder.tvPackageName.text = "Package Name : " + jioLauncherDetails.packageName
        viewHolder.tvVersionName.text = "Version Name : " + jioLauncherDetails.versionName
        viewHolder.tvVersionCode.text = "Version Code : " + jioLauncherDetails.versionCode
        viewHolder.tvLauncherName.text = "Main Activity : " + jioLauncherDetails.mainActivity
    }

    //View Holder
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {
        var appName: TextView = itemView.findViewById(R.id.tvAppName)
        var imgIcon: ImageView = itemView.findViewById(R.id.imgAppIcon) as ImageView
        var tvPackageName: TextView = itemView.findViewById(R.id.tvPackageName)
        var tvVersionName: TextView = itemView.findViewById(R.id.tvVersionDetail);
        var tvVersionCode: TextView = itemView.findViewById(R.id.tvVersionCode)
        var tvLauncherName: TextView = itemView.findViewById(R.id.tvJioLauncher)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            val context: Context = view.context
            val launchIntent: Intent = context.getPackageManager()
                    .getLaunchIntentForPackage(jioLaunchers[position].packageName.toString())!!
            context.startActivity(launchIntent)
        }
    }
}