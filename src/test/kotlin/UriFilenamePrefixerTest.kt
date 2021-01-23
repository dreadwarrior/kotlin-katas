import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals

class UriFilenamePrefixerTest {

    @ParameterizedTest
    @ValueSource(strings = ["doc.html", "./doc.html"])
    fun `it should prefix relative, same-dir references`(input: String) {
        val output = (UriFilenamePrefixer("my_prefix_foo")::prefix)(input)

        assertEquals("my_prefix_doc.html", output, "Reference is not prefixed.")
    }

    @ParameterizedTest
    @ValueSource(strings = ["doc.html#anchor", "./doc.html#anchor"])
    fun `it should keep fragments`(input: String) {
        val output = (UriFilenamePrefixer("some_thing")::prefix)(input)

        assertEquals("some_doc.html#anchor", output, "Fragment is missing.")
    }

    @ParameterizedTest
    @ValueSource(strings = ["img.gif", "../doc.html", "https://example.org/path/to/doc.html"])
    fun `it should ignore invalid references`(input: String) {
        val output = (UriFilenamePrefixer("my_prefix_bar")::prefix)(input)

        assertEquals(input, output, "Referenced invalid reference, but should not.")
    }

    @ParameterizedTest
    @ValueSource(strings = ["""javascript:alert(".html");""", "doc<.html"])
    fun `it should ignore invalid URIs`(input: String) {
        val output = (UriFilenamePrefixer("foo_bar")::prefix)(input)

        assertEquals(input, output, "Non-URI links processed, but should not.")
    }
}
