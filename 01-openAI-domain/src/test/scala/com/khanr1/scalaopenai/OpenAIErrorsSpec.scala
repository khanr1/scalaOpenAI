package com.khanr1.scalaopenai

import weaver.SimpleIOSuite
import io.circe.syntax.*
import io.circe.parser.*

object OpenAIErrorsSpec extends SimpleIOSuite {

  pureTest("should encode APIConnectionError to JSON") {
    val model = OpenAIErrorResponse.APIConnectionError
    val json = model.asJson.noSpaces
    expect.same(json, "\"api_connection_error\"")
  }

  pureTest("should decode valid JSON string to APIConnectionError model") {
    val json = "\"api_connection_error\""
    val decoded = decode[OpenAIErrorResponse](json)
    expect(decoded.isRight) and expect.same(
      decoded.toOption.get,
      OpenAIErrorResponse.APIConnectionError
    )
  }

}
