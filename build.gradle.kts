plugins {
    `kotlin-dsl`
    `maven-publish`
}

group = "com.sicarx"
version = "1.0.0"

gradlePlugin {
    plugins {
        create("versioningPlugin") {
            id = "com.sicarx.versioning"
            implementationClass = "com.sicarx.TestListenerPlugin"
        }
    }
}

