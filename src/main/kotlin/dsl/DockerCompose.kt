package com.example.dsl

/**
 * version: '3'
 * services:
 *   db:
 *     image: mysql
 *   environment:
 *     - USER: myuser
 *     - PASSWORD: mypassword
 *   port:
 *     - "9999:3306"
 */
fun main() {
    val yml: DockerCompose =
        dockerCompose {
            version { 3 }
            service(name = "db") {
                image { "mysql" }
                env("USER" - "myuser")
                env("PASSWORD" - "mypassword")
                port(host = 9999, container = 3306)
            }
        }

    val yml2: DockerCompose =
        dockerCompose {
            service(name = "db") {
                this@dockerCompose.service(name = "web") {
                }
            }
        }

    println(yml.render("  "))

    val listOf = listOf("A", "B", "C")

    println(listOf.joinToString("\n") { "$it" })
}

fun dockerCompose(init: DockerCompose.() -> Unit): DockerCompose {
    val dockerCompose = DockerCompose()
    dockerCompose.init()
    return dockerCompose
}

@YamlDsl
class DockerCompose {
    private var version: Int by onceNotNull()
    private val services = mutableListOf<Service>()

    fun version(init: () -> Int) {
        version = init()
    }

    fun service(
        name: String,
        init: Service.() -> Unit,
    ) {
        val service = Service(name)
        service.init()
        services.add(service)
    }

    fun render(indent: String): String {
        val builder = StringBuilder()
        builder.appendNew("version: '$version'")
        builder.appendNew("services:")
        builder.appendNew(services.joinToString("\n") { it.render(indent) }.addIndent(indent, 1))
        return builder.toString()
    }
}

@DslMarker // 가장 가까운 수신객체에 대해서만 this를 생갹할 수 있다.
annotation class YamlDsl
