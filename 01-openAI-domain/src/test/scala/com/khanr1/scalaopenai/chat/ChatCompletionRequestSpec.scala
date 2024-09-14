package com.khanr1.scalaopenai
package chat

import weaver.SimpleIOSuite
import io.circe.parser.*
import io.circe.syntax.*
import scala.util.Try

object ChatCompletionRequestSpec extends SimpleIOSuite {

  pureTest("should encode ChatCompletionRequest to JSON") {
    val request = ChatCompletionRequest(
      model = Models.GPT4o,
      messages = List(Message(Roles.User, "Hello")),
      temperature = 1.0,
      stream = false
    )
    val json = request.asJson.noSpaces
    expect.same(
      json,
      """{"model":"gpt-4o-2024-05-13","messages":[{"role":"user","content":"Hello","name":null}],"temperature":1.0,"stream":false}"""
    )
  }

  pureTest("should decode valid JSON to ChatCompletionRequest") {
    val json = """
      {
        "model": "gpt-4o-2024-05-13",
        "messages": [{"role": "user", "content": "Hello"}],
        "temperature": 1.0,
        "stream": false
      }
    """
    val decoded = decode[ChatCompletionRequest](json)
    expect(decoded.isRight) and expect(decoded.toOption.get.temperature == 1.0)
  }

  pureTest("should fail when temperature is invalid (above range)") {
    val result = Try {
      ChatCompletionRequest(
        model = Models.GPT4o,
        messages = List(Message(Roles.User, "Hello")),
        temperature = 3.0, // Invalid temperature (above range)
        stream = false
      )
    }
    expect(result.isFailure) and expect(result.failed.get.isInstanceOf[IllegalArgumentException])
  }

  pureTest("should allow valid temperature within range") {
    val request = ChatCompletionRequest(
      model = Models.GPT4o,
      messages = List(Message(Roles.User, "Hello")),
      temperature = 0.5,
      stream = false
    )
    expect(request.temperature == 0.5)
  }

  pureTest("should fail when temperature is invalid (below range)") {
    val result = Try {
      ChatCompletionRequest(
        model = Models.GPT4o,
        messages = List(Message(Roles.User, "Hello")),
        temperature = -1.0, // Invalid temperature (below range)
        stream = false
      )
    }
    expect(result.isFailure) and expect(result.failed.get.isInstanceOf[IllegalArgumentException])
  }
}
