import java.net.URI

class UriFilenamePrefixer(
    private val prefix: String
) {

    fun prefix(reference: String): String {
        return try {
            with(URI(reference)) {
                when (isValid()) {
                    true -> prefix.replaceAfterLast("_", normalize().toString())
                    else -> reference
                }
            }
        } catch (e: Exception) {
            reference
        }
    }

    private fun URI.isValid(): Boolean {
        return !isAbsolute &&
            path.endsWith(".html") &&
            normalize() == resolve(normalize())
    }
}
