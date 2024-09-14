package com.khanr1.scalaopenai
package chat

import weaver.SimpleIOSuite
import io.circe.parser._
import io.circe.syntax._

object MessageSpec extends SimpleIOSuite {

  pureTest("should encode Message with name to JSON") {
    val message = Message(Roles.Assistant, "Hello, how can I help you?", Some("Assistant"))
    val json = message.asJson.noSpaces
    expect.same(
      json,
      """{"role":"assistant","content":"Hello, how can I help you?","name":"Assistant"}"""
    )
  }

  pureTest("should encode Message without name to JSON") {
    val message = Message(Roles.User, "What is the weather today?", None)
    val json = message.asJson.noSpaces
    expect.same(
      json,
      """{"role":"user","content":"What is the weather today?","name":null}"""
    )
  }

  pureTest("should decode valid JSON with name to Message") {
    val json = """{"role":"assistant","content":"Hello, how can I help you?","name":"Assistant"}"""
    val decoded = decode[Message](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      Message(Roles.Assistant, "Hello, how can I help you?", Some("Assistant"))
    )
  }

  pureTest("should decode valid JSON without name to Message") {
    val json = """{"role":"user","content":"What is the weather today?"}"""
    val decoded = decode[Message](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      Message(Roles.User, "What is the weather today?", None)
    )
  }

  pureTest("should fail to decode JSON with missing content field") {
    val json = """{"role":"user"}"""
    val decoded = decode[Message](json)
    expect(decoded.isLeft)
  }
}
