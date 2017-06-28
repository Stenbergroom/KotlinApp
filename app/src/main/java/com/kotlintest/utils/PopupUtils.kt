package com.kotlintest.utils

import android.app.Activity
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.kotlintest.R

/**
 * Created by alex on 12/28/16.
 */

class PopupUtils {

    companion object {

        private val HIDE_PROGRESS_DIALOG_DELAYED = 2000

        private val TOAST_OFFSET_X = 0
        private val TOAST_OFFSET_Y = Utils.pxToDp(150)

        private val dialogProgress: ProgressBar? = null
        private val dialogText: TextView? = null
        private val hideScheduled: Boolean = false

        fun showCustomToast(activity: Activity, stringResId: Int, gravity: Int) {
            showCustomToast(activity, activity.getString(stringResId), gravity)
        }

        fun showCustomToast(activity: Activity, stringResId: Int) {
            showCustomToast(activity, activity.getString(stringResId))
        }

        fun showCustomToast(activity: Activity, message: String, gravity: Int = Gravity.CENTER) {
            if (Utils.isNullOrEmpty(message)) {
                return
            }

            var xOffset = 0
            var yOffset = 0

            when (gravity) {
                Gravity.BOTTOM -> {
                    xOffset = TOAST_OFFSET_X
                    yOffset = TOAST_OFFSET_Y
                }

                Gravity.TOP -> {
                    xOffset = TOAST_OFFSET_X
                    yOffset = -TOAST_OFFSET_Y
                }
            }

            showToast(activity, message, gravity, xOffset, yOffset)
        }

        fun showCustomToast(activity: Activity, message: String, anchor: View?) {
            if (Utils.isNullOrEmpty(message)) {
                return
            }

            var xOffset = 0
            var yOffset = 0

            val anchorRect = Rect()
            if (anchor != null && anchor.getGlobalVisibleRect(anchorRect)) {
                val anchorCenterX = anchorRect.centerX()
                val anchorCenterY = anchorRect.centerY()

                val root = anchor.rootView
                val rootHalfWidth = root.right / 2
                val rootHalfHeight = root.bottom / 2

                yOffset = anchorCenterY - rootHalfHeight
                xOffset = anchorCenterX - rootHalfWidth
            }

            showToast(activity, message, Gravity.CENTER, xOffset, yOffset)
        }

        private fun showToast(activity: Activity, message: String, gravity: Int, xOffset: Int, yOffset: Int) {
            val view = View.inflate(activity, R.layout.view_custom_toast, null)
            val textView = view.findViewById(R.id.notificationText) as TextView
            textView.text = message

            val toast = Toast(activity)
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(gravity, xOffset, yOffset)
            toast.view = view
            toast.show()
        }
    }
}