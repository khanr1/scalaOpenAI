package com.khanr1.scalaopenai
package chat

/** Case class representing a partial or delta message in an OpenAI chat interaction.
  *
  * @param role
  *   Optional role of the message sender. This field can be None if the role is not provided.
  * @param content
  *   Optional message content. This field can be None if the content is not provided.
  */
final case class Delta(role: Option[Roles], content: Option[String])

object Delta:
  /** Decoder instance for Delta, allowing the class to be decoded from JSON.
    */
  given Decoder[Delta] = Decoder.derived
  given Encoder[Delta] = Encoder.derived
