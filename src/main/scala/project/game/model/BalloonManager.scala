package project.game.model

import scalafx.animation.{AnimationTimer, Timeline}
import scalafx.scene.layout.AnchorPane
import scalafx.util.Duration
import scala.util.Random
import scala.collection.mutable.ListBuffer

class BalloonManager(private val balloonPane: AnchorPane, private val handleBalloonClick: Balloon => Unit) {
  // Factory methods for creating different balloon types
  private val balloonFactories: Map[String, () => Balloon] = Map(
    "red" -> (() => new RedBalloon()),
    "blue" -> (() => new BlueBalloon()),
    "black" -> (() => new BlackBalloon())
  )

  // List to hold animations for balloons
  private val animations = ListBuffer[AnimationTimer]()
  private var balloonGenerator: Timeline = _
  private val gamePaused: Boolean = false
  private var normalSpeed: Double = 1.5

  // Start generating balloons at fixed intervals
  def startBalloonGenerator(): Unit = {
    balloonGenerator = new Timeline {
      cycleCount = Timeline.Indefinite
      keyFrames = Seq(
        scalafx.animation.KeyFrame(Duration(500), onFinished = _ => createBalloons())
      )
    }
    balloonGenerator.play()
  }

  // Create balloons at random positions within the pane
  private def createBalloons(): Unit = {
    if (!gamePaused) {
      val balloonType = balloonFactories.keys.toList(Random.nextInt(balloonFactories.size))
      val balloonFactory = balloonFactories(balloonType)
      val balloon = balloonFactory()
      balloon.fitWidth = 70
      balloon.fitHeight = 140

      // Check if the balloon image is successfully loaded
      if (balloon.image.value == null) {
        println(s"Failed to load image: ${balloon.colour}")
      } else {
        // Set the balloon's position within the pane
        balloon.layoutX.value = Random.nextDouble() * (balloonPane.width.value - balloon.fitWidth.value)
        balloon.layoutY.value = balloonPane.height.value

        // Set the click handler for the balloon
        balloon.onMouseClicked = _ => handleBalloonClick(balloon)
        balloonPane.children.add(balloon)

        // Create an animation to move the balloon upwards
        val animation = AnimationTimer { _ =>
          if (!gamePaused) {
            if (balloon.layoutY.value > -balloon.fitHeight.value) {
              balloon.layoutY.value = balloon.layoutY.value - normalSpeed
            } else {
              balloon.layoutY.value = balloonPane.height.value
            }
          }
        }
        animations += animation
        animation.start()
      }
    }
  }

  // Stop the balloon generator and animations
  def stopBalloonGenerator(): Unit = {
    if (balloonGenerator != null) balloonGenerator.stop()
    animations.foreach(_.stop())
    animations.clear()
  }

  // Pause the balloon generator and animations
  def pauseBalloonGenerator(): Unit = {
    if (balloonGenerator != null) balloonGenerator.pause()
    animations.foreach(_.stop())
  }

  // Resume the balloon generator and animations
  def resumeBalloonGenerator(): Unit = {
    if (balloonGenerator != null) balloonGenerator.play()
    animations.foreach(_.start())
  }

  // Set the speed of the balloons
  def setBalloonSpeed(speed: Double): Unit = {
    normalSpeed = speed
  }

  // Clear all balloons from the pane
  def resetBalloonPane(): Unit = {
    balloonPane.children.clear()
  }
}
