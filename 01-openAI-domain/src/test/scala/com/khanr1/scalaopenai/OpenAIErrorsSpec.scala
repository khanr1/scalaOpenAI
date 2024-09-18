package com.khanr1.scalaopenai

import weaver.SimpleIOSuite
import io.circe.syntax.*
import io.circe.parser.*

object OpenAIErrorsSpec extends SimpleIOSuite {

  pureTest("should encode APIConnectionError to JSON") {
    val model = OpenAIErrors.APIConnectionError
    val json = model.asJson.noSpaces
    expect.same(json, "\"api_connection_error\"")
  }

  pureTest("should decode valid JSON string to APIConnectionError model") {
    val json = "\"api_connection_error\""
    val decoded = decode[OpenAIErrors](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      OpenAIErrors.APIConnectionError
    )
  }

}
