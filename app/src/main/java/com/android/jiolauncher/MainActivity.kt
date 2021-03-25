package com.android.jiolauncher

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.jiolauncher.adapter.JioLauncherAdapter
import com.android.jiolauncherlibrary.services.AppAsyncTask
import com.android.jiolauncherlibrary.data.JioLauncherDetails
import com.android.jiolauncherlibrary.services.LauncherStatusReceiver
import com.android.jiolauncherlibrary.delegates.SetAppListData
import com.android.jiolauncherlibrary.delegates.SetAppStatus
import java.util.*

class MainActivity : AppCompatActivity() {
    private var searchApps: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchApps = findViewById<EditText>(R.id.searchApps)

        registerAppReceiver()
        getAppsList()

        searchApps?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getAppsList()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })
    }

    private fun initAdapter(appInfoArrayList: ArrayList<JioLauncherDetails>) {
        val appListAdapter = JioLauncherAdapter(appInfoArrayList);
        val rvAppList: RecyclerView = findViewById(R.id.rvAppList)
        rvAppList.layoutManager = LinearLayoutManager(this)
        rvAppList.adapter = appListAdapter
    }

    private var appStatusReceiver = LauncherStatusReceiver(SetAppStatus {
        if (it.isNotEmpty()) {
            getAppsList()
        }
    })

    private fun registerAppReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addDataScheme("package")
        registerReceiver(appStatusReceiver, intentFilter)
    }

    private fun getAppsList() {
        AppAsyncTask(this, SetAppListData { appInfoArrayList ->
            if (appInfoArrayList != null) {
                initAdapter(appInfoArrayList)
            }
        }, searchApps?.text.toString()).execute()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(appStatusReceiver)
    }
}