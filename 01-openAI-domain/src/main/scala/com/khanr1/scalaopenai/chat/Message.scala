package com.khanr1.scalaopenai
package chat

/** Case class representing a message in an OpenAI chat interaction.
  *
  * @param role
  *   The role of the message sender (e.g., Assistant, User, System).
  * @param content
  *   The content of the message.
  * @param name
  *   Optional name of the sender, if applicable.
  */
final case class Message(role: Roles, content: String, name: Option[String] = None)

object Message:
  /** Encoder instance for Message, allowing the class to be encoded to JSON.
    */
  given Encoder[Message] = Encoder.derived

  /** Decoder instance for Message, allowing the class to be decoded from JSON. Handles cases where
    * optional fields may be absent.
    */
  given Decoder[Message] = Decoder.derived
