package com.djodar.katas.tennisgame

class TennisGame(val player1: Player, val player2: Player) {

  private def whoWins(player1: Player, player2: Player): Option[String] = (player1.score, player2.score) match {
    case (s1, s2) if s1 == s2 => Option.empty
    case (s1, s2) if s1 > s2 => Option(player1.name)
    case (s1, s2) if s1 < s2 => Option(player2.name)
  }

  def score: String = (player1.score, player2.score, whoWins(player1, player2)) match {
    case (s1, s2, name) if hasReachedPoints(3, s1, s2) && hasAdvantage(1, s1, s2) => "advantage " + name.get
    case (s1, s2, _) if hasReachedPoints(3, s1, s2) && s1 == s2 => "deuce"
    case (s1, s2, name) if hasAnyReachedPoints(4, s1, s2) && hasAdvantage(2, s1, s2) => name.get + " won"
    case (s1, s2, _) => getScoreName(s1) + ", " + getScoreName(s2)
  }

  private def hasReachedPoints(points: Int, score: Int*): Boolean = score.forall(_ >= points)

  private def hasAnyReachedPoints(points: Int, score: Int*): Boolean = score.exists(_ >= points)

  private def hasAdvantage(points: Int, score1: Int, score2: Int) = (score1 + points == score2) || (score2 + points == score1)

  private def getScoreName(score: Int): String = score match {
    case 0 => "love"
    case 1 => "fifteen"
    case 2 => "thirty"
    case 3 => "forty"
  }
}

class Player(val name: String) {
  var currentScore: Int = 0

  def score: Int = currentScore

  def winBall: Unit = currentScore += 1

}

object Player {
  @Override
  def apply(name: String): Player = new Player(name)
}
