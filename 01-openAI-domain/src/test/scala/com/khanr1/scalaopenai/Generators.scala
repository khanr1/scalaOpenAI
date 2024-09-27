package com.khanr1.scalaopenai

import chat.*

import org.scalacheck.Gen
import scala.collection.immutable.LazyList.cons

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
  // Message generator
  val messageGen: Gen[Message] = for
    role <- roleGen
    content <- (Gen.alphaLowerStr)
    name <- Gen.option(Gen.alphaLowerStr)
  yield Message(role, content, name)
  // Delta Generator
  val deltaGen: Gen[Delta] = for
    role <- Gen.option(roleGen)
    content <- Gen.option(Gen.alphaLowerStr)
  yield Delta(role, content)
  // Choice Generator
  val choiceGen: Gen[Choice] = for
    index <- Gen.posNum[Int]
    delta <- deltaGen
    logprobs <- Gen.option(Gen.oneOf(List(true, false)))
    finish_reason <- Gen.option(Gen.alphaLowerStr)
  yield Choice(index, delta, logprobs, finish_reason)
  // ChatCompletionRequest Generator
  val chatCompletionRequestGen: Gen[ChatCompletionRequest] = for
    model <- modelGen
    messages <- Gen.listOf(messageGen)
  yield ChatCompletionRequest(model, messages)
  // ChatCompletionResposne Generator
  val chatCompletionResponse: Gen[ChatCompletionResponse] = for
    id <- Gen.alphaLowerStr
    `object` <- Gen.alphaLowerStr
    created <- Gen.long
    model <- modelGen
    choices <- Gen.listOf(choiceGen)
    usage <- Gen.option(usageGen)
  yield ChatCompletionResponse(id, `object`, created, model, choices, usage)

}
