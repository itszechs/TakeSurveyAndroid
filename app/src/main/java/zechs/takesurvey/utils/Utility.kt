package zechs.takesurvey.utils

import zechs.takesurvey.utils.Constants.Companion.TAKESURVEY_API

fun extractIdFromUrl(url: String): String? {
    val pattern = Regex("^$TAKESURVEY_API/([A-Za-z0-9]{24})/results\$")
    val matchResult = pattern.matchEntire(url)
    return matchResult?.groupValues?.get(1)
}


