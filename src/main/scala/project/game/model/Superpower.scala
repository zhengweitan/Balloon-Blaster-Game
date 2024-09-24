package project.game.model

import scalafx.animation.Timeline
import scalafx.util.Duration

class Superpower {
  private val slowSpeed: Double = 0.5
  private val normalSpeed: Double = 1.5

  // Activate the superpower to slow down balloons
  def activateSuperpower(balloonManager: BalloonManager): Unit = {
    balloonManager.setBalloonSpeed(slowSpeed)
    new Timeline {
      cycleCount = 1
      keyFrames = Seq(
        scalafx.animation.KeyFrame(Duration(5000), onFinished = _ => {
          balloonManager.setBalloonSpeed(normalSpeed)
        })
      )
    }.play()
  }
}
