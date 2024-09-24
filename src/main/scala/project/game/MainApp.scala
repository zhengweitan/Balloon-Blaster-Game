package project.game

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.StageStyle
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import project.game.view.GameSceneController

object MainApp extends JFXApp {
  // Initialize the main stage (window)
  stage = new PrimaryStage {
    initStyle(StageStyle.Undecorated) // Disable window decorations
    title = "BalloonBlaster"
    scene = new Scene {
    }
  }

  // Display the game scene window
  def showGameScene(): Unit = {
    val resource = getClass.getResource("/project/game/view/GameScene.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    val controller = loader.getController[GameSceneController#Controller]
    stage.scene().setRoot(roots)
    controller.initialize()    // Initialize the game scene
  }

  // Display the welcome screen
  def showWelcome(): Unit = {
    val resource = getClass.getResource("/project/game/view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    stage.scene().setRoot(roots)
  }

  // call to display Welcome when the game starts
  showWelcome()
}
