package org.skrymer.spockshots

import org.spockframework.runtime.model.FeatureInfo

import java.util.concurrent.ConcurrentHashMap

/**
 * Holds the current snapshot
 */
class SnapshotHolder {
  private static final ThreadLocal<String> contextHolder = new ThreadLocal<>()
  private static final Map<String, String> snapshots = new ConcurrentHashMap<>()

  static void setSnapshotForFeature(FeatureInfo feature, String snapshot) {
    def key = getSnapshotKey(feature)
    snapshots.put(key, snapshot)
  }

  static String getSnapshotForFeature(FeatureInfo feature) {
    return snapshots.get(getSnapshotKey(feature))
  }

  static String getSnapshotKey(FeatureInfo feature) {
    def spec = feature.getParent()
    return spec.getPackage() + "--" +
      spec.getName() + "--" +
      feature.getName()
  }

  static void setCurrentSnapshot(String snapshot) {
    contextHolder.set(snapshot)
  }

  static String getSnapshot() {
    return contextHolder.get()
  }
}