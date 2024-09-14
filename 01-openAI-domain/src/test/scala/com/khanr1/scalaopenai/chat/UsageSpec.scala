package com.khanr1.scalaopenai
package chat

import weaver.SimpleIOSuite
import io.circe.parser._

object UsageSpec extends SimpleIOSuite {

  pureTest("should decode valid JSON to Usage case class") {
    val json = """
      {
        "prompt_tokens": 100,
        "completion_tokens": 50,
        "total_tokens": 150
      }
    """
    val decoded = decode[Usage](json)
    expect(decoded.isRight) and expect.same(decoded.toOption.get, Usage(100, 50, 150))
  }

  pureTest("should fail to decode JSON with missing fields") {
    val json = """
      {
        "prompt_tokens": 100,
        "completion_tokens": 50
      }
    """
    val decoded = decode[Usage](json)
    expect(decoded.isLeft)
  }

  pureTest("should fail to decode JSON with invalid field type") {
    val json = """
      {
        "prompt_tokens": "ab",
        "completion_tokens": 50,
        "total_tokens": 150
      }
    """
    val decoded = decode[Usage](json)
    expect(decoded.isLeft)
  }
}
