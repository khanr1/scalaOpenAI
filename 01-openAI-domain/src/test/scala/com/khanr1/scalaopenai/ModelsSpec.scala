package com.khanr1.scalaopenai

import weaver.SimpleIOSuite
import io.circe.syntax.*
import io.circe.parser.*

object ModelsSpec extends SimpleIOSuite {

  pureTest("should encode GPT4o to JSON") {
    val model = Models.GPT4o
    val json = model.asJson.noSpaces
    expect.same(json, "\"gpt-4o-2024-05-13\"")
  }

  pureTest("should encode GPT4omin to JSON") {
    val model = Models.GPT4omin
    val json = model.asJson.noSpaces
    expect.same(json, "\"gpt-4o-mini-2024-05-13\"")
  }

  pureTest("should decode valid JSON string to GPT4o model") {
    val json = "\"gpt-4o-2024-05-13\""
    val decoded = decode[Models](json)
    expect(decoded.isRight) and expect.same(decoded.toOption.get, Models.GPT4o)
  }

  pureTest("should decode valid JSON string to GPT4omin model") {
    val json = "\"gpt-4o-mini-2024-05-13\""
    val decoded = decode[Models](json)
    expect(decoded.isRight) and expect.same(decoded.toOption.get, Models.GPT4omin)
  }

  pureTest("should fail to decode invalid JSON string") {
    val json = "\"invalid-model\""
    val decoded = decode[Models](json)
    expect(decoded.isLeft)
  }
}
