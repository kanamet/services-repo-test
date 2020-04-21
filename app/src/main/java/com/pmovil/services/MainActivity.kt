package com.pmovil.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MessageListener {

    private var mBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(AlarmReceiver(), IntentFilter(AppConstants.ACTION_ALARM))
        registerReceiver(
            ServicesMessagesReceiver(this),
            IntentFilter(AppConstants.ACTION_SERVICE_MESSAGE)
        )
    }

    fun createAlarm(view: View) {
        val delay = if (alarm_delay_time_five_seconds_rb.isChecked) 5 else 10

        val context: Context = this
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(AppConstants.ACTION_ALARM).apply {
            putExtra(AppConstants.EXTRA_DELAY_TIME, delay)
        }


        val pendingIntent =
            PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        // TODO buscar permisos necesarios para ejecutar en background
/*
        val intent2 = Intent(this, MainActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(this, 100, intent2, 0 )

*/
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + delay * 1000,
            pendingIntent
        )

        Toast.makeText(context, "Created Alarm", Toast.LENGTH_SHORT).show()
    }

    private fun getPrimeNumbersMaxValue(): Int {
        return when (prime_numbers_rg.checkedRadioButtonId) {
            R.id.prime_numbers_100k_rb -> 100000
            R.id.prime_numbers_300k_rb -> 300000
            R.id.prime_numbers_600k_rb -> 600000
            R.id.prime_numbers_900k_rb -> 900000
            else -> 1000000
        }
    }

    fun calculatePrimes(view: View) {
        val maxValue = getPrimeNumbersMaxValue()
        var primesCount = 0
        for (i in 1..maxValue) {
            if (AppUtils.isPrime(i)) {
                primesCount++
            }
        }
        prime_numbers_count_tv. text = "$primesCount "
        Log.d("MainActivity", "calculatePrimes: $primesCount ")
    }

    fun calculatePrimesWithRunnable(view: View) {

        val runnable = Runnable {
            val maxValue = getPrimeNumbersMaxValue()
            var primesCount = 0
            for (i in 1..maxValue) {
                if (AppUtils.isPrime(i)) {
                    primesCount++
                }
            }
            prime_numbers_count_tv. text = "$primesCount "
        }

        Thread(runnable).start()

    }

    fun startIntentService(view: View) {
        val intent = Intent(this, PrimesIntentService::class.java).apply {
            putExtra(AppConstants.EXTRA_MAX_VALUE, getPrimeNumbersMaxValue())
        }

        startService(intent)
    }

    fun startService(view: View) {
        val intent = Intent(this, PrimesService::class.java).apply {
            putExtra(AppConstants.EXTRA_MAX_VALUE, getPrimeNumbersMaxValue())
        }

        startService(intent)
    }

    fun startServiceWithThread(view: View) {
        // TODO Implement method
    }

    fun startForegroundService(view: View) {
        // TODO Implement method
    }

    override fun onMessageReceived(intent: Intent) {
        val serviceName = intent.getStringExtra(AppConstants.EXTRA_SERVICE_NAME)
        val serviceStatus = intent.getStringExtra(AppConstants.EXTRA_SERVICE_STATUS)
        Toast.makeText(this, "$serviceName: $serviceStatus", Toast.LENGTH_SHORT).show()
    }

    fun bindService(view: View) {
        // TODO Implement method
    }

    fun unbindService(view: View) {
        // TODO Implement method
    }

    fun bindServiceFunctions(view: View) {
        if (mBound) {
            when (view.id) {
                R.id.bind_function_primes -> {
                    // TODO Implement method
                }
                R.id.bind_function_random -> {
                    // TODO Implement method
                }
            }
        } else {
            Toast.makeText(this, "Service is not bounded", Toast.LENGTH_SHORT).show()
        }
    }
}