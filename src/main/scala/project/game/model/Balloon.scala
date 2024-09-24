package project.game.model

import scalafx.scene.image.{Image, ImageView}

// Base class for all balloons
abstract class Balloon(imagePath: String, val colour: String) extends ImageView {
  // Load the image
  image = new Image(getClass.getResourceAsStream(imagePath))
  println(s"Loading image from path: $imagePath")

  // Set balloon size
  fitWidth = 50
  fitHeight = 100
  preserveRatio = true

  // Method to handle balloon click; can be overridden
  def onClick(): Unit = {
    println(s"${colour.capitalize} balloon clicked!")
  }
}

// Subclass for red balloons
class RedBalloon extends Balloon("/Images/RedBalloon.gif", "red") {
  override def onClick(): Unit = {
    println("Red balloon is clicked!")
  }
}

// Subclass for blue balloons
class BlueBalloon extends Balloon("/Images/BlueBalloon.gif", "blue") {
  override def onClick(): Unit = {
    println("Blue balloon is clicked!")
  }
}

// Subclass for black balloons
class BlackBalloon extends Balloon("/Images/BlackBalloon.png", "black") {
  override def onClick(): Unit = {
    println("Black balloon is clicked!")
  }
}