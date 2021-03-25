package com.android.jiolauncherlibrary.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.util.Log
import com.android.jiolauncherlibrary.data.JioLauncherDetails
import com.android.jiolauncherlibrary.delegates.SetAppListData
import java.util.*

class AppAsyncTask(
        @field:SuppressLint("StaticFieldLeak") private val mContext: Context,
        private val setAppListData: SetAppListData,
        private val searchApp: String
) :
    AsyncTask<Void?, ArrayList<JioLauncherDetails?>?, ArrayList<JioLauncherDetails>?>() {
    override fun doInBackground(objects: Array<Void?>): ArrayList<JioLauncherDetails>? {
        try {
            return listOfApps
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return null
    }

    @get:Throws(PackageManager.NameNotFoundException::class)
    private val listOfApps: ArrayList<JioLauncherDetails>
        get() {
            val pm = mContext.packageManager
            val appsList: ArrayList<JioLauncherDetails> = ArrayList<JioLauncherDetails>()
            val i = Intent(Intent.ACTION_MAIN, null)
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            val allApps = pm.queryIntentActivities(i, 0)
            for (ri in allApps) {
                val app = JioLauncherDetails()
                if (searchApp.isEmpty() || ri.loadLabel(pm).contains(searchApp, true)){
                    app.appName = ri.loadLabel(pm)
                    app.packageName = ri.activityInfo.packageName
                    app.appIcon = ri.activityInfo.loadIcon(pm)
                    app.mainActivity = ri.activityInfo.name
                    app.versionName = pm.getPackageInfo(ri.activityInfo.packageName, 0).versionName
                    app.versionCode = pm.getPackageInfo(ri.activityInfo.packageName, 0).versionCode
                    appsList.add(app)
                }
            }
            return appsList
        }

    override fun onPostExecute(`object`: ArrayList<JioLauncherDetails>?) {
        super.onPostExecute(`object`)
        if (`object` != null) {
            setAppListData.setAppListData(`object`)
        }
    }

}

