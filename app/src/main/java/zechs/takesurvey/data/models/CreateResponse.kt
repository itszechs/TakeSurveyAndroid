package zechs.takesurvey.data.models

import androidx.annotation.Keep

@Keep
data class CreateResponse(
    val id: String,
    val message: String
)