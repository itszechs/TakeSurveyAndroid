package zechs.takesurvey.data.models

import androidx.annotation.Keep

@Keep
data class Option(
    val title: String,
    val vote: Int
)