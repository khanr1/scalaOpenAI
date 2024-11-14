package io.github.khanr1.scalaopenai

import weaver.SimpleIOSuite
import io.circe.parser._
import io.circe.syntax._
import io.github.khanr1.scalaopenai.Roles
import io.github.khanr1.scalaopenai.Roles

object RolesSpec extends SimpleIOSuite {

  pureTest("should encode Assistant role to JSON") {
    val role = Roles.Assistant
    val json = role.asJson.noSpaces
    expect.same(json, "\"assistant\"")
  }

  pureTest("should encode System role to JSON") {
    val role = Roles.System
    val json = role.asJson.noSpaces
    expect.same(json, "\"system\"")
  }

  pureTest("should encode User role to JSON") {
    val role = Roles.User
    val json = role.asJson.noSpaces
    expect.same(json, "\"user\"")
  }

  pureTest("should decode valid JSON string to Assistant role") {
    val json = "\"assistant\""
    val decoded = decode[Roles](json)
    expect(decoded.isRight) and expect.same(decoded.toOption.get, Roles.Assistant)
  }

  pureTest("should decode valid JSON string to System role") {
    val json = "\"system\""
    val decoded = decode[Roles](json)
    expect(decoded.isRight) and expect.same(decoded.toOption.get, Roles.System)
  }

  pureTest("should decode valid JSON string to User role") {
    val json = "\"user\""
    val decoded = decode[Roles](json)
    expect(decoded.isRight) and expect.same(decoded.toOption.get, Roles.User)
  }

  pureTest("should fail to decode invalid JSON string") {
    val json = "\"invalid-role\""
    val decoded = decode[Roles](json)
    expect(decoded.isLeft)
  }
}
