package com.example.broadcastrecieverdemo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.CallLog
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class MissedCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        if (state == TelephonyManager.EXTRA_STATE_IDLE) {
            val number = getLastMissedCallNumber(context)
            if (number != null) {
                val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val message = sharedPreferences.getString("sms_message", "Sorry, I missed your call.")
                sendSms(number, message ?: "Sorry, I missed your call.")
            }
        }
    }

    @SuppressLint("Range")
    private fun getLastMissedCallNumber(context: Context): String? {
        val cursor = context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(CallLog.Calls.NUMBER),
            "${CallLog.Calls.TYPE} = ? AND ${CallLog.Calls.NEW} = ?",
            arrayOf(CallLog.Calls.MISSED_TYPE.toString(), "1"),
            "${CallLog.Calls.DATE} DESC"
        )
        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(it.getColumnIndex(CallLog.Calls.NUMBER))
            }
        }
        return null
    }

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception) {
            Log.e("MissedCallReceiver", "Failed to send SMS", e)
        }
    }
}