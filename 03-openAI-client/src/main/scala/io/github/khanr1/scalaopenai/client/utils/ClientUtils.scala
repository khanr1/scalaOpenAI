package io.github.khanr1.scalaopenai
package client.utils

import io.circe.parser.*
import io.circe.DecodingFailure
import cats.effect.Concurrent
import io.circe.ParsingFailure
import fs2.Pipe
import fs2.Stream

/** Utility method to decode a JSON object into a type A.
  *
  * @param json
  *   The JSON to decode
  * @param F
  *   The effect type (e.g., IO, Future)
  * @tparam A
  *   The type we want to decode to
  * @return
  *   A Stream[F, A] that emits the decoded object or raises an error on failure.
  */
def decodeJson[F[_]: Concurrent, A: Decoder](json: Json): Stream[F, A] =
  json.as[A] match {
    case Left(decodingFailure) =>
      Stream.raiseError[F](
        new Throwable(s"we tried to decode $json but failed  ${decodingFailure.getMessage}")
      )
    case Right(a) => Stream.emit(a)
  }

/** Tries to parse a string into a JSON object and decode it into a type A.
  *
  * @param text
  *   The string to parse
  * @param F
  *   The effect type (e.g., IO, Future)
  * @tparam A
  *   The type we want to decode to
  * @return
  *   A Stream[F, A] that emits the decoded object or raises an error on failure.
  */
def decodeText[F[_]: Concurrent, A: Decoder](text: String): Stream[F, A] =
  parse(text) match {
    case Left(parseFailure) =>
      Stream.raiseError[F](
        new ParsingFailure(
          s"Parsing failure: we tried to parse *$text, but failed. The failure message is ${parseFailure.getMessage}",
          parseFailure.underlying
        )
      )
    case Right(json) => decodeJson[F, A](json)
  }

/** Pipe to parse a streaming response, decode it line by line into type A.
  *
  * @tparam A
  *   The type to decode the response into
  * @tparam F
  *   The effect type (e.g., IO, Future)
  * @return
  *   A Pipe[F, String, A] that emits the decoded objects or raises errors on failure.
  */
def parseResponsePipe[F[_]: Concurrent, A: Decoder]: Pipe[F, String, A] = inStream =>
  inStream
    .flatMap { text =>
      // Split the response on newlines in case multiple `data:` chunks are in the same line
      Stream.emits(text.split("\n").toSeq) // Convert to a stream of individual lines
    }
    // Remove the "data: " prefix from the strings
    .map(_.replace("data: ", ""))
    // Filter out empty lines or lines that indicate the stream is done
    .filterNot(line => line.isEmpty || line == "[DONE]")
    // Attempt to decode each text line into a JSON object
    .flatMap(text => decodeText[F, A](text))
