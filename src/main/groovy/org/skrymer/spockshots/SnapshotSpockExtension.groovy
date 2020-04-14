package org.skrymer.spockshots


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
    def snapshotName = annotation.snapshotName()

    return Files.list(Paths.get(snapshotFolderPath(spec)))
            .filter({ file -> getFileName(file) == snapshotName })
            .findFirst()
            .map({ path -> path.toFile().text })
            .orElseThrow({ new CouldNotFindSnapshotException(snapshotName) })
  }

  private static snapshotFolderPath(spec) {
    def rootFolder = './src/test/groovy'
    def executionSubFolder = spec.getPackage().replace('.', '/')
    def snapshotFolder = "__snapshots__"

    return "$rootFolder/$executionSubFolder/$snapshotFolder/"
            .replace('/', FileSystems.getDefault().getSeparator())
  }

  private static String getFileName(Path file){
    return file.fileName.toString().replace('.json', '')
  }

  static class CouldNotFindSnapshotException extends RuntimeException {
    CouldNotFindSnapshotException(String snapshot){
      super(snapshot)
    }
  }
}

