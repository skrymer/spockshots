package org.skrymer.spockshots.core

import spock.lang.Specification


@Spockshot(
    rootFolder = "./src/test/groovy"
)
class TestSpec extends Specification {

    @Snapshot(snapshotName = 'snapshot')
    def 'should load snapshot' () {
        given: 'some json'
        def json =
        """
        {
            "first": "John",
            "last": "Doe"
        }
        """

        expect:
        SnapshotHolder.getSnapshot().replaceAll("\\s","") == json.replaceAll("\\s","")
    }
}
