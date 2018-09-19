package com.android.technicalandroidnewsfeedstest.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.technicalandroidnewsfeedstest.R
import com.android.technicalandroidnewsfeedstest.utils.LogUtils

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        try {
            val actionBar = supportActionBar
            actionBar!!.hide()
        }
        catch (e:Exception){
            LogUtils.getInstance().logError("Error: ", "Error occured while hiding the titlebar")
        }
        finally {
            navigateToLoginScreen()
        }
    }

    /**
     * do Navigation after 2 seconds to Home Activity
     */
    private fun navigateToLoginScreen() {
        try {
            val timerThread = object : Thread() {
                override fun run() {
                    try {
                        Thread.sleep(2000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        startActivity(Intent(this@SplashActivity, NewsFeedActivity::class.java))
                    }
                }
            }
            timerThread.start()
        }
        catch (e:Exception){
            e.toString()
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
