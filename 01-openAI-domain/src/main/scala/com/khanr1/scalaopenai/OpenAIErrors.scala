package com.khanr1.scalaopenai

import io.circe.Decoder.Result

/** Enumeration representing different OpenAI error. Each error can be mapped to its corresponding
  * string representation.
  */
enum OpenAIErrors {
  case APIConnectionError
  case APITimeoutError
  case AuthenticationError
  case BadRequestError
  case ConflictError
  case InternalServerError
  case NotFoundError
  case PermissionDeniedError
  case RateLimitError
  case UnprocessableEntityError
}

object OpenAIErrors:
  // Map associating error with their string representation.
  val errorLookup: Map[OpenAIErrors, String] = Map(
    APIConnectionError -> "api_connection_error",
    APITimeoutError -> "api_timeout_error",
    AuthenticationError -> "authentication_error",
    BadRequestError -> "bad_request_error",
    ConflictError -> "conflict_error",
    InternalServerError -> "interanl_server_error",
    NotFoundError -> "not_found_error",
    PermissionDeniedError -> "permission_denied_error",
    RateLimitError -> "rate_limit_error",
    UnprocessableEntityError -> "unprocessable_entity_error"
  )

  /** Provides a Circe Encoder instance for `OpenAIErrors`, converting a error into its
    * corresponding JSON string.
    */
  given Encoder[OpenAIErrors] = new Encoder[OpenAIErrors] {

    override def apply(a: OpenAIErrors): Json = Encoder.encodeString(errorLookup(a))

  }

  /** Provides a Circe Decoder instance for `OpenAIErrors`, converting a Json into its corresponding
    * OpenAIErrors
    */
  given Decoder[OpenAIErrors] = new Decoder[OpenAIErrors] {

    override def apply(c: HCursor): Result[OpenAIErrors] = for
      stringValue <- c.as[String]
      error <- errorLookup.map((k, v) => v -> k).get(stringValue) match
        case None =>
          Left(
            DecodingFailure(
              s"Invalid Error: $stringValue. Expected one of ${errorLookup.values.mkString(", ")}",
              c.history
            )
          )
        case Some(value) => Right(value)
    yield error

  }
