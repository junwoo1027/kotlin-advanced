package com.example.dsl

@YamlDsl
class Service(private val name: String) {
    private var image: String by onceNotNull()
    private val environments = mutableListOf<Environment>()
    private val portRules = mutableListOf<PortRule>()

    fun image(init: () -> String) {
        image = init()
    }

    fun env(environment: Environment) {
        this.environments.add(environment)
    }

    fun port(
        host: Int,
        container: Int,
    ) {
        this.portRules.add(PortRule(host, container))
    }

    fun render(indent: String): String {
        val builder = StringBuilder()
        builder.appendNew("$name:")
        builder.appendNew("image: $image", indent, 1)
        builder.appendNew("environment:")
        builder.appendNew(environments.joinToString("\n") { "- ${it.key}: ${it.value}" }.addIndent(indent, 1))
        builder.appendNew("port:")
        builder.appendNew(portRules.joinToString("\n") { "- \"${it.host}:${it.container}\"" }.addIndent(indent, 1))
        return builder.toString()
    }
}
