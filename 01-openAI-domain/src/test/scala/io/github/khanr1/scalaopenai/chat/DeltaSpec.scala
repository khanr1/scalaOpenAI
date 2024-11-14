package io.github.khanr1.scalaopenai
package chat

import weaver.SimpleIOSuite
import io.circe.parser._
import io.circe.syntax._
import io.github.khanr1.scalaopenai.chat.Delta

object DeltaSpec extends SimpleIOSuite {

  pureTest("should decode valid JSON with role and content to Delta") {
    val json = """{"role":"assistant","content":"Hello, how can I assist you?"}"""
    val decoded = decode[Delta](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      Delta(Some(Roles.Assistant), Some("Hello, how can I assist you?"))
    )
  }
}
