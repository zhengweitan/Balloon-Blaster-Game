package project.game.model

import scalafx.scene.text.Text

class Score(private val scoreLabel: Text) {
  private var score: Int = 0

  // Increase the score by a specified number of points
  def increaseScore(points: Int): Unit = {
    score += points
    scoreLabel.text = s"Score: $score"
  }

  // Reset the score to zero
  def resetScore(): Unit = {
    score = 0
    scoreLabel.text = s"Score: $score"
  }

  // Get the current score
  def getScore(): Int = score
}
