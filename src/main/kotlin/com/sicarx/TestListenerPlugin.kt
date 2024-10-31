package com.sicarx

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.TestListener
import org.gradle.api.tasks.testing.TestResult
import org.gradle.api.tasks.testing.TestDescriptor

class TestListenerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.withType(Test::class.java).configureEach {
            useJUnitPlatform()

            val failures = mutableSetOf<String>()
            addTestListener(object : TestListener {
                override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {
                    if (result.resultType == TestResult.ResultType.FAILURE) {
                        val name = testDescriptor.name.substringBefore('(').let {
                            if (it == "initializationError") testDescriptor.className.toString() else "${testDescriptor.className}.$it"
                        }
                        failures.add(name)
                    }
                }

                override fun afterSuite(suite: TestDescriptor?, result: TestResult?) {
                    if (failures.isNotEmpty()) {
                        val failedTestsFile = project.layout.buildDirectory.file("failed-tests.txt").get().asFile
                        failedTestsFile.printWriter().use { writer ->
                            failures.forEach { testPath ->
                                writer.println(testPath)
                            }
                        }
                    }
                }

                override fun beforeSuite(suite: TestDescriptor?) {}

                override fun beforeTest(testDescriptor: TestDescriptor?) {}
            })
        }
    }
}
