package org.skrymer.spockshots

import groovy.util.logging.Log
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo

/**
 * loads a snapshot before a spec is run and removes snapshot after spec has run
 */
@Log
class SnapshotListener extends AbstractRunListener {
  @Override
  void beforeFeature(FeatureInfo feature) {
    def snapshot = SnapshotHolder.getSnapshotForFeature(feature)
    if (snapshot != null) {
      log.info("found snapshot for feature $feature.name")
      SnapshotHolder.setCurrentSnapshot(snapshot)
    } else {
      log.info("beforeSpec ran but no snapshot for this test")
      SnapshotHolder.setCurrentSnapshot("beforeSpec ran but no snapshot for this test")
    }
  }

  @Override
  void afterSpec(SpecInfo spec) {
    log.info("afterSpec ran and destroyed current snapshot")
    SnapshotHolder.setCurrentSnapshot("afterSpec ran and destroyed current snapshot")
  }
}
