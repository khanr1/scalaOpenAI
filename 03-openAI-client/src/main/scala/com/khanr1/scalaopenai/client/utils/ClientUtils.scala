package com.khanr1.scalaopenai
package client.utils

import io.circe.parser.*
import io.circe.DecodingFailure
import cats.effect.Concurrent
import io.circe.ParsingFailure

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
        decodingFailure
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
          s"Parsing failure: ${parseFailure.getMessage}",
          parseFailure.underlying
        )
      )
    case Right(json) => decodeJson[F, A](json)
  }
