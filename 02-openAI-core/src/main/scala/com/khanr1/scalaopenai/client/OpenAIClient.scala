package com.khanr1.scalaopenai

import com.khanr1.scalaopenai.chat.Message
import com.khanr1.scalaopenai.chat.ChatCompletionResponse

trait OpenAIClient[F[_]] {
  def chatCompletion(messages: List[Message]): Stream[F, ChatCompletionResponse]
}
