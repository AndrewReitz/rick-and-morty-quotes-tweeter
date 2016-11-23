package cash.andrew.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class WriteConfig extends DefaultTask {

  File generatedResourcesDir

  @Input
  Properties appProperties = new Properties()

  @OutputFile
  File getConfigPropertiesFile() {
    new File(generatedResourcesDir, "$project.name-config.properties".toString())
  }

  @TaskAction
  def generate() {
    configPropertiesFile.withOutputStream { appProperties.store(it, null) }
  }
}
