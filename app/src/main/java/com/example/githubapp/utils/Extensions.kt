package com.example.githubapp.utils

import android.content.Context
import android.util.TypedValue
import androidx.lifecycle.MutableLiveData
import com.example.githubapp.model.util.LiveDataEvent
import com.example.githubapp.model.util.SafeField
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun <T:Any> MutableLiveData<LiveDataEvent<T>>.postEvent(value: T) {
    postValue(LiveDataEvent(value))
}

// 2016-12-05T16:51:55Z
fun String.parseAsDate() : String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault())
    return try {
        sdf.parse(this)?.let { safeDate ->
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateFormat.format(safeDate)
        } ?: ""
    } catch (e: ParseException){
        ""
    }
}

fun Int.toDp(context: Context) : Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics).toInt()
}

fun SafeField<Boolean>.ifTrue(action: () -> Unit){
    get {
        if (it){
            action.invoke()
        }
    }
}