package com.android.jiolauncherlibrary.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.jiolauncherlibrary.delegates.SetAppStatus

class LauncherStatusReceiver(private val setAppStatus: SetAppStatus) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Action " + intent.action, Toast.LENGTH_LONG).show()
        setAppStatus.setAppStatus(intent.action)
    }
}