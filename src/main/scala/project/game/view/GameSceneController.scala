package project.game.view

import scalafx.scene.layout.AnchorPane
import scalafx.scene.text.Text
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}
import scalafx.application.Platform
import scalafxml.core.macros.sfxml
import project.game.model.{Balloon, BalloonManager, Timer, Superpower, Score}
import scalafx.event.ActionEvent
import scalafx.Includes._

@sfxml
class GameSceneController(
                           private val balloonPane: AnchorPane,
                           private val scoreLabel: Text,
                           private val timerLabel: Text
                         ) {

  // Initialize game components
  private val scoreManager = new Score(scoreLabel)
  private val timer = new Timer(timerLabel, 30)
  private val superpower = new Superpower
  private val balloonManager = new BalloonManager(balloonPane, handleBalloonClick)

  // Method to initialize the game scene
  def initialize(): Unit = {
    println("Initializing game scene...")
    scoreManager.resetScore()
    timer.resetTimer(30)
    balloonManager.startBalloonGenerator()
    timer.startGameTimer(() => endGame("Time is up!"))
  }

  // Handle balloon click events
  private def handleBalloonClick(balloon: Balloon): Unit = {
    balloon.colour match {
      case "red" =>
        scoreManager.increaseScore(1)
        println("Red balloon clicked! Score: " + scoreManager.getScore)
      case "blue" =>
        scoreManager.increaseScore(2)
        println("Blue balloon clicked! Score: " + scoreManager.getScore)
      case "black" =>
        println("Black balloon clicked! Game Over")
        endGame("You clicked a black balloon!")
      case _ =>
        println("Other balloon clicked!")
    }
    balloonPane.children.remove(balloon)
  }

  // End the game and display a message
  private def endGame(message: String): Unit = {
    balloonManager.stopBalloonGenerator()
    timer.stopGameTimer()
    showGameOverDialog(message)
  }

  // Show a dialog indicating the game is over
  private def showGameOverDialog(message: String): Unit = {
    Platform.runLater {
      val retryButtonType = new ButtonType("Retry")
      val exitButtonType = new ButtonType("Exit")
      val alert = new Alert(AlertType.Information) {
        initOwner(balloonPane.scene().getWindow) // Use getWindow
        title = "Game Over"
        headerText = message
        contentText = s"Game Over. Your final score is: ${scoreManager.getScore}"
        buttonTypes = Seq(retryButtonType, exitButtonType)
      }
      val result = alert.showAndWait()
      result match {
        case Some(`retryButtonType`) => resetGame()
        case Some(`exitButtonType`) => Platform.exit()
        case _ => // Do nothing
      }
    }
  }

  // Reset the game to its initial state
  private def resetGame(): Unit = {
    scoreManager.resetScore()
    timer.resetTimer(30)
    balloonPane.children.clear()
    balloonManager.startBalloonGenerator()
    timer.startGameTimer(() => endGame("Time is up!"))
  }

  // Handle pause button click
  def handlePauseButton(action: ActionEvent): Unit = {
    balloonManager.pauseBalloonGenerator()
    timer.pauseTimer()
    val pauseAlert = new Alert(AlertType.Confirmation) {
      initOwner(balloonPane.scene().getWindow) // Use getWindow
      title = "Game Paused"
      headerText = "Game Paused"
      contentText = "What would you like to do?"
      buttonTypes = Seq(new ButtonType("Resume"), new ButtonType("Exit"))
    }
    val result = pauseAlert.showAndWait()
    result match {
      case Some(bt) if bt.text == "Resume" =>
        balloonManager.resumeBalloonGenerator()
        timer.resumeTimer()
      case Some(bt) if bt.text == "Exit" => Platform.exit()
      case _ => // Do nothing
    }
  }

  // Handle superpower button click
  def handleSuperpowerButton(action: ActionEvent): Unit = {
    superpower.activateSuperpower(balloonManager)
  }
}




