package com.khanr1.scalaopenai

import io.circe.Decoder.Result

/** Enum representing the different roles that a user can have in the OpenAI API.
  *   - `Assistant`: Represents the assistant role (AI system)
  *   - `System`: Represents the system role
  *   - `User`: Represents the end user
  */
enum Roles {
  case Assistant
  case System
  case User
}

object Roles:
  // Map associating roles with their string representation.
  private val roleLookUp: Map[Roles, String] = Map(
    Assistant -> "assistant",
    System -> "system",
    User -> "user"
  )

  /** Show instance for Roles, which allows converting a Roles enum into its corresponding string
    * representation.
    */
  given show: Show[Roles] = Show.show(role => roleLookUp(role))

  /** Encoder instance for Roles, converting a Roles enum to a JSON string.
    */
  given encoder: Encoder[Roles] = new Encoder[Roles] {

    override def apply(role: Roles): Json = Encoder.encodeString(role.show)

  }

  /** Decoder instance for Roles, converting a JSON string to a Roles enum. Provides an error
    * message if the input string doesn't match any of the roles.
    */
  given decoder: Decoder[Roles] = new Decoder[Roles] {

    override def apply(c: HCursor): Result[Roles] =
      for
        stringValue <- c.as[String]
        role <- roleLookUp.map((k, v) => v -> k).get(stringValue) match
          case None =>
            Left(
              DecodingFailure(
                s"Invalid role: $stringValue. Expected one of ${roleLookUp.values.mkString(", ")}",
                c.history
              )
            )
          case Some(value) => Right(value)
      yield role

  }
