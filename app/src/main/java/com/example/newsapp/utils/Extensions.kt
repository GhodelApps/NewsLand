package com.example.newsapp.utils

import android.text.format.DateUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.libnews.models.Article
import com.example.newsapp.ui.Resource
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun TextView.formatDate(article: Article) {
//    val pattern = when (article.source.id) {
//        "bbc-news" -> "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"
//        "cnn" -> "yyyy-MM-dd'T'HH:mm:ss.SSSSS'Z'"
//        "al-jazeera-english" -> "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"
//        else -> "yyyy-MM-dd'T'HH:mm:ss'Z'"
//    }

    val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val timeStringToBeParsed = article.publishedAt.subSequence(0, 19).toString() + "Z"

    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    val parsedDate: Date? = simpleDateFormat.parse(timeStringToBeParsed)
    val time: Long = parsedDate?.time!!
    // this wil give difference in string with ago added
    val timeAgo =
        DateUtils.getRelativeTimeSpanString(
            time,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        )
    this.text = timeAgo
}

fun View.showSnackBar(message: String, retryFun: (() -> Unit)? = null) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun Fragment.handleApiError(failure: Resource.Failure) {
    when {
        failure.errorCode == 401 -> requireView().showSnackBar("Unauthorized request")
        failure.isNetworkError == true -> requireView().showSnackBar("Please check your network")
        else -> requireView().showSnackBar(failure.message ?: "Something went wrong")
    }
}