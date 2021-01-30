package org.skrymer.spockshots.matchers

import org.hamcrest.MatcherAssert
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultMatcher

import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals
import static org.skrymer.spockshots.core.SnapshotHolder.getSnapshot

final class JsonUnitSnapshotAssert implements ResultMatcher {
  private String expectedJson

  private JsonUnitSnapshotAssert(String expectedJson){
    this.expectedJson = expectedJson
  }

  static ResultMatcher matchesSnapshot(){
    new JsonUnitSnapshotAssert(getSnapshot())
  }

  @Override
  void match(MvcResult result) throws Exception {
    def content = result.response.contentAsString
    MatcherAssert.assertThat(content, jsonEquals(expectedJson));
  }
}
