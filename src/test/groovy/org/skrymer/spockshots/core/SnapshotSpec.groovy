package org.skrymer.spockshots.core

import spock.lang.Specification

@Spockshot(
    rootFolder = "./src/test/groovy"
)
class SnapshotSpec extends Specification {

    @Snapshot(snapshotName = 'snapshot')
    def 'should load snapshot' () {
        given: 'some json'
        def expectedSnapshot =
        """
        {
            "first": "Sonni",
            "last": "Doe"
        }
        """.replaceAll("\\s","")

        expect:
        def snapshot = SnapshotHolder.getSnapshot()
        snapshot.replaceAll("\\s","") == expectedSnapshot
    }
}
