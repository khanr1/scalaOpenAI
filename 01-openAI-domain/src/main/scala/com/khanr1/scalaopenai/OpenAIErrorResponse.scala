package com.khanr1.scalaopenai

/** Define error case class
  *
  * @param message
  *   The message associated to the error
  * @param type
  *   The type of the Error from the `OpenAIErrors`
  * @param param
  *   Provide information about whit parameter in the request caused the error
  * @param code
  *   provides more detailed information about the type of error encountered
  */
final case class OpenAIErrorResponse(
    message: String,
    `type`: OpenAIErrors,
    param: Option[String],
    code: Option[String]
) extends OpenAIResponse:
  override def getResponseMessage: String =
    s"OpenAI Error: $message. Type: ${`type`}, Param: ${param.getOrElse("None")}, Code: ${code.getOrElse("None")}"

object OpenAIErrorResponse:
  given Show[OpenAIErrorResponse] = Show.fromToString
  given Encoder[OpenAIErrorResponse] = Encoder.derived
  given Decoder[OpenAIErrorResponse] = Decoder.derived
