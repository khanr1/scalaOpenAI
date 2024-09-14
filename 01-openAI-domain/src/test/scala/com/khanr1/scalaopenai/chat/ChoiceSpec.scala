package com.khanr1.scalaopenai
package chat

import weaver.SimpleIOSuite
import io.circe.parser.*
import io.circe.syntax.*

object ChoiceSpec extends SimpleIOSuite {

  pureTest("should decode valid JSON with all fields to Choice") {
    val json = """
      {
        "index": 0,
        "delta": { "role": "assistant", "content": "Hello" },
        "logprobs": true,
        "finish_reason": "max_tokens"
      }
    """
    val decoded = decode[Choice](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      Choice(0, Delta(Some(Roles.Assistant), Some("Hello")), Some(true), Some("max_tokens"))
    )
  }

  pureTest("should decode valid JSON with missing optional fields to Choice") {
    val json = """
      {
        "index": 1,
        "delta": { "role": "user", "content": "Hi" }
      }
    """
    val decoded = decode[Choice](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      Choice(1, Delta(Some(Roles.User), Some("Hi")), None, None)
    )
  }

  pureTest("should fail to decode invalid JSON with missing required field") {
    val json = """
      {
        "delta": { "role": "user", "content": "Hi" }
      }
    """
    val decoded = decode[Choice](json)
    expect(decoded.isLeft)
  }

  pureTest("should encode Choice object to JSON") {
    val choice =
      Choice(0, Delta(Some(Roles.Assistant), Some("Hello")), Some(true), Some("max_tokens"))
    val json = choice.asJson.noSpaces
    expect.same(
      json,
      """{"index":0,"delta":{"role":"assistant","content":"Hello"},"logprobs":true,"finish_reason":"max_tokens"}"""
    )
  }
}
