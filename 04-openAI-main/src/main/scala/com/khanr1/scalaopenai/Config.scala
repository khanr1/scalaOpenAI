package com.khanr1.scalaopenai

import ciris.*

/** Case class representing the OpenAI configuration.
  *
  * @param apiKey
  *   The OpenAI API key used for authenticating requests.
  */
case class OpenAIConfig(apiKey: String)

object Config {

  /** Loads the OpenAI configuration by reading the API key from the environment.
    *
    * @tparam F
    *   The effect type (e.g., IO, Future) used by Ciris for configuration loading.
    * @return
    *   A `ConfigValue` that holds the `OpenAIConfig`.
    */
  def loadOpenAIConfig[F[_]](): ConfigValue[F, OpenAIConfig] = {
    // Load the OPENAI_API_KEY from environment variables
    val apiKey = env("OPENAI_API_KEY").as[String]
    // Map the loaded API key into an OpenAIConfig
    apiKey.map(OpenAIConfig.apply)
  }
}
