package zechs.takesurvey.utils

import zechs.takesurvey.utils.Constants.Companion.TAKESURVEY
import java.net.URL

fun extractIdFromUrl(url: String): String? {
    val domain = URL(TAKESURVEY).host
    val pattern = Regex("^(http|https)://${domain}/([A-Za-z0-9]{24})(/results)?$")
    val matchResult = pattern.matchEntire(url)
    return matchResult?.groupValues?.get(2)
}


