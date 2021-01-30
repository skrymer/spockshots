package org.skrymer.spockshots.core


import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * loads snapshot specified in @Snapshot and puts it in SnapshotHolder
 */
class SnapshotSpockExtension extends AbstractAnnotationDrivenExtension<Snapshot> {
    def snapListener = new SnapshotListener()

    @Override
    void visitFeatureAnnotation(Snapshot annotation, FeatureInfo feature) {
        def spec = feature.getSpec()
        def snapshotContent = getSnapshotContent(annotation, spec)
        SnapshotHolder.setSnapshotForFeature(feature, snapshotContent)
        spec.addListener(snapListener)
    }

    private static String getSnapshotContent(Snapshot annotation, SpecInfo spec) {
        def snapshotName = annotation.name()
        // Should be overridable with sys prop
        def rootFolder = './src/test/java'
        def executionSubFolder = spec.getPackage().replace('.', '/')
        def snapshotFolderPath = "$rootFolder/$executionSubFolder/__snapshots__/"
                .replace('/', FileSystems.getDefault().getSeparator())
        def file = Files.list(Paths.get(snapshotFolderPath))
                .filter({ file -> getFileName(file) == snapshotName })
                .map({ path -> path.toFile() })
                .findFirst()
                .orElseThrow({ new CouldNotFindSnapshotException(snapshotName) })

        return file.text
    }

    private static String getFileName(Path file) {
        return file.fileName.toString().replace('.json', '')
    }

    static class CouldNotFindSnapshotException extends RuntimeException {
        CouldNotFindSnapshotException(String snapshot) {
            super(snapshot)
        }
    }
}

