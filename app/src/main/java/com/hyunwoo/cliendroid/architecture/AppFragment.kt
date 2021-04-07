package com.hyunwoo.cliendroid.architecture

import android.content.Context
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

open class AppFragment: Fragment(), HasAndroidInjector, MavericksView {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any>? = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun invalidate() {
        // do nothing
    }
}
