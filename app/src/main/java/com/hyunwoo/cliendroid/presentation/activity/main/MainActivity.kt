package com.hyunwoo.cliendroid.presentation.activity.main

import android.os.Bundle
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.architecture.AppActivity

class MainActivity : AppActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
