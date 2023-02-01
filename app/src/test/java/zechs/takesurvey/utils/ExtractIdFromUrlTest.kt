package zechs.takesurvey.utils

import org.junit.Assert
import org.junit.Test


internal class ExtractIdFromUrlTest {

    @Test
    fun `extracts the ID from a valid URL`() {
        val url = "https://takesurvey.vercel.app/63d6cbd5651e8c29710f29a3/results"
        val expectedId = "63d6cbd5651e8c29710f29a3"
        val extractedId = extractIdFromUrl(url)
        Assert.assertEquals(expectedId, extractedId)
    }

    @Test
    fun `returns null for an invalid URL`() {
        val url = "https://takesurvey.vercel.app/invalid/results"
        val extractedId = extractIdFromUrl(url)
        Assert.assertNull(extractedId)
    }

    @Test
    fun `returns null for an URL with a different domain name`() {
        val url = "https://otherdomain.com/63d6cbd5651e8c29710f29a3/results"
        val extractedId = extractIdFromUrl(url)
        Assert.assertNull(extractedId)
    }

    @Test
    fun `returns null for an URL with a different path`() {
        val url = "https://takesurvey.vercel.app/63d6cbd5651e8c29710f29a3"
        val extractedId = extractIdFromUrl(url)
        Assert.assertNull(extractedId)
    }

    @Test
    fun `returns null for an URL with a different scheme`() {
        val url = "http://takesurvey.vercel.app/63d6cbd5651e8c29710f29a3/results"
        val extractedId = extractIdFromUrl(url)
        Assert.assertNull(extractedId)
    }
}
