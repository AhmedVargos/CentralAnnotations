package central.weaving.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.aspectj.bridge.IMessage
import org.aspectj.bridge.MessageHandler
import org.aspectj.tools.ajc.Main
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile

class CentralPlugin implements Plugin<Project> {
  @Override void apply(Project project) {
    def hasApp = project.plugins.withType(AppPlugin)
    def hasLib = project.plugins.withType(LibraryPlugin)
    if (!hasApp && !hasLib) {
      throw new IllegalStateException("'android' or 'android-library' plugin required.")
    }

    final def log = project.logger
    final def variants
    if (hasApp) {
      variants = project.android.applicationVariants
    } else {
      variants = project.android.libraryVariants
    }

    project.afterEvaluate {
      project.dependencies {
//      debugCompile(name:'central-runtime-release', ext:'aar')

//        debugImplementation 'com.ahmedvargos:central-runtime:1.0.0'
//        debugImplementation 'org.aspectj:aspectjrt:1.8.6'
//        implementation 'com.github.AhmedVargos:CentralAnnotations:0.1'
//        implementation 'org.aspectj:aspectjrt:1.8.6'

      }
    }

    project.extensions.create('central', CentralExtension)

    variants.all { variant ->
      if (!variant.buildType.isDebuggable()) {
        log.debug("Skipping non-debuggable build type '${variant.buildType.name}'.")
        return
      } else if (!project.central.enabled) {
        log.debug("Central is not disabled.")
        return
      }

      JavaCompile javaCompile = variant.javaCompile
      javaCompile.doLast {
        String[] args = [
            "-showWeaveInfo",
            "-1.5",
            "-inpath", javaCompile.destinationDir.toString(),
            "-aspectpath", javaCompile.classpath.asPath,
            "-d", javaCompile.destinationDir.toString(),
            "-classpath", javaCompile.classpath.asPath,
            "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)
        ]
        log.debug "ajc args: " + Arrays.toString(args)

        MessageHandler handler = new MessageHandler(true)
        new Main().run(args, handler)
        for (IMessage message : handler.getMessages(null, true)) {
          switch (message.getKind()) {
            case IMessage.ABORT:
            case IMessage.ERROR:
            case IMessage.FAIL:
              log.error message.message, message.thrown
              break
            case IMessage.WARNING:
              log.warn message.message, message.thrown
              break
            case IMessage.INFO:
              log.info message.message, message.thrown
              break
            case IMessage.DEBUG:
              log.debug message.message, message.thrown
              break
          }
        }
      }
    }
  }
}
