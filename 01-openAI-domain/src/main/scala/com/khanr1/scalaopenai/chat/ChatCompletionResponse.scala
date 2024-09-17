package com.khanr1.scalaopenai
package chat

/** Represents a response to a chat completion request in OpenAI's API.
  *
  * @param id
  *   Unique identifier for the response.
  * @param `object`
  *   The type of object (e.g., "chat.completion").
  * @param created
  *   Timestamp indicating when the response was created.
  * @param model
  *   The model used for the completion.
  * @param choices
  *   A list of possible completions (`Choice` objects).
  * @param usage
  *   Optional usage statistics (e.g., token counts).
  */
final case class ChatCompletionResponse(
    id: String,
    `object`: String,
    created: Long,
    model: Models,
    choices: List[Choice],
    usage: Option[Usage]
) extends OpenAIResponse:

  /** Extracts and concatenates the content of the choices in the response. If any choice's content
    * is None, it is ignored.
    *
    * @return
    *   A concatenated string of all choice contents.
    */
  def getReponseMessage: String =
    choices.flatMap(_.delta.content).mkString

object ChatCompletionResponse:
  given Decoder[ChatCompletionResponse] = Decoder.derived
  given Encoder[ChatCompletionResponse] = Encoder.derived
