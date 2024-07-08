plugins {
    entitylib.`library-conventions`
    entitylib.`shadow-conventions`
    `el-version`
}

dependencies {
    api(libs.jetbrains.annotations)

    compileOnlyApi(libs.bundles.adventure)
    compileOnlyApi(libs.packetevents.api)
}

tasks {
    generateVersionsFile {
        packageName = "me.tofaa.entitylib.utils"
    }
}
