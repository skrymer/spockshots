package org.skrymer.spockshots.matchers

import org.skrymer.spockshots.core.SnapshotHolder
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification

class JsonUnitSnapshotAssertSpec extends Specification {
    def mvcResult = Mock(MvcResult)
    def servletResponse = Mock(MockHttpServletResponse)

    def 'should match correctly' (){
        given: 'a mvcResult object containing a http servlet response'
        mvcResult.getResponse() >> new MockHttpServletResponse()
        servletResponse.getContentAsString() >> '{}'

        and: 'a snapshot is present in snapshot holder'
        SnapshotHolder.setCurrentSnapshot('{}')

        expect: ''
        JsonUnitSnapshotAssert.matchesSnapshot().match(mvcResult) == true
    }
}
