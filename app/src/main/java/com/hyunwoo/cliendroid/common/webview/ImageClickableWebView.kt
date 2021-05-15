package com.hyunwoo.cliendroid.common.webview

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.webkit.WebView
import java.util.Calendar

/**
 * 이미지 클릭시 해당 이미지의 url을 반환해주기 위해서 만든 웹뷰
 */
class ImageClickableWebView : WebView {
    private var startClickTime: Long = 0L
    private var listener: ((String) -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun setImageClickListener(listener: (String) -> Unit) {
        this.listener = listener
    }

    private fun init() {
        setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startClickTime = Calendar.getInstance().timeInMillis
                }
                MotionEvent.ACTION_UP -> {
                    val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                    if (clickDuration < MAX_CLICK_DURATION) {
                        performClick()
                    }
                }
            }
            false
        }

        setOnClickListener {
            if (hitTestResult.type == IMAGE_TYPE) {
                hitTestResult.extra?.let { link ->
                    listener?.let { it(link) }
                }
            }
        }
    }

    companion object {
        private const val MAX_CLICK_DURATION = 200
        private const val IMAGE_TYPE = 5
    }
}
