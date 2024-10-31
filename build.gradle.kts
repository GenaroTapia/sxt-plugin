plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "com.sicarx"
version = "1.0.0"

gradlePlugin {
    plugins {
        create("listenerPlugin") {
            id = "sxt-plugin"
            implementationClass = "com.sicarx.TestListenerPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = project.group.toString()
            artifactId = "sxt-plugin"
            version = project.version.toString()
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/GenaroTapia/sxt-plugin")
            credentials {
                username = System.getenv("MAVEN_USER")
                password = System.getenv("MAVEN_SECRET")
            }
        }
    }
}

