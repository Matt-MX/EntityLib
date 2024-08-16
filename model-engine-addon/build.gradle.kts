plugins {
    entitylib.`shadow-conventions`
    entitylib.`library-conventions`
}

repositories {
    maven {
        url = uri("https://mvn.lumine.io/repository/maven-public/")
    }
    maven("https://jitpack.io")
}

dependencies {
    // compileOnly("com.ticxo.modelengine:ModelEngine:R4.0.4")
    api(project(":api"))

    implementation("commons-io:commons-io:2.11.0")
    implementation("org.zeroturnaround:zt-zip:1.8")

    implementation("javax.json:javax.json-api:1.1.4")
    implementation("org.glassfish:javax.json:1.1.4")

    implementation("com.github.hollow-cube.common:mql:2b48ad430f")
}
