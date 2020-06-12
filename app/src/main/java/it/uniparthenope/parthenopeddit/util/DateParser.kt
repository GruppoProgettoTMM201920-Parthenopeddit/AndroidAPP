package it.uniparthenope.parthenopeddit.util

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.text.format.DateUtils
import java.util.*

class DateParser {
    companion object {
        private val cal: Calendar
            get() = Calendar.getInstance()

        private val locale: Locale
            get() = Locale.getDefault()

        private val tz: TimeZone by lazy {
            cal.getTimeZone()
        }

        private val sdf: SimpleDateFormat by lazy {
            val temp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
            temp.timeZone = tz;
            temp
        }
//
        //fun getNow(): String {
//
        //}
//
        fun prettyParse(timestamp: String): String {
            lateinit var res: String
            try {
                res = DateUtils.getRelativeTimeSpanString(sdf.parse(timestamp).time, cal.timeInMillis,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE
                ).toString()
            } catch (e: Exception) {
                return timestamp
            }
            return res
        }
    }
}