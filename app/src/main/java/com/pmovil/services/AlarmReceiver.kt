package com.pmovil.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        val delayTime = intent?.getIntExtra(AppConstants.EXTRA_DELAY_TIME, 0)
        AppUtils.showNotification(context, "Alarm Notification",
            "Alarm was delayed $delayTime seconds", 100
        )
    }

}