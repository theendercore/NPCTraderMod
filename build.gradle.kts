plugins {
	id("fabric-loom") version "1.0-SNAPSHOT"
	kotlin("jvm") version "1.7.21"
	id("maven-publish")
}

base.archivesName.set(project.properties["archives_base_name"] as String)
version = project.properties["mod_version"] as String
group = project.properties["maven_group"] as String

repositories {

	maven { url = uri( "https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/") }
}

dependencies {
	minecraft("com.mojang:minecraft:${project.properties["minecraft_version"]}")
	mappings("net.fabricmc:yarn:${project.properties["yarn_mappings"]}:v2")
	modImplementation("net.fabricmc:fabric-loader:${project.properties["loader_version"]}")

	modImplementation("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_version"]}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${project.properties["fabric_kotlin_version"]}")
	modImplementation ("software.bernie.geckolib:geckolib-fabric-1.19:3.1.37")
}

tasks {
	processResources {
		inputs.property("version", project.version)
		filteringCharset = "UTF-8"

		filesMatching("fabric.mod.json") {
			expand(mapOf("version" to project.version))
		}
	}

	val targetJavaVersion = 17
	withType<JavaCompile> {
		options.encoding = "UTF-8"
		options.release.set(targetJavaVersion)
	}

	withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
		kotlinOptions.jvmTarget = targetJavaVersion.toString()
	}

	java {
		toolchain.languageVersion.set(JavaLanguageVersion.of(JavaVersion.toVersion(targetJavaVersion).toString()))
		withSourcesJar()
	}

	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesName}" }
		}
	}
}

publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
