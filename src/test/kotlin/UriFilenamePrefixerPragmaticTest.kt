import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UriFilenamePrefixerPragmaticTest {

    @Test
    fun `it should prefix the reference`() {
        val output = pragmaticPrefixer("my_prefix", "doc.html")

        assertEquals("my_doc.html", output)
    }

    @Test
    @DisplayName("unconsidered edge case: anchored reference")
    fun `it does not consider an anchored reference`() {
        val output = pragmaticPrefixer("foo_bar", "doc.html#anchor")

        assertEquals("foo_doc.html", output)
    }

    @Test
    @DisplayName("unconsidered edge case: relative same-dir reference")
    fun `it does not consider relative same-dir reference`() {
        val output = pragmaticPrefixer("lorem_ipsum", "./doc.html")

        assertEquals("lorem_./doc.html", output)
    }

    @Test
    @DisplayName("unconsidered edge case: absolute URLs")
    fun `it does not consider absolute URLs`() {
        val output = pragmaticPrefixer("a_b", "https://example.org/doc.html")

        assertEquals("a_https://example.org/doc.html", output)
    }

    @Test
    @DisplayName("unconsidered edge case: javascript:-URLs")
    fun `it does not consider javascript-scheme URLs`() {
        val output = pragmaticPrefixer("c_d", "javascript:alert('foo.html')")

        assertEquals("c_javascript:alert('foo.html", output)
    }

    @Test
    @DisplayName("unconsidered edge case: invalid URIs")
    fun `it does not consider invalid URIs`() {
        val output = pragmaticPrefixer("e_f", "doc<3.html")

        assertEquals("e_doc<3.html", output)
    }

    private fun pragmaticPrefixer(prefix: String, reference: String): String {
        val index = reference.indexOf(".html", 0, true)
        val fileName = reference.substring(0, index + 5)

        return prefix.replaceAfterLast("_", fileName)
    }
}