package core.util

import config.data.AppConfig
import java.net.URLDecoder

class UriParser (private val appConfig: AppConfig) {
    fun parseUri(uri: String): Map<String, Any> {
        require(uri.startsWith("vless://")) { "Only vless:// is supported" }

        val noScheme = uri.removePrefix("vless://")
        val split = noScheme.split("@")
        val uuid = split[0]
        val rest = split[1]

        val addressPortAndQuery = rest.split("?", limit = 2)
        val addressPort = addressPortAndQuery[0].split(":")
        val server = addressPort[0]
        val port = addressPort[1].toInt()

        val queryAndTag = addressPortAndQuery[1].split("#", limit = 2)
        val queryParams = parseQueryParams(queryAndTag[0])
        val tag = queryAndTag.getOrNull(1) ?: "vless-out"

        val outbound = generateOutboundFromUri(uuid, server, port, queryParams, tag)
        return generateFullConfig(outbound)
    }

    private fun parseQueryParams(query: String): Map<String, String> {
        return query.split("&").associate {
            val (key, value) = it.split("=")
            key to URLDecoder.decode(value, "UTF-8")
        }
    }

    private fun generateOutboundFromUri(
        uuid: String,
        server: String,
        port: Int,
        params: Map<String, String>,
        tag: String
    ): Map<String, Any?> {
        return mapOf(
            "tag" to tag,
            "type" to "vless",
            "server" to server,
            "server_port" to port,
            "uuid" to uuid,
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
    }

    private fun generateFullConfig(outbound: Map<String, Any?>): Map<String, Any> {
        return mapOf(
            "log" to mapOf("level" to appConfig.singBox.logLevel),
            "inbounds" to listOf(
                mapOf(
                    "domain_strategy" to "",
                    "listen" to "127.0.0.1",
                    "listen_port" to appConfig.singBox.port,
                    "sniff" to true,
                    "sniff_override_destination" to false,
                    "tag" to "mixed-in",
                    "type" to "mixed",
                    "set_system_proxy" to true
                )
            ),
            "outbounds" to listOf(outbound)
        )
    }
}
