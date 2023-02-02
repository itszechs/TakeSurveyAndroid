package zechs.takesurvey.utils

import android.util.Log
import android.view.View
import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import zechs.takesurvey.ui.attempt.AttemptFragment
import zechs.takesurvey.utils.Constants.Companion.TAKESURVEY
import java.net.URL

fun extractIdFromUrl(url: String): String? {
    val domain = URL(TAKESURVEY).host
    val pattern = Regex("^(http|https)://${domain}/([A-Za-z0-9]{24})(/results)?$")
    val matchResult = pattern.matchEntire(url)
    return matchResult?.groupValues?.get(2)
}

@Keep
internal data class SnackBarAction(
    @StringRes val resId: Int,
    val listener: View.OnClickListener
)

internal fun showSnackBar(
    root: View,
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    action: SnackBarAction? = null
) {
    Log.i(AttemptFragment.TAG, "Showing snackbar: $message")

    Snackbar.make(
        root,
        message,
        Snackbar.LENGTH_SHORT
    ).also {
        action?.let { a ->
            it.setAction(a.resId, a.listener)
        }
        it.duration = duration
    }.show()
}

