package com.khanr1.scalaopenai

import io.circe.Decoder.Result

/** Enumeration representing different OpenAI models. Each model can be mapped to its corresponding
  * string representation.
  */
enum Models {
  case GPT4o
  case GPT4omin
}

object Models:

  // Map associating models with their string representation.
  private val modelLookUp: Map[Models, String] = Map(
    GPT4o -> "gpt-4o-2024-05-13",
    GPT4omin -> "gpt-4o-mini-2024-05-13"
  )

  /** Provides a `Show` instance for `Models`, allowing conversion to a string.
    */
  given Show[Models] = Show.show(model => modelLookUp(model))

  /** Provides a Circe Encoder instance for `Models`, converting a model into its corresponding JSON
    * string.
    */
  given Encoder[Models] = new Encoder[Models] {

    override def apply(model: Models): Json = Encoder.encodeString(model.show)

  }

  /** Provides a Circe Decoder instance for `Models`, converting a JSON string back into the
    * corresponding `Models` enum.
    */
  given Decoder[Models] = new Decoder[Models] {

    override def apply(c: HCursor): Result[Models] = for
      stringValue <- c.as[String]
      model <- modelLookUp.map((key, value) => value -> key) get (stringValue) match
        case None =>
          Left(
            DecodingFailure(
              s"Invalid model: $stringValue. Expected one of ${modelLookUp.values.mkString(", ")}",
              c.history
            )
          )
        case Some(value) => Right(value)
    yield model

  }
