package com.khanr1.scalaopenai

import chat.*

import org.scalacheck.Gen

object Generators {
  // Models Generators
  val modelGen: Gen[Models] = Gen.oneOf(Models.values)
  // Roles Generators
  val roleGen: Gen[Roles] = Gen.oneOf(Roles.values)
  // OpenAI Error Generator
  val openAIErrorGen: Gen[OpenAIErrors] = Gen.oneOf(OpenAIErrors.values)
  // OpenAI Error Response Generator
  val OpenAIErrorResponseGen: Gen[OpenAIErrorResponse] = for
    message <- Gen.alphaStr
    typ <- openAIErrorGen
    param <- Gen.option(Gen.alphaLowerStr)
    code <- Gen.option(Gen.alphaStr)
  yield OpenAIErrorResponse(message, typ, param, code)
  // Usage Generator
  val usageGen: Gen[Usage] = for
    prompt_tokens <- Gen.posNum[Int]
    response_tokens <- Gen.posNum[Int]
    total_tokens <- Gen.posNum[Int]
  yield Usage(prompt_tokens, response_tokens, total_tokens)

}
