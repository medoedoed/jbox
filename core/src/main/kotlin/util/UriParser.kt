package core.util

import config.data.AppConfig
import java.net.URLDecoder

class UriParser (
    private val defaultConfigPath: String,
    private val outputConfigPath: String
) {
    private val mapper: ObjectMapper = jacksonObjectMapper()

    fun injectVlessFromUri(uriString: String) {
        val uri = URI(uriString)
        val params = parseQuery(uri.rawQuery)

        val outbound = mapOf(
            "tag" to "vless-out",
            "type" to "vless",
            "server" to uri.host,
            "server_port" to uri.port,
            "uuid" to uri.userInfo,
            "flow" to params["flow"],
            "network" to params["type"],
            "tls" to mapOf(
                "enabled" to true,
                "reality" to mapOf(
                    "enabled" to true,
                    "public_key" to params["pbk"],
                    "short_id" to params["sid"]
                ),
                "server_name" to params["sni"],
                "utls" to mapOf(
                    "enabled" to true,
                    "fingerprint" to params["fp"]
                )
            )
        )

        val root: MutableMap<String, Any> = mapper.readValue(File(defaultConfigPath))
        val outbounds = (root["outbounds"] as? MutableList<Any>) ?: mutableListOf()
        outbounds.add(outbound)
        root["outbounds"] = outbounds

        mapper.writerWithDefaultPrettyPrinter().writeValue(File(outputConfigPath), root)
    }

    private fun parseQuery(query: String?): Map<String, String> {
        return query?.split("&")?.mapNotNull {
            val parts = it.split("=")
            if (parts.size == 2) parts[0] to URLDecoder.decode(parts[1], "UTF-8") else null
        }?.toMap() ?: emptyMap()
    }
}