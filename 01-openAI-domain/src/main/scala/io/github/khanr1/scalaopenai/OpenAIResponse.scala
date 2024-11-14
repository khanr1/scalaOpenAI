package io.github.khanr1.scalaopenai

/** A trait representing an OpenAI API response.
  *
  * Any class extending this trait must provide an implementation for retrieving a response message.
  */
trait OpenAIResponse {

  /** Retrieves the message from the OpenAI response.
    *
    * @return
    *   a String containing the response message.
    */
  def getResponseMessage: String

}
