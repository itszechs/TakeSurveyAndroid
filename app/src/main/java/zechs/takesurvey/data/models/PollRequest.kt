package zechs.takesurvey.data.models

import androidx.annotation.Keep

@Keep
data class PollRequest(
    val title: String,
    val options: List<String>
)
