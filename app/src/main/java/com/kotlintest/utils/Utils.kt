package com.kotlintest.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.UrlQuerySanitizer
import android.util.DisplayMetrics
import android.util.TypedValue
import com.kotlintest.KotlinApp
import com.kotlintest.*
import java.util.regex.Pattern

/**
 * Created by alex on 12/28/16.
 */

class Utils {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
    }

    companion object {

        private val TAG = Utils::class.java.simpleName

        fun isValidEmail(target: String?): Boolean {
            if (target == null) {
                return false
            } else {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }

        fun isPasswordValid(password: String?): Boolean {
            if (password == null || password.isEmpty()) {
                return false
            } else {
                val pattern = Pattern.compile(PASSWORD_REGEXP)
                val matcher = pattern.matcher(password)
                return matcher.matches()
            }
            //        if (password.length() < Constants.MAX_PASSWORD_LENGHT) {
            //            return false;
            //        } else {
            //            return true;
            //        }
        }

        fun isPhoneValid(phone: String?): Boolean {
            if (phone == null || phone.isEmpty()) {
                return false
            } else {
                val pattern = Pattern.compile(PHONE_REGEXP)
                val matcher = pattern.matcher(phone)
                return matcher.matches()
            }
        }

        @Synchronized fun isNullOrEmpty(collection: Collection<*>?): Boolean {
            return collection == null || collection.isEmpty()
        }

        fun isNullOrEmpty(string: String?): Boolean {
            return string == null || string.isEmpty()
        }

        fun pxToDp(px: Int): Int {
            val res = KotlinApp.kotlinApp!!.getInstance().getAppContext().resources
            val density = res.displayMetrics.density
            return (px / density).toInt()
        }

        fun dpToPx(dp: Float): Int {
            val metrics = KotlinApp.kotlinApp!!.getAppContext().resources.displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics).toInt()
        }

        fun isLandscape(context: Context): Boolean {
            val orient = context.resources.configuration.orientation
            return orient == Configuration.ORIENTATION_LANDSCAPE
        }

        fun getScreenWidthInDp(context: Context): Float {
            val res = context.resources
            val displayMetrics = res.displayMetrics
            return displayMetrics.widthPixels / displayMetrics.density
        }

        fun getScreenWidthInPixel(context: Context): Int {
            val res = context.resources
            val displayMetrics = res.displayMetrics
            return displayMetrics.widthPixels
        }

        fun getScreenHeightInPixel(context: Context): Int {
            val res = context.resources
            val displayMetrics = res.displayMetrics
            return displayMetrics.heightPixels
        }

        fun convertDpToPixsels(context: Context, widthInDp: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (widthInDp * scale + 0.5).toInt()
        }

        fun convertPixselsToDp(context: Context, widthInPixsels: Int): Int {
            val scale = context.resources.displayMetrics.density
            return (widthInPixsels / scale + 0.5).toInt()
        }

        fun getNextPage(url: String): String? {
            val sanitizer = UrlQuerySanitizer(url)
            if (sanitizer.hasParameter("page")) {
                return sanitizer.getValue("page").toString()
            } else {
                return null
            }
        }

        fun convertDpToPixel(dp: Int): Int {
            val displayMetrics = Resources.getSystem().displayMetrics
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics).toInt()
        }
    }
}
