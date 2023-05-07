package pl.birski.mvvmrecipeapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

object DateUtil {
    private const val dashPattern = "dd-MM-yyyy"
    private const val slashPattern = "dd/MM/yyyy"

    @SuppressLint("SimpleDateFormat")
    fun dateToSlashText(date: Date): String = SimpleDateFormat(slashPattern).format(date)

    @SuppressLint("SimpleDateFormat")
    fun dateToDashText(date: Date): String = SimpleDateFormat(dashPattern).format(date)

    fun longToDate(long: Long) = Date(long)

    fun dateToLongInSecs(date: Date) = date.time / 1000

    fun createTimestamp() = Date()
}
