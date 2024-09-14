package com.khanr1.scalaopenai

import ciris.*

case class OpenAIConfig(apiKey: String)

object Config {
  def loadOpenAIConfig[F[_]](): ConfigValue[F, OpenAIConfig] = {
    val apiKey = env("OPENAI_API_KEY").as[String]
    apiKey.map(OpenAIConfig.apply)
  }
}
