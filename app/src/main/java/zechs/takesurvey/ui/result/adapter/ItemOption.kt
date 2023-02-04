package zechs.takesurvey.ui.result.adapter

import androidx.annotation.Keep

@Keep
data class ItemOption(
    val title: String,
    val vote: Int,
    val percentage: Int
)