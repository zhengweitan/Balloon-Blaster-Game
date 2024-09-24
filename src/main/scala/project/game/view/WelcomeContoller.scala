package project.game.view

import project.game.MainApp
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

@sfxml
class WelcomeController {
  // Start the game when the play button is clicked
  def playGame(): Unit = {
    MainApp.showGameScene()
  }

  // Show the game guide when the guide button is clicked
  def showGuide(): Unit = {
    val alert = new Alert(AlertType.Information) {
      title = "Game Guide"
      headerText = "How to Play Balloon Blaster Game"
      contentText = "Welcome to the Balloon Blaster game! Your objective is to score as many points as possible within the 30-second time limit. The rules are as follows:\n\n" +
        "- Click on the red balloons to score 1 point.\n" +
        "- Click on the blue balloons to score 2 points.\n" +
        "- Avoid clicking on the black balloons as doing so will immediately end the game.\n" +
        "- Keep an eye on the timer displayed. The game will automatically end when the timer reaches zero.\n" +
        "- Utilize the superpower button, represented by the snail icon at the top left corner of screen, to slow down the movement of the balloons for 5 seconds.\n\n" +
        "Good luck, and have fun blasting balloons!"
    }
    alert.showAndWait()
  }
}
