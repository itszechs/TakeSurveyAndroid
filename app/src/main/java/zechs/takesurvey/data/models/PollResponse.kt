package zechs.takesurvey.data.models

import androidx.annotation.Keep

@Keep
data class PollResponse(
    val _id: String,
    val options: List<Option>,
    val title: String
)