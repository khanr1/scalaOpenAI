package com.khanr1.scalaopenai
package chat

import weaver.SimpleIOSuite
import io.circe.parser.*
import io.circe.syntax.*

object ChatCompletionResponseSpec extends SimpleIOSuite {

  pureTest("should decode valid JSON into ChatCompletionResponse") {
    val json = """
      {
        "id": "response-123",
        "object": "chat.completion",
        "created": 1623712932,
        "model": "gpt-4o-2024-05-13",
        "choices": [
          {
            "index": 0,
            "delta": {
              "role": "assistant",
              "content": "Hello, how can I help?"
            },
            "logprobs": null,
            "finish_reason": "stop"
          }
        ],
        "usage": {
          "prompt_tokens": 10,
          "completion_tokens": 15,
          "total_tokens": 25
        }
      }
    """
    val decoded = decode[ChatCompletionResponse](json)
    expect(decoded.isRight) and expect(decoded.toOption.get.id == "response-123")
  }

  pureTest("should correctly extract content from choices") {
    val response = ChatCompletionResponse(
      id = "response-123",
      `object` = "chat.completion",
      created = 1623712932,
      model = Models.GPT4o,
      choices = List(
        Choice(0, Delta(Some(Roles.Assistant), Some("Hello")), None, Some("stop")),
        Choice(1, Delta(Some(Roles.User), None), None, Some("stop"))
      ),
      usage = Some(Usage(10, 15, 25))
    )
    expect.same(response.getResponseMessage, "Hello")
  }

  pureTest("should handle no content in getContent method") {
    val response = ChatCompletionResponse(
      id = "response-456",
      `object` = "chat.completion",
      created = 1623712932,
      model = Models.GPT4omin,
      choices = List(
        Choice(0, Delta(Some(Roles.User), None), None, Some("stop"))
      ),
      usage = Some(Usage(10, 15, 25))
    )
    expect.same(response.getResponseMessage, "")
  }
}
