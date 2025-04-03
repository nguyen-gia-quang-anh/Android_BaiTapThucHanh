package com.example.th5_b3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import java.lang.reflect.Method

class CallBlocker : BroadcastReceiver() {
    private val blockedNumbers = mutableListOf<String>()

    fun addBlockedNumber(number: String) {
        blockedNumbers.add(number)
    }

    fun removeBlockedNumber(number: String) {
        blockedNumbers.remove(number)
    }

    fun getBlockedNumbers(): List<String> {
        return blockedNumbers.toList()
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                val phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                if (phoneNumber != null && isBlocked(phoneNumber)) {
                    Log.d("CallBlocker", "Blocked call from $phoneNumber")
                    Toast.makeText(context, "Blocking call from $phoneNumber", Toast.LENGTH_SHORT).show()

                    try {
                        // This is a reflection approach which might not work on all devices
                        val telephonyService = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                        val telephonyClass = Class.forName(telephonyService.javaClass.name)
                        val methodEndCall = telephonyClass.getDeclaredMethod("endCall")
                        methodEndCall.isAccessible = true
                        methodEndCall.invoke(telephonyService)
                    } catch (e: Exception) {
                        Log.e("CallBlocker", "Could not end call", e)
                    }
                }
            }
        }
    }

    private fun isBlocked(phoneNumber: String): Boolean {
        return blockedNumbers.any { phoneNumber.contains(it) }
    }
}