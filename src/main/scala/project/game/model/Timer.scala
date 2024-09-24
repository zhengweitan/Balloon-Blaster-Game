package project.game.model

import scalafx.animation.Timeline
import scalafx.scene.text.Text
import scalafx.util.Duration

class Timer(private val timerLabel: Text, private var remainingTime: Int) {
  private var gameTimer: Timeline = _

  // Start the game timer
  def startGameTimer(onTimeUp: () => Unit): Unit = {
    gameTimer = new Timeline {
      cycleCount = remainingTime
      keyFrames = Seq(
        scalafx.animation.KeyFrame(Duration(1000), onFinished = _ => {
          remainingTime -= 1
          timerLabel.text = s"Time: $remainingTime"
          if (remainingTime <= 0) {
            onTimeUp()
          }
        })
      )
    }
    gameTimer.play()
  }

  // Stop the game timer
  def stopGameTimer(): Unit = {
    if (gameTimer != null) {
      gameTimer.stop()
    }
  }

  // Reset the timer to a new time value
  def resetTimer(newTime: Int): Unit = {
    remainingTime = newTime
    timerLabel.text = s"Time: $remainingTime"
  }

  // Pause the game timer
  def pauseTimer(): Unit = {
    if (gameTimer != null) gameTimer.pause()
  }

  // Resume the game timer
  def resumeTimer(): Unit = {
    if (gameTimer != null) gameTimer.play()
  }
}
