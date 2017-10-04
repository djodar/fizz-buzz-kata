package com.djodar.katas.tennisgame

import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class TennisGameScenarioTest extends FeatureSpec with GivenWhenThen with Matchers {

  scenario("Simple tennis game") {

    Given("new game between Victor and Sarah starts")
    val victor = Player("Victor")
    val sarah = Player("Sarah")
    val game = new TennisGame(victor, sarah)

    Then("score is love, love")
    game.score should be ("love, love")

    When("Victor wins the ball")
    victor.winBall

    Then("score is fifteen, love")
    game.score should be ("fifteen, love")

    When("Victor wins the ball")
    victor.winBall

    Then("score is thirty, love")
    game.score should be ("thirty, love")

    When("Victor wins the ball")
    victor.winBall

    Then("score is forty, love")
    game.score should be ("forty, love")

    When("Sarah wins the ball")
    sarah.winBall

    Then("score is forty, fifteen")
    game.score should be ("forty, fifteen")

    When("Sarah wins the ball")
    sarah.winBall

    Then("score is forty, thirty")
    game.score should be ("forty, thirty")

    When("Sarah wins the ball")
    sarah.winBall

    Then("score is deuce")
    game.score should be ("deuce")

    When("Victor wins the ball")
    victor.winBall

    Then("score is advantage Victor")
    game.score should be ("advantage Victor")

    When("Sarah wins the ball")
    sarah.winBall

    Then("score is deuce")
    game.score should be ("deuce")

    When("Sarah wins the ball")
    sarah.winBall

    Then("score is advantage Sarah")
    game.score should be ("advantage Sarah")

    When("Sarah wins the ball")
    sarah.winBall

    Then("score is Sarah won")
    game.score should be ("Sarah won")

  }

}
